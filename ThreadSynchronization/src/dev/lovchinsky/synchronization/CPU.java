package dev.lovchinsky.synchronization;

public class CPU extends Thread {
    private static int number = 0;

    private final int id;
    private volatile boolean isBusy;
    private Process currentProcess;
    private CPUQueue cpuQueue;

    public CPU(CPUQueue cpuQueue) {
        id = number++;
        this.cpuQueue = cpuQueue;

        System.out.println(this + " has created");
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean isBusy) {
        this.isBusy = isBusy;
    }

    public synchronized boolean setProcess(Process process) {
        if (!isBusy()) {
            currentProcess = process;
            setBusy(true);

            System.out.println(this + " will run");

            notify();
        }

        return isBusy();
    }

    private void executeProcess(Process process) {
        System.out.println(this + " processing process: " + process);

        process.execute();
        setBusy(false);

        System.out.println(this + " has just finished process: " + process);
    }

    private void executeProcess(CPUQueue cpuQueue) {
        Process process = cpuQueue.poll();
        if(process != null) {
            setBusy(true);
            executeProcess(process);
        }
    }

    @Override
    public String toString() {
        return "CPU(" + id + ")";
    }

    @Override
    public void run() {
        while(!isInterrupted()) {
            if(currentProcess != null) {
                executeProcess(currentProcess);
                currentProcess = null;
            } else {
                if(!cpuQueue.isEmpty()) {
                    executeProcess(cpuQueue);
                } else {
                    synchronized (this) {
                        try {
                            System.out.println(this + " will wait");

                            wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
