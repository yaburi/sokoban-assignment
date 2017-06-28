


import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	/**
	 * auto generated
	 */
	private static final long serialVersionUID = 4754112823176979886L;
	
	public Window(int width, int height, String title, Game game){
		//create new JFrame with title
		JFrame frame = new JFrame(title);
		
		//absolute set the Size of the window (max size == min size)
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));

		//Sets Close Operation
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set Resizeable to false, so the user can not resize the window ( i do believe weird things happen)
		frame.setResizable(false);
		//spawns the window at the center of the screen (since it is null, it automatics sets it to center)
		frame.setLocationRelativeTo(null);
		//adds the game
		frame.add(game);
		//set Visible to true so the user can see it
		frame.setVisible(true);
		//start the Game
		game.start();
	}
	
	
}
