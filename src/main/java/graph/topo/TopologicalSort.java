package graph.topo;

import graph.utils.Metrics;
import java.util.*;

public class TopologicalSort {
    public static List<Integer> kahn(Map<Integer, List<Integer>> dag, Metrics metrics) {
        Map<Integer, Integer> indegree = new HashMap<>();
        for (int u : dag.keySet()) indegree.put(u, 0);

        for (int u : dag.keySet()) {
            for (int v : dag.get(u))
                indegree.put(v, indegree.getOrDefault(v, 0) + 1);
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int u : indegree.keySet())
            if (indegree.get(u) == 0) {
                q.add(u);
                metrics.incPush();
            }

        List<Integer> order = new ArrayList<>();
        while (!q.isEmpty()) {
            int u = q.poll();
            metrics.incPop();
            order.add(u);
            for (int v : dag.get(u)) {
                indegree.put(v, indegree.get(v) - 1);
                if (indegree.get(v) == 0) {
                    q.add(v);
                    metrics.incPush();
                }
            }
        }
        return order;
    }
}
