package graph.dagsp;

import graph.model.Graph;
import graph.utils.Metrics;
import java.util.*;

public class DAGShortestPath {
    private final Graph graph;
    private final List<Integer> topoOrder;
    private final int source;
    private final Metrics metrics;

    public DAGShortestPath(Graph graph, List<Integer> topoOrder, int source, Metrics metrics) {
        this.graph = graph;
        this.topoOrder = topoOrder;
        this.source = source;
        this.metrics = metrics;
    }

    public Map<Integer, Integer> shortestPaths() {
        Map<Integer, Integer> dist = new HashMap<>();
        for (int i = 0; i < graph.size(); i++)
            dist.put(i, Integer.MAX_VALUE);
        dist.put(source, 0);

        for (int u : topoOrder) {
            if (dist.get(u) != Integer.MAX_VALUE) {
                for (Graph.Edge e : graph.getAdj().get(u)) {
                    if (dist.get(e.to) > dist.get(u) + e.weight) {
                        dist.put(e.to, dist.get(u) + e.weight);
                        metrics.incRelax();
                    }
                }
            }
        }
        return dist;
    }

    public Map<Integer, Integer> longestPaths() {
        Map<Integer, Integer> dist = new HashMap<>();
        for (int i = 0; i < graph.size(); i++)
            dist.put(i, Integer.MIN_VALUE);
        dist.put(source, 0);

        for (int u : topoOrder) {
            if (dist.get(u) != Integer.MIN_VALUE) {
                for (Graph.Edge e : graph.getAdj().get(u)) {
                    if (dist.get(e.to) < dist.get(u) + e.weight) {
                        dist.put(e.to, dist.get(u) + e.weight);
                        metrics.incRelax();
                    }
                }
            }
        }
        return dist;
    }
}
