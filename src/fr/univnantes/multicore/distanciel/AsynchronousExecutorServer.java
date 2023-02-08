package fr.univnantes.multicore.distanciel;

import java.awt.Image;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsynchronousExecutorServer implements Server {

	/**
	 * Fields
	 */
	private final ExecutorService threadPool;
	private final ImageDrawer drawer;

	/**
	 * Creates a new asynchronous executor server
	 *
	 * @param drawer the drawer
	 * @param numberOfThreads the number of threads in the thread pool (ExecutorService)
	 */
	public AsynchronousExecutorServer(ImageDrawer drawer, int numberOfThreads) {
		this.threadPool = Executors.newFixedThreadPool(numberOfThreads);
		this.drawer = drawer;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Pre-compute the image asynchronously
	 */
	@Override
	public Block getBlock(ScreenArea area) {
		Future<Image> image = threadPool.submit(() -> drawer.getImage(area));
		return new AsynchronousBlock(image, area);
	}	
	
}