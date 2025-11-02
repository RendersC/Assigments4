package graph.model;

import java.util.*;

public class Graph {
    private final int n;
    private final boolean directed;
    private final Map<Integer, List<Edge>> adj;

    public static class Edge {
        public int to, weight;
        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    public Graph(int n, boolean directed) {
        this.n = n;
        this.directed = directed;
        this.adj = new HashMap<>();
        for (int i = 0; i < n; i++) adj.put(i, new ArrayList<>());
    }

    public void addEdge(int u, int v, int w) {
        adj.get(u).add(new Edge(v, w));
        if (!directed) adj.get(v).add(new Edge(u, w));
    }

    public int size() { return n; }
    public Map<Integer, List<Edge>> getAdj() { return adj; }
}
