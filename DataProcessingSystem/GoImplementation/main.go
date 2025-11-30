package main

import (
	"fmt"
	"log"
	"sync"
	"time"
)

// Task represents a data processing task
type Task struct {
	ID      int
	Payload string
}

// Worker processes tasks from the tasks channel
func Worker(id int, tasks <-chan Task, wg *sync.WaitGroup, mu *sync.Mutex) {
	defer wg.Done()
	for task := range tasks {
		log.Printf("Worker %d started Task %d\n", id, task.ID)
		// Simulate processing time
		time.Sleep(time.Millisecond * 500)

		// Critical section: safely print or store results
		mu.Lock()
		fmt.Printf("Worker %d processed Task %d with payload: %s\n", id, task.ID, task.Payload)
		mu.Unlock()

		log.Printf("Worker %d finished Task %d\n", id, task.ID)
	}
}

func main() {
	// Number of workers
	numWorkers := 3
	// Number of tasks
	numTasks := 10

	// Create channel as shared queue
	tasks := make(chan Task, numTasks)

	// WaitGroup to wait for all workers
	var wg sync.WaitGroup
	// Mutex for safe access to shared output
	var mu sync.Mutex

	// Start workers
	for i := 1; i <= numWorkers; i++ {
		wg.Add(1)
		go Worker(i, tasks, &wg, &mu)
	}

	// Add tasks to channel
	for i := 1; i <= numTasks; i++ {
		tasks <- Task{ID: i, Payload: fmt.Sprintf("Data_%d", i)}
	}

	// Close channel after all tasks are added
	close(tasks)

	// Wait for all workers to finish
	wg.Wait()

	log.Println("All tasks processed successfully!")
}

