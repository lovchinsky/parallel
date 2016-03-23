package dev.lovchinsky.synchronization;

public class CPUProcess extends Thread {
    private final CPUQueue cpuQueue;
    private final CPU[] cpus;
    private final long maxProcessingTime;
    private final long maxWaitingTime;

    public CPUProcess(int maxSizeOfQueue, int numberOfCPU, long maxProcessingTime, long maxWaitingTime) {
        cpuQueue = new CPUQueue(maxSizeOfQueue);

        cpus = new CPU[numberOfCPU];
        for (int i = 0; i < numberOfCPU; i++) {
            cpus[i] = new CPU(cpuQueue);
            cpus[i].start();
        }

        this.maxProcessingTime = maxProcessingTime;
        this.maxWaitingTime = maxWaitingTime;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            Process process = new Process((long)(Math.random() * maxProcessingTime));
            boolean isAddedProcess = false;

            for (CPU cpu : cpus) {
                if(!cpu.isBusy()) {
                    if(cpu.setProcess(process)) {
                        isAddedProcess = true;
                        break;
                    }
                }
            }

            if(!isAddedProcess) {
                cpuQueue.offer(process);
            }

            try {
                sleep((long)(Math.random() * maxWaitingTime));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
