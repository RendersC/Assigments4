README
Project Title

Assignment 4 — Graph Algorithms and Performance Metrics

Description

This project implements several graph algorithms in Java and measures their performance.
The following algorithms are included:

Tarjan’s SCC Algorithm — finds strongly connected components.

Topological Sort (Kahn’s Algorithm) — orders vertices in a directed acyclic graph (DAG).

DAG Shortest and Longest Path — calculates the shortest and longest paths in a DAG.

Each algorithm records performance metrics:

Number of operations (DFS, Relax, Push, Pop)

Execution time in milliseconds

All results are saved automatically in data/metrics_results.csv.

Project Structure
Assigments4/
├── pom.xml
├── data/
│   ├── tasks.json
│   ├── small1.json ... large3.json
│   └── metrics_results.csv
└── src/main/java/graph/
├── Main.java
├── model/Graph.java
├── scc/TarjanSCC.java
├── topo/TopologicalSort.java
├── topo/CondensationGraph.java
├── dagsp/DAGShortestPath.java
└── utils/Metrics.java

Requirements

Java 17 or higher

Apache Maven 3.8 or higher

How to Run

Compile the project:

mvn compile


Run all datasets:

mvn exec:java -Dexec.mainClass="graph.Main"


After execution, all results will be shown in the console and saved in
data/metrics_results.csv.

Dataset Format

Each graph dataset is stored in a .json file:

{
"directed": true,
"n": 8,
"edges": [
{"u": 0, "v": 1, "w": 3},
{"u": 1, "v": 2, "w": 2}
],
"source": 0,
"weight_model": "edge"
}

Example Output
===== Testing small1.json =====
[SCC] time: 0.210 ms | DFS: 16 | Relax: 0 | Push: 0 | Pop: 0
[Topo] time: 0.038 ms | DFS: 0 | Relax: 0 | Push: 8 | Pop: 8
[DAG-SP] time: 0.061 ms | DFS: 0 | Relax: 10 | Push: 0 | Pop: 0

Metrics File Example

File data/metrics_results.csv:

Dataset,Algorithm,Time(ms),DFS,Relax,Push,Pop
small1.json,SCC,0.210,16,0,0,0
small1.json,Topo,0.038,0,0,8,8
small1.json,DAG-SP,0.061,0,10,0,0

Explanation of Metrics
Metric	Meaning
DFS	Number of depth-first recursive calls (Tarjan SCC)
Relax	Number of edge relaxations (shortest/longest path)
Push	Queue insert operations in topological sort
Pop	Queue remove operations in topological sort
Time (ms)	Execution time of the algorithm
Datasets Summary
Type	Files	Nodes	Cyclic	Description
Small	small1–small3.json	6–10	Mixed	Simple and small graphs
Medium	medium1–medium3.json	10–20	Mixed	Moderate graphs
Large	large1–large3.json	20–50	Mostly acyclic	Large test graphs
Example	tasks.json	8	Yes	Sample dataset for demonstration
Output Files

Console output with algorithm metrics

CSV report: data/metrics_results.csv

You can open the CSV file in Excel or Google Sheets to analyze the performance results.


### REPORT IN docs/report.pdf
