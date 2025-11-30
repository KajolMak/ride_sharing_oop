package com.example.dataprocessing;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int numWorkers = 3;
        int numTasks = 10;

        TaskQueue taskQueue = new TaskQueue();

        // Add tasks to queue
        for (int i = 1; i <= numTasks; i++) {
            taskQueue.addTask(new Task(i, "Data_" + i));
        }

        // Start worker threads
        ExecutorService executor = Executors.newFixedThreadPool(numWorkers);
        for (int i = 1; i <= numWorkers; i++) {
            executor.submit(new Worker(i, taskQueue));
        }

        // Optional: shutdown executor after some delay
        executor.shutdown();
    }
}

