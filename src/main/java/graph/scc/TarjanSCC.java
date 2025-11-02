package graph.scc;

import graph.model.Graph;
import graph.utils.Metrics;
import java.util.*;

public class TarjanSCC {
    private int time = 0;
    private final Graph graph;
    private final int[] disc, low;
    private final boolean[] inStack;
    private final Deque<Integer> stack;
    private final List<List<Integer>> components;
    private final Metrics metrics;

    public TarjanSCC(Graph graph, Metrics metrics) {
        this.graph = graph;
        this.metrics = metrics;
        int n = graph.size();
        disc = new int[n];
        low = new int[n];
        inStack = new boolean[n];
        stack = new ArrayDeque<>();
        components = new ArrayList<>();
        Arrays.fill(disc, -1);
    }

    public List<List<Integer>> findSCCs() {
        for (int i = 0; i < graph.size(); i++)
            if (disc[i] == -1)
                dfs(i);
        return components;
    }

    private void dfs(int u) {
        metrics.incDFS(); // ← считаем вызов DFS

        disc[u] = low[u] = ++time;
        stack.push(u);
        inStack[u] = true;

        for (Graph.Edge edge : graph.getAdj().get(u)) {
            int v = edge.to;
            if (disc[v] == -1) {
                dfs(v);
                low[u] = Math.min(low[u], low[v]);
            } else if (inStack[v]) {
                low[u] = Math.min(low[u], disc[v]);
            }
        }

        if (low[u] == disc[u]) {
            List<Integer> component = new ArrayList<>();
            int v;
            do {
                v = stack.pop();
                inStack[v] = false;
                component.add(v);
            } while (v != u);
            components.add(component);
        }
    }
}
