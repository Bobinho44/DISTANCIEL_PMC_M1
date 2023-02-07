package fr.univnantes.multicore.distanciel;

import java.awt.Image;
import java.util.concurrent.Future;

public class AsynchronousCustomServer implements Server {

	private final ThreadPool threadPool = new ThreadPool(10);
	private final ImageDrawer drawer;
		 
	public AsynchronousCustomServer(ImageDrawer drawer) {
		this.drawer = drawer;
	}
	
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