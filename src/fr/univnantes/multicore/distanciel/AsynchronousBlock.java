package fr.univnantes.multicore.distanciel;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsynchronousBlock implements Block {

	/**
	 * Fields
	 */
	private final Future<Image> image;
	private final ScreenArea area;

	/**
	 * Creates a new asynchronous block
	 *
	 * @param image the image being pre-computed asynchronously
	 * @param area the area
	 */
	public AsynchronousBlock(Future<Image> image, ScreenArea area) {
		this.image = image;
		this.area = area;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Draws the image when it is available, asynchronously
	 */
	@Override
	public boolean draw(Graphics2D graphics) {
		try {
			return image.isDone() && graphics.drawImage(image.get(), area.pixels().x, area.pixels().y, null);
		} catch (InterruptedException | ExecutionException e) {
			return false;
		}
	}
	
}