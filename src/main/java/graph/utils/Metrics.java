package graph.utils;

import java.io.FileWriter;
import java.io.IOException;

public class Metrics {
    private long startTime;
    private long endTime;
    private long dfsCount = 0;
    private long relaxCount = 0;
    private long pushCount = 0;
    private long popCount = 0;

    private static final String CSV_FILE = "data/metrics_results.csv";
    private static boolean headerWritten = false;

    // ==== Таймер ====
    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public double getTimeMs() {
        return (endTime - startTime) / 1_000_000.0;
    }

    // ==== Счётчики ====
    public void incDFS() { dfsCount++; }
    public void incRelax() { relaxCount++; }
    public void incPush() { pushCount++; }
    public void incPop() { popCount++; }

    // ==== Геттеры ====
    public long getDfsCount() { return dfsCount; }
    public long getRelaxCount() { return relaxCount; }
    public long getPushCount() { return pushCount; }
    public long getPopCount() { return popCount; }

    // ==== Вывод на экран ====
    public void print(String label) {
        System.out.printf("[%s] time: %.3f ms | DFS: %d | Relax: %d | Push: %d | Pop: %d%n",
                label, getTimeMs(), dfsCount, relaxCount, pushCount, popCount);
    }

    // ==== Запись в CSV ====
    public void saveToCSV(String datasetName, String algorithm) {
        try (FileWriter writer = new FileWriter(CSV_FILE, true)) {
            if (!headerWritten) {
                writer.write("Dataset,Algorithm,Time(ms),DFS,Relax,Push,Pop\n");
                headerWritten = true;
            }
            writer.write(String.format("%s,%s,%.3f,%d,%d,%d,%d\n",
                    datasetName, algorithm, getTimeMs(), dfsCount, relaxCount, pushCount, popCount));
        } catch (IOException e) {
            System.err.println("Error writing metrics CSV: " + e.getMessage());
        }
    }
}
