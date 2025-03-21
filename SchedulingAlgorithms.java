import java.util.*;

public class SchedulingAlgorithms {

    // FCFS Scheduling
    public static void executeFCFS(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;

        System.out.println("\nFirst-Come, First-Served (FCFS) Scheduling:");
        for (Process p : processes) {
            if (currentTime < p.arrivalTime)
                currentTime = p.arrivalTime;
            int waitingTime = currentTime - p.arrivalTime;
            int turnaroundTime = waitingTime + p.burstTime;
            currentTime += p.burstTime;
            printProcessInfo(p, waitingTime, turnaroundTime);
        }
    }

   // SJF Scheduling (Fixed Version)
    public static void executeSJF(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));
        int currentTime = 0, completed = 0, index = 0;

        System.out.println("\nShortest Job First (SJF) Scheduling:");
        while (completed < processes.size()) {
            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                readyQueue.add(processes.get(index));
                index++;
            }
            if (!readyQueue.isEmpty()) {
                Process shortest = readyQueue.poll();
                int waitingTime = currentTime - shortest.arrivalTime;
                waitingTime = Math.max(waitingTime, 0);
                int turnaroundTime = waitingTime + shortest.burstTime;
                currentTime += shortest.burstTime;
                completed++;
                printProcessInfo(shortest, waitingTime, turnaroundTime);
            } else {
                currentTime++;
            }
        }
    }

    // Helper method to print process details
    private static void printProcessInfo(Process p, int waitingTime, int turnaroundTime) {
        System.out.printf("PID: %-4d | Waiting Time: %-4d | Turnaround Time: %-4d%n",
                p.pid, waitingTime, turnaroundTime);
    }
}
