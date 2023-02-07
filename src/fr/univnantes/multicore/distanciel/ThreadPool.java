package fr.univnantes.multicore.distanciel;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {
	
	private final BlockingQueue<FutureTask<?>> priorityQueue = new LinkedBlockingQueue<>();
	private final BlockingQueue<FutureTask<?>> nonPriorityQueue = new LinkedBlockingQueue<>();
	private final Thread[] threads;
	
	public ThreadPool(int numberOfThreads) {
	    threads = new Thread[numberOfThreads];
	    for (int i = 0; i < numberOfThreads; i++) {
	        threads[i] = new Thread(() -> {
	        	while (true) {
	                FutureTask<?> futureTask = priorityQueue.poll();
					if (futureTask == null) {
						futureTask = nonPriorityQueue.poll();
					}
					if (futureTask != null) {             		
						futureTask.run();
					}
	             }
	        });
	        threads[i].start();
	    }
	}
	
	public synchronized <T> Future<T> submitPriorityTask(Callable<T> task) {
	    FutureTask<T> futureTask = new FutureTask<>(task);
	    priorityQueue.offer(futureTask);
	    return futureTask;
	}
	
	public synchronized <T> Future<T> submitNonPriorityTask(Callable<T> task) {
	    FutureTask<T> futureTask = new FutureTask<>(task);
	    nonPriorityQueue.offer(futureTask);
	    return futureTask;
	}
	
}