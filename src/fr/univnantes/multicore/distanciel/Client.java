package fr.univnantes.multicore.distanciel;

import java.awt.*;
import javax.swing.*;

/**
 * All the data needed to build a new block
 */
public class Client extends JFrame {

	private final long startTime = System.currentTimeMillis();
	private boolean isDone = false;
	private static final long serialVersionUID = 1L;

	public Client(Block block, String frametitle) {
		var timer = new Timer(100, event -> repaint());
		timer.start();
		var dimension = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		setSize(dimension.width, dimension.height);
		setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(frametitle);
		getContentPane().add(new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics graphics) {
				if(block.draw((Graphics2D) graphics) && !isDone) {
					isDone = true;
					long endTime = System.currentTimeMillis();
					System.out.println("execution time = " + (endTime - startTime)/1000. + " seconds");
				}
			}
		});
	}
}
