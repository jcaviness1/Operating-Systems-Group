import java.io.*;
import java.util.*;

public class ProcessScheduler {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("processes.txt"))) {
            String line = reader.readLine(); // Skip header line

            while ((line = reader.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int pid = Integer.parseInt(parts[0]);
                int arrivalTime = Integer.parseInt(parts[1]);
                int burstTime = Integer.parseInt(parts[2]);
                int priority = Integer.parseInt(parts[3]);

                processes.add(new Process(pid, arrivalTime, burstTime, priority));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        // Display processes
        System.out.println("PID  Arrival  Burst  Priority");
        for (Process p : processes) {
            System.out.printf("%-4d %-7d %-6d %-4d%n", p.pid, p.arrivalTime, p.burstTime, p.priority);
        }

        // Execute scheduling algorithms
        SchedulingAlgorithms.executeFCFS(new ArrayList<>(processes));
        SchedulingAlgorithms.executeSJF(new ArrayList<>(processes));
    }
}
