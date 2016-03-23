package dev.lovchinsky;

import dev.lovchinsky.synchronization.CPUProcess;

public class Main {
    public static final int MAX_SIZE_OF_QUEUE = 10;
    public static final int NUMBER_OF_CPU = 2;
    public static final long MAX_PROCESSING_TIME = 5000;
    public static final long MAX_WAITING_TIME = 2000;

    public static void main(String[] args) {
        CPUProcess cpuProcess = new CPUProcess(MAX_SIZE_OF_QUEUE, NUMBER_OF_CPU, MAX_PROCESSING_TIME, MAX_WAITING_TIME);
        cpuProcess.start();
    }
}
