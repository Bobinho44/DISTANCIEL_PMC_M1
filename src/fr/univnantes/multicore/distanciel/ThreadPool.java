package fr.univnantes.multicore.distanciel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

	/**
	 * Fields
	 */
	private final BlockingQueue<FutureTask<?>> priorityQueue = new LinkedBlockingQueue<>();
	private final BlockingQueue<FutureTask<?>> nonPriorityQueue = new LinkedBlockingQueue<>();

	/**
	 * Creates a new thread pool
	 *
	 * @param numberOfThreads the number of threads used
	 */
	public ThreadPool(int numberOfThreads) {
	    for (int i = 0; i < numberOfThreads; i++) {
	        new Thread(() -> {
	        	while (true) {
	                FutureTask<?> futureTask = priorityQueue.poll();

					//Check for a non-priority task if no priority task is available
					if (futureTask == null) {
						futureTask = nonPriorityQueue.poll();
					}

					//Executed the task found
					if (futureTask != null) {
						futureTask.run();
					}
	             }
	        }).start();
	    }
	}

	/**
	 * Submit a priority task
	 *
	 * @param task The task
	 * @return the result of the task being calculated asynchronously
	 */
	public synchronized <T> Future<T> submitPriorityTask(Callable<T> task) {
	    FutureTask<T> futureTask = new FutureTask<>(task);
	    priorityQueue.offer(futureTask);
	    return futureTask;
	}

	/**
	 * Submit a non-priority task
	 *
	 * @param task The task
	 * @return the result of the task being calculated asynchronously
	 */
	public synchronized <T> Future<T> submitNonPriorityTask(Callable<T> task) {
	    FutureTask<T> futureTask = new FutureTask<>(task);
	    nonPriorityQueue.offer(futureTask);
	    return futureTask;
	}
	
}