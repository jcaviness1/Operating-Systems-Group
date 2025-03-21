import java.io.*;
import java.util.*;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int priority;

    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class ProcessScheduler {
    public static void main(String[] args) {
        List<Process> processes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("processes.txt"))) {
            String line = reader.readLine(); // Skip the header line

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

        // Display processes to verify
        System.out.println("PID  Arrival  Burst  Priority");
        for (Process p : processes) {
            System.out.printf("%-4d %-7d %-6d %-4d%n", p.pid, p.arrivalTime, p.burstTime, p.priority);
        }

        // Call scheduling algorithms from SchedulingAlgorithms.java
        SchedulingAlgorithms.executeFCFS(new ArrayList<>(processes));
        SchedulingAlgorithms.executeSJF(new ArrayList<>(processes));
    }
}
