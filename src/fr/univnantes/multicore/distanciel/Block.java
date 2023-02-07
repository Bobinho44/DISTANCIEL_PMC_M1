package fr.univnantes.multicore.distanciel;

import java.awt.Graphics2D;

/**
 * All the data needed to build a new block
 */
@FunctionalInterface
public interface Block {
	
	/**
	 * Draws the square block on a given panel
	 * @param g2 object that describes the panel on which to draw
	 */
	boolean draw(Graphics2D graphics);
	
}
