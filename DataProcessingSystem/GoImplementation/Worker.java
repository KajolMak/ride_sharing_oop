package com.example.dataprocessing;

public class Worker implements Runnable {
    private final int workerId;
    private final TaskQueue taskQueue;

    public Worker(int workerId, TaskQueue taskQueue) {
        this.workerId = workerId;
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Task task = taskQueue.getTask();
                System.out.println("Worker " + workerId + " started Task " + task.getId());

                // Simulate processing
                Thread.sleep(500);

                System.out.println("Worker " + workerId + " processed Task " + task.getId()
                        + " with payload: " + task.getPayload());
            }
        } catch (InterruptedException e) {
            System.out.println("Worker " + workerId + " interrupted.");
        }
    }
}

