package graph.topo;

import graph.model.Graph;
import java.util.*;

public class CondensationGraph {
    private final Graph original;
    private final List<List<Integer>> sccs;
    private final Map<Integer, Integer> compIndex; // vertex -> component ID
    private final Map<Integer, List<Integer>> dag; // DAG adjacency list

    public CondensationGraph(Graph original, List<List<Integer>> sccs) {
        this.original = original;
        this.sccs = sccs;
        this.compIndex = new HashMap<>();
        this.dag = new HashMap<>();
        build();
    }

    private void build() {
        for (int i = 0; i < sccs.size(); i++) {
            for (int v : sccs.get(i)) compIndex.put(v, i);
            dag.put(i, new ArrayList<>());
        }


        for (int u = 0; u < original.size(); u++) {
            for (Graph.Edge e : original.getAdj().get(u)) {
                int cu = compIndex.get(u);
                int cv = compIndex.get(e.to);
                if (cu != cv && !dag.get(cu).contains(cv)) {
                    dag.get(cu).add(cv);
                }
            }
        }
    }

    public Map<Integer, List<Integer>> getDag() { return dag; }
    public Map<Integer, Integer> getCompIndex() { return compIndex; }
}
