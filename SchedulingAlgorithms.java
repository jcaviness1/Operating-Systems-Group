import java.util.*;

public class SchedulingAlgorithms {

    // First-Come, First-Served (FCFS)
    public static void executeFCFS(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        int currentTime = 0;

        System.out.println("\nFirst-Come, First-Served (FCFS) Scheduling:");
        List<String> ganttChart = new ArrayList<>();
        List<Integer> timeStamps = new ArrayList<>();
        timeStamps.add(0);

        for (Process p : processes) {
            if (currentTime < p.arrivalTime)
                currentTime = p.arrivalTime;
            p.waitingTime = currentTime - p.arrivalTime;
            p.turnaroundTime = p.waitingTime + p.burstTime;
            currentTime += p.burstTime;

            ganttChart.add("P" + p.pid);
            timeStamps.add(currentTime);
            printProcessInfo(p);
        }

        printGanttChart(ganttChart, timeStamps);
        printAverageTimes(processes);
    }

    // Shortest Job First (SJF)
    public static void executeSJF(List<Process> processes) {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));
        PriorityQueue<Process> readyQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.burstTime));
        int currentTime = 0, completed = 0, index = 0;

        System.out.println("\nShortest Job First (SJF) Scheduling:");
        List<String> ganttChart = new ArrayList<>();
        List<Integer> timeStamps = new ArrayList<>();
        timeStamps.add(0);

        while (completed < processes.size()) {
            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                readyQueue.add(processes.get(index));
                index++;
            }
            if (!readyQueue.isEmpty()) {
                Process shortest = readyQueue.poll();
                shortest.waitingTime = currentTime - shortest.arrivalTime;
                shortest.turnaroundTime = shortest.waitingTime + shortest.burstTime;
                currentTime += shortest.burstTime;
                completed++;

                ganttChart.add("P" + shortest.pid);
                timeStamps.add(currentTime);
                printProcessInfo(shortest);
            } else {
                currentTime++;
            }
        }

        printGanttChart(ganttChart, timeStamps);
        printAverageTimes(processes);
    }

    // print process details
    private static void printProcessInfo(Process p) {
        System.out.printf("PID: %-4d | Waiting Time: %-4d | Turnaround Time: %-4d%n",
                p.pid, p.waitingTime, p.turnaroundTime);
    }

    // Updated Method to Print Gantt Chart 
    private static void printGanttChart(List<String> ganttChart, List<Integer> timeStamps) {
        System.out.println("\nGantt Chart:");

        // Print the process sequence
        System.out.print("| ");
        for (String process : ganttChart) {
            System.out.print(process + " | ");
        }
        System.out.println();

        // Print the timestamps aligned with the Gantt chart
        for (int i = 0; i < timeStamps.size(); i++) {
            System.out.printf("%-5d", timeStamps.get(i)); // Ensures proper alignment
        }
        System.out.println("\n");
    }

    // Method to calculate and print average times
    private static void printAverageTimes(List<Process> processes) {
        double avgWT = processes.stream().mapToInt(p -> p.waitingTime).average().orElse(0);
        double avgTAT = processes.stream().mapToInt(p -> p.turnaroundTime).average().orElse(0);
        System.out.printf("Average Waiting Time: %.2f%n", avgWT);
        System.out.printf("Average Turnaround Time: %.2f%n\n", avgTAT);
    }
}
