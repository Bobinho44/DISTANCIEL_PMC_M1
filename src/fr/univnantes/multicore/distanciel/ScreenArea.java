package fr.univnantes.multicore.distanciel;

import java.awt.*;
import java.awt.geom.*;

public class ScreenArea {

	private final Rectangle pixels;
	private final Rectangle2D geometry;

	public ScreenArea(Rectangle pixels, Rectangle2D geometry) {
		this.pixels = pixels;
		this.geometry = geometry;
	}
	
	public Rectangle pixels() {
		return pixels;
	}
	public Rectangle2D geometry() {
		return geometry;
	}
	
	private final static Rectangle ENV_DIMENSION;

	static {
		Rectangle envDimension = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		ENV_DIMENSION = new Rectangle(0, 0, (int)envDimension.getWidth(), (int)envDimension.getHeight());
	}

	public ScreenArea(double x, double y, double width, double height) {
		this(ENV_DIMENSION, new Rectangle2D.Double(x, y, width, height));
	}

	public ScreenArea subPixels(int i, int j, int width, int height) {
		Rectangle newPixels = new Rectangle(i, j, width, height);
		double gWidth = geometry.getWidth() * width / pixels.width;
		double gHeight = geometry.getHeight() * height / pixels.height;
		Rectangle2D newGeometry = new Rectangle2D.Double(xPixelToGeometry(i), yPixelToGeometry(j), gWidth, gHeight);
		return new ScreenArea(newPixels, newGeometry);
	}

	public ScreenArea subGeometry(double i, double j, double width, double height) {
		Rectangle newPixels = new Rectangle(xGeometryToPixel(i), yGeometryToPixel(j), xGeometryToPixel(width), yGeometryToPixel(height));
		Rectangle2D newGeometry = new Rectangle2D.Double(i, j, width, height);
		return new ScreenArea(newPixels, newGeometry);
	}


	double xPixelToGeometry(int abscissa) {
		return geometry.getX() + (abscissa - pixels.x) * geometry.getWidth() / pixels.getWidth();	
	}

	double yPixelToGeometry(int ordinate) {
		return geometry.getY() + (ordinate - pixels.y) * geometry.getHeight() / pixels.getHeight();	
	}

	int xGeometryToPixel(double abscissa) {
		return (int) (pixels.x + (abscissa - geometry.getX()) * pixels.getWidth() / geometry.getWidth());
	}

	int yGeometryToPixel(double ordinate) {
		return (int) (pixels.y + (ordinate - geometry.getY()) * pixels.getHeight() / geometry.getHeight());
	}

}

