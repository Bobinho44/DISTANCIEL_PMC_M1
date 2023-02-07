package fr.univnantes.multicore.distanciel;

import java.awt.Image;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsynchronousExecutorServer implements Server {

	private final ExecutorService threadPool = Executors.newFixedThreadPool(15);
	private final ImageDrawer drawer;
		 
	public AsynchronousExecutorServer(ImageDrawer drawer) {
		this.drawer = drawer;
	}
	
	@Override
	public Block getBlock(ScreenArea area) {
		Future<Image> image = threadPool.submit(() -> {
			return drawer.getImage(area);
		});
		return new AsynchronousBlock(image, area);
	}	
	
}