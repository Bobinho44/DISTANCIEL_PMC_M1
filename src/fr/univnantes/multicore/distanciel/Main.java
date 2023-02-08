package fr.univnantes.multicore.distanciel;

import java.awt.Color;

public class Main {	

	public static void main(String[] args) {

		// TODO: Tweak the following settings to find a difficulty that 
		// makes the computation more interesting to observe on your machine
		// (between 10 and 30 seconds)

		// Maximal number of iterations before we consider it will never diverge
		// Keep the threshold low for the naive server below
		// Increase the threshold to 100000 after parallelization for a prettier image
		int threshold = 1000000;
		
		// affects the picture's colors at the end
		// should not impact the computation time
		// @see ColorPalette for other palettes
		var colorPalette = ColorPalette.gradient(new Color(240, 160, 80)).brighter().brighter();
		
		// the math function that will actually compute the pixel colors
		// should not be modified
		var mandelbrot = new Mandelbrot(threshold, colorPalette);

		// area of the complex state that will be drawn
		// @see MandelbrotDrawer for other areas of interest
		var areaToDraw = Mandelbrot.fullSet();
		
		// size of a square block in pixel
		// affects the number of tasks for parallelization
		int blockSize = 50;

		// create two new classes that implement the Server interface:
		//   - one that uses ExecutorService;
		//   - one that uses your own thread pools;
		var server = new AsynchronousCustomServer(mandelbrot, 10);
		
		// Specifies the window on which the picture will be drowned
		// should not be modified
		var block = server.split(blockSize).getBlock(areaToDraw);
		var client = new Client(block, "The Mandelbrot Set");
		client.setVisible(true);
	}
		
}