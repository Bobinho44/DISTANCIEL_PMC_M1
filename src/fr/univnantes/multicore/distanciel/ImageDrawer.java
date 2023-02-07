package fr.univnantes.multicore.distanciel;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.util.function.ToIntBiFunction;

/**
 * Functional interface whose 
 */
@FunctionalInterface
public interface ImageDrawer {

	/**
	 * returns the expected priority level of the task
	 * this is done by evaluating the angles of the block with a very low threshold
	 * @return true if the block should be computed in priority
	 */
	Color getColor(double x, double y);	

	default Image getImage(ScreenArea area) {
		BufferedImage image = new BufferedImage(area.pixels().width, area.pixels().height, BufferedImage.TYPE_4BYTE_ABGR);
		for(int i = 0; i < area.pixels().width; ++i) {
			for(int j = 0; j < area.pixels().height; ++j) {
				double x = area.xPixelToGeometry(i+area.pixels().x);
				double y = area.yPixelToGeometry(j+area.pixels().y);
				Color color = getColor(x, y);
				image.setRGB(i, j, color.getRGB());
			}
		}
		return image;
	}
	
	/**
	 * returns the expected priority level of the task
	 * this is done by evaluating the angles of the block with a very low threshold
	 * @return true if the block should be computed in priority
	 */
	default boolean hasPriority(double x, double y) {
		return false;
	}

	default boolean hasPriority(ScreenArea area) {
		Rectangle2D geometry = area.geometry();
		return 	hasPriority(geometry.getMinX(), geometry.getMinY()) ||
				hasPriority(geometry.getMinX(), geometry.getMaxY()) ||
				hasPriority(geometry.getMaxX(), geometry.getMinY()) ||
				hasPriority(geometry.getMaxX(), geometry.getMaxY());
	}

	
	static ImageDrawer fromFunction(ToIntBiFunction<Double, Double> f, ColorPalette palette) {
		return (x,y) -> palette.colorize(f.applyAsInt(x, y));
	}
	
	
}
