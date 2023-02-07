package fr.univnantes.multicore.distanciel;

import java.awt.Color;
import java.util.function.UnaryOperator;

/**
 * Represents a function in charge of mapping responses of the Mandelbrot algorithm to colors on a screen
 * 
 * This is a functional interface whose functional method is colorize(int).
 */
@FunctionalInterface
public interface ColorPalette {

	/**
	 * Associates a color to an integer
	 * @param i value that was returned by Mandelbrot
	 * @return the color in which the data will be drawn on the screen
	 */
	Color colorize(int i);

	
	default ColorPalette withInnerColor(Color colorIn) {
		return i -> i < 0 ? colorIn : colorize(i);
	}

	default ColorPalette withFilter(UnaryOperator<Color> filter) {
		return i-> filter.apply(colorize(i));
	}

	default ColorPalette brighter() {
		return withFilter(Color::brighter);
	}

	default ColorPalette darker() {
		return withFilter(Color::darker);
	}

	
	static ColorPalette gradient(Color color) {
		return i -> {
			if (i < 0) return Color.BLACK;
			int red = (int) Math.min(255, color.getRed() * i / 50);
			int green = (int) Math.min(255, color.getGreen() * i / 50);
			int blue = (int) Math.min(255, color.getBlue()  * i / 50);
			return new Color(red, green, blue);
		};
	}

	static ColorPalette mapping(Color... map) {
		return i -> i < 0 ? Color.BLACK : map[i%map.length];
	}

	static ColorPalette ultra() {
		return mapping(
				new Color(66, 30, 15),
				new Color(25, 7, 26),
				new Color(9, 1, 47),
				new Color(4, 4, 73),
				new Color(0, 7, 100),
				new Color(12, 44, 138),
				new Color(24, 82, 177),
				new Color(57, 125, 209),
				new Color(134, 181, 229),
				new Color(211, 236, 248),
				new Color(241, 233, 191),
				new Color(248, 201, 95),
				new Color(255, 170, 0),
				new Color(204, 128, 0),
				new Color(153, 87, 0),
				new Color(106, 52, 3));
	}	

}
