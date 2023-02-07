package fr.univnantes.multicore.distanciel;

import java.awt.Color;

/**
 * 
 * @author Matthieu Perrin
 */
public class Mandelbrot implements ImageDrawer {

	private final int threshold;
	private final ColorPalette colorMap;

	public Mandelbrot(int threshold, ColorPalette colorMap) {
		this.threshold = threshold;
		this.colorMap = colorMap;
	}
	
	/**
	 * returns the expected priority level of the task
	 * this is done by evaluating the angles of the block with a very low threshold
	 * @return true if the block should be computed in priority
	 */
	@Override
	public boolean hasPriority(double x, double y) {
		return isMainCardioid(x, y) || isMainCircle(x, y) || mandelbrot(x, y, 100) >= 0;
	}

	/**
	 * returns the expected priority level of the task
	 * this is done by evaluating the angles of the block with a very low threshold
	 * @return true if the block should be computed in priority
	 */
	@Override
	public Color getColor(double x, double y) {
		return colorMap.colorize(mandelbrot(x, y, threshold));
	}

	/**
	 * Does the math
	 * @param x real part of a complex number
	 * @param y imaginary part of a complex number
	 * @return -1 if x+i*y is probably in the set, or an integer between 0 and threshold indicating the time it takes to ensure it is not
	 */
	public int mandelbrot(double x, double y, int threshold){
		if(isMainCardioid(x, y) || isMainCircle(x, y)) return -1;
		double x1 = x, y1 = y;
		for(int k = 0; k < threshold; k++) {
			double x2 = x1 * x1 - y1 * y1 + x;
			y1 = 2 * x1 * y1 + y;
			x1 = x2;
			if(x1 * x1 + y1 * y1 >= 4) return k;
		}
		return -1;
	}

	public boolean isMainCardioid(double x, double y){
		double p2 = (x - .25)*(x - .25) + y*y;
		double p = Math.sqrt(p2);
		return x < p - 2*p2 + .25;
	}

	public boolean isMainCircle(double x, double y){
		return (x+1)*(x+1) +y*y < .25*.25;
	}

	public static ScreenArea sideOfCardioid() {
		return new ScreenArea(-0.65, -0.72, 0.33, 0.22);
	}

	public static ScreenArea biggestCircle() {
		return new ScreenArea(-1.5, -0.1, 0.3, 0.2);
	}

	public static ScreenArea leftReplicate() {
		return new ScreenArea(-1.5, -0.01, 0.03, 0.02);
	}

	public static ScreenArea fullSet() {
		return new ScreenArea(-2.25, -1, 3, 2);
	}

	public static ScreenArea squizzed() {
		return new ScreenArea(-0.122, 0.643, 0.03, 0.02);
	}

}
