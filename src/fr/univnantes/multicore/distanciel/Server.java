package fr.univnantes.multicore.distanciel;

import java.util.*;

/**
 * Manages the computation of the blocks
 * @author Matthieu Perrin
 * TODO: That is the interface that must be implemented twice in the exercise
 */
@FunctionalInterface
public interface Server {
	/**
	 * This method is called each time a block has to be computed
	 * @return a Block object that, when ready, contains the corresponding block
	 */
	Block getBlock(ScreenArea area);
	
	default Server split(int blockSize) {
		Server base = this;
		return largeArea -> {
			List<Block> blocks = new ArrayList<>();

			for(int i = largeArea.pixels().x; i < largeArea.pixels().getMaxX(); i+=blockSize) {
				for(int j = largeArea.pixels().y; j < largeArea.pixels().getMaxY(); j+=blockSize) {
					var smallArea = largeArea.subPixels(i, j, blockSize, blockSize);
					var block = base.getBlock(smallArea);
					blocks.add(block);
				}
			}
			
			return (graphics) -> {
				int numberOfAvailableBlocks = 0;
				for(var block : blocks)
					if(block.draw(graphics))
						numberOfAvailableBlocks++;
				return numberOfAvailableBlocks == blocks.size();
			};
		};
	}
	
}
