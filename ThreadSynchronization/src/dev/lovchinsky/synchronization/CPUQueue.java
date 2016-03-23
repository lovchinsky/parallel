package dev.lovchinsky.synchronization;

import java.util.LinkedList;
import java.util.Queue;

public class CPUQueue {
    private final int maxSize;
    private final Queue<Process> processes = new LinkedList<>();

    public CPUQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void offer(Process process) {
        if(processes.size() < maxSize) {
            processes.offer(process);
            System.out.println(process + " has been added to " + this);
        } else {
            System.out.println(process + " has not been added to " + this);
        }
    }

    public synchronized Process poll() {
        if(!processes.isEmpty()) {
            Process process = processes.poll();
            System.out.println(process + " has been polled from " + this);
            return process;
        } else {
            return null;
        }
    }

    public synchronized boolean isEmpty() {
        return processes.isEmpty();
    }

    @Override
    public String toString() {
        return "Queue(Size: " + processes.size() + ")";
    }
}
