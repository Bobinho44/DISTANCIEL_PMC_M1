package fr.univnantes.multicore.distanciel;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsynchronousBlock implements Block {
	
	private final Future<Image> image;
	private final ScreenArea area;

	public AsynchronousBlock(Future<Image> image, ScreenArea area) {
		this.image = image;
		this.area = area;
	}
	
	@Override
	public boolean draw(Graphics2D graphics) {
		try {
			return image.isDone() && graphics.drawImage(image.get(), area.pixels().x, area.pixels().y, null);
		} catch (InterruptedException | ExecutionException e) {
			return false;
		}
	}
	
}