package dev.lovchinsky.synchronization;

public class Process {
    private static int number = 0;

    private final int id;
    private final long time;

    public Process(long time) {
        id = number++;
        this.time = time;

        System.out.println(this + " has created");
    }

    public void execute() {
        try {
            System.out.println(this + " is started");
            Thread.currentThread().sleep(time);
            System.out.println(this + " is finished");
        } catch (InterruptedException e) {
            System.err.println(this + " was interrupted");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Process(" + id + ")";
    }
}
