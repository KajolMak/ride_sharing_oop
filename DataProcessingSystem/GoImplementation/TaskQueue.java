package com.example.dataprocessing;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TaskQueue {
    private final BlockingQueue<Task> queue;

    public TaskQueue() {
        queue = new LinkedBlockingQueue<>();
    }

    public void addTask(Task task) {
        try {
            queue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Failed to add task: " + task.getId());
        }
    }

    public Task getTask() throws InterruptedException {
        return queue.take();
    }
}

