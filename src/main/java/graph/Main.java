package graph;

import com.google.gson.*;
import graph.model.Graph;
import graph.scc.TarjanSCC;
import graph.topo.*;
import graph.dagsp.*;
import graph.utils.Metrics;

import java.nio.file.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Path dataDir = Path.of("data");

        Files.list(dataDir)
                .filter(f -> f.toString().endsWith(".json"))
                .sorted()
                .forEach(f -> {
                    try {
                        System.out.println("\n===== Testing " + f.getFileName() + " =====");

                        String json = Files.readString(f);
                        JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
                        int n = obj.get("n").getAsInt();
                        boolean directed = obj.get("directed").getAsBoolean();

                        Graph g = new Graph(n, directed);
                        for (JsonElement e : obj.getAsJsonArray("edges")) {
                            JsonObject edge = e.getAsJsonObject();
                            g.addEdge(edge.get("u").getAsInt(),
                                    edge.get("v").getAsInt(),
                                    edge.get("w").getAsInt());
                        }

                        // ===== SCC =====
                        Metrics m = new Metrics();
                        m.start();
                        TarjanSCC scc = new TarjanSCC(g, m);
                        var comps = scc.findSCCs();
                        m.stop();
                        m.print("SCC");
                        m.saveToCSV(f.getFileName().toString(), "SCC");

                        // ===== TOPOLOGICAL SORT =====
                        m = new Metrics();
                        m.start();
                        CondensationGraph cond = new CondensationGraph(g, comps);
                        var dag = cond.getDag();
                        var topo = TopologicalSort.kahn(dag, m);
                        m.stop();
                        m.print("Topo");
                        m.saveToCSV(f.getFileName().toString(), "Topo");

                        // ===== DAG SHORTEST / LONGEST PATH =====
                        m = new Metrics();
                        m.start();
                        int source = obj.get("source").getAsInt();
                        DAGShortestPath dagsp = new DAGShortestPath(g, topo, source, m);
                        dagsp.shortestPaths();
                        dagsp.longestPaths();
                        m.stop();
                        m.print("DAG-SP");
                        m.saveToCSV(f.getFileName().toString(), "DAG-SP");

                    } catch (Exception e) {
                        System.out.println("Error in " + f.getFileName() + ": " + e.getMessage());
                    }
                });

        System.out.println("\n✅ All datasets processed! Metrics saved in data/metrics_results.csv");
    }
}
