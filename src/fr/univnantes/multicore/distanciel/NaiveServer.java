package fr.univnantes.multicore.distanciel;

import java.awt.*;

/**
 * Manages the computation of the blocks
 * @author Matthieu Perrin
 * TODO: This server should be replaced by an asynchronous implementation
 * 
 * The same server can also be implemented using the following curryfied lambda-expression.
 * 
 * static Server split(ImageDrawer drawer) {
 * 		return area -> {
 *			Image image = drawer.getImage(area);
 *			return graphics -> graphics.drawImage(image, area.pixels().x, area.pixels().y, null);
 *		};
 * }
 */
public class NaiveServer implements Server {

	private final ImageDrawer drawer;
	
	public NaiveServer(ImageDrawer drawer) {
		this.drawer = drawer;
	}
	
	@Override
	public Block getBlock(ScreenArea area) {
		Image image = drawer.getImage(area);
		return new NaiveBlock(image, area);
	}	
}

class NaiveBlock implements Block {
	private final Image image;
	private final ScreenArea area;

	public NaiveBlock(Image image, ScreenArea area) {
		this.image = image;
		this.area = area;
	}
	
	@Override
	public boolean draw(Graphics2D graphics) {
		System.out.println("aaaaaaaa");
		return graphics.drawImage(image, area.pixels().x, area.pixels().y, null);
	}
}