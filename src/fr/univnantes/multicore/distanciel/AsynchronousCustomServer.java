package fr.univnantes.multicore.distanciel;

import java.awt.Image;
import java.util.concurrent.Future;

public class AsynchronousCustomServer implements Server {

	/**
	 * Fields
	 */
	private final ThreadPool threadPool;
	private final ImageDrawer drawer;

	/**
	 * Creates a new asynchronous custom server
	 *
	 * @param drawer the drawer
	 * @param numberOfThreads the number of threads in the thread pool (own implementation)
	 */
	public AsynchronousCustomServer(ImageDrawer drawer, int numberOfThreads) {
		this.threadPool = new ThreadPool(numberOfThreads);
		this.drawer = drawer;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Pre-compute the image asynchronously using drawer priority
	 */
	@Override
	public Block getBlock(ScreenArea area) {
		Future<Image> image;

		if (drawer.hasPriority(area)) {
			image = threadPool.submitPriorityTask(() -> drawer.getImage(area));
		}
		else {
			image = threadPool.submitNonPriorityTask(() -> drawer.getImage(area));
		}

		return new AsynchronousBlock(image, area);
	}

}