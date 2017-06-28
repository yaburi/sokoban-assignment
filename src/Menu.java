import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

public class Menu extends MouseAdapter{

	private Game game;
	private Handler handler;
	private String gameStateWord;
	private int bouncer;
	private boolean startFromTop;
	private static Sound menuTheme, theme1button, theme2button;
	
	public Menu(Game game, Handler handler){
		this.game = game;
		this.handler = handler;
		this.gameStateWord = null;
		this.bouncer = 0;
		this.menuTheme = new Sound("../sounds/Gameboy.wav", -1);
		menuTheme.play();
		this.theme1button = new Sound("../sounds/Magic Air Blast 1.wav", 1);
		this.theme2button = new Sound("../sounds/Magic Air Blast 2.wav", 1);
	}
	
	public void mousePressed(MouseEvent e){
		int mx = e.getX();
		int my = e.getY();
		
		if(game.IsgameStateMenu()){
			//playButton
			if (mouseOver(mx, my, 210, 150, 200, 64)) {
				gameStateWord = "PlayerMenu";
				
			}
			
			//help button, is Mouse on the help button
			if(mouseOver(mx, my, 210, 250, 200, 64)){
				gameStateWord = "Help";
			}
			
			// quit button, is Mouse on the quit button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				System.exit(1);
			}
			
			// Theme 1
			if (mouseOver(mx, my, 527, 430, 47, 15)) {
				handler.setTheme(0);
				System.out.println("THEME 1 SELECTED");
				theme1button.play();
			}
			
			// Theme 2
			if (mouseOver(mx, my, 577, 430, 47, 15)) {
				handler.setTheme(1);
				System.out.println("THEME 2 SELECTED");
				theme2button.play();
			}
		
		}
		
		if(game.IsgameStatePlayerMenu()){
			//single player button
			if (mouseOver(mx, my, 210, 150, 200, 64)) {
				gameStateWord = "LevelTypeMenu";
			}
			
			//2 player button
			if(mouseOver(mx, my, 210, 250, 200, 64)){
				gameStateWord = "Game";
				//ThisMUSTBeChanged
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(13);
				game.getSpawner().levelLoader(13, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			// back button, is Mouse on the button button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				gameStateWord = "MainMenu";
			}
			
		}
		if(game.isgameStateLevelTypeMenu()){
			//Level select button
			if (mouseOver(mx, my, 210, 150, 200, 64)) {
				gameStateWord = "LevelSelectMenu";
			}
			
			//Random button
			if(mouseOver(mx, my, 210, 250, 200, 64)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(-1);
				game.getSpawner().levelLoader(game.getSpawner().getCurrentLevelNumber(), handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			// back button, is Mouse on the button button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				gameStateWord = "PlayerMenu";
			}
		}
		
		if(game.isgameStateLevelSelectMenu()){
			
			if(mouseOver(mx, my, 300, 150, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(1);
				game.getSpawner().levelLoader(game.getSpawner().getCurrentLevelNumber(), handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 350, 150, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(2);
				game.getSpawner().levelLoader(2, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 400, 150, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(3);
				game.getSpawner().levelLoader(3, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 450, 150, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(4);
				game.getSpawner().levelLoader(4, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 300, 200, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(5);
				game.getSpawner().levelLoader(5, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 350, 200, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(6);
				game.getSpawner().levelLoader(6, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 400, 200, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(7);
				game.getSpawner().levelLoader(7, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 450, 200, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(8);
				game.getSpawner().levelLoader(8, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			//level 9
			if(mouseOver(mx, my, 300, 250, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(9);
				game.getSpawner().levelLoader(9, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			//level 10
			if(mouseOver(mx, my, 350, 250, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(10);
				game.getSpawner().levelLoader(10, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			//level 11
			if(mouseOver(mx, my, 400, 250, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(11);
				game.getSpawner().levelLoader(11, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			//level 12
			if(mouseOver(mx, my, 450, 250, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(12);
				game.getSpawner().levelLoader(12, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			//level 0
			if(mouseOver(mx, my, 80, 200, 32, 32)){
				gameStateWord = "Game";
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().setCurrentLevelNumber(0);
				game.getSpawner().levelLoader(0, handler);
				
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			
			//back button
			if (mouseOver(mx, my, 210, 350, 200, 64)) {
				gameStateWord = "LevelTypeMenu";
			}
			
			if(gameStateWord != "LevelTypeMenu") menuTheme.stop();
		}
		
		
		//for levelInbetween
		if(game.isgameStateLevelInbetween()){
			gameStateWord = null;
			
			//restart
			if(mouseOver(mx, my, 90, 300, 200, 64)){
				gameStateWord = "Game";
				int currentLevel = game.getSpawner().getCurrentLevelNumber();	
				game.getSpawner().setCurrentLevelNumber(currentLevel - 1);
				currentLevel = game.getSpawner().getCurrentLevelNumber();
				handler.object.clear();
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().levelLoader(currentLevel, handler);
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			//next level
			if(mouseOver(mx, my, 310, 300, 200, 64)){
				gameStateWord = "Game";
				int currentLevel = game.getSpawner().getCurrentLevelNumber();		
				handler.object.clear();
				game.removeKeyListener(game.getKeyinput());
				game.getSpawner().levelLoader(currentLevel, handler);
				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			}
			
			if(mouseOver(mx, my, 200, 380, 200, 64)){
				gameStateWord = "LevelSelectMenu";
			}
			
		}
		
		//back button for help
		if(game.IsgameStateHelp()){
			if(mouseOver(mx, my, 210, 350, 200, 64)){
				gameStateWord = "MainMenu";
				return;
			}
		}
	}
	
	/**
	 * helper function, determines whether mouse is in box
	 * @param mx, mouse x
	 * @param my, mouse y
	 * @param x , x position of box
	 * @param y, y position of box
	 * @param width of the box
	 * @param height of the box
	 * @return
	 */
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if (mx > x && mx < x + width) {
			if (my > y && my < y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void mouseReleased(MouseEvent e){
		if (game.IsgameStateMenu() && gameStateWord == "Game"){
			//do nothing
		} else if (!(gameStateWord == null)){
			game.setgameState(gameStateWord);
		}
	}
	
	
	
	public void tick(){
		
		if(bouncer < 10 && !startFromTop){
			bouncer++;
		} else {
			startFromTop = true;
			if(bouncer > 0){
				bouncer--;
			} else {
				startFromTop = false;
			}
		}
	}
	
	/**
	 * draw the graphics
	 * @param g
	 */
	public void render(Graphics g, Handler handler){
		if (game.IsgameStateMenu()) {
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 10);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Sokoban", 205, 90);

			g.setFont(fnt2);
			g.drawRect(210 - bouncer/2, 150 - bouncer/2, 200 + bouncer, 64 + bouncer);
			g.drawString("Play", 270, 190);

			g.drawRect(210 - bouncer/2, 250 - bouncer/2, 200 + bouncer, 64 + bouncer);
			g.drawString("Help", 270, 290);

			g.drawRect(210 - bouncer/2, 350 - bouncer/2, 200 + bouncer, 64 + bouncer);
			g.drawString("Quit", 270, 390);
			
			g.setFont(fnt3);
			g.setColor(Color.RED);
			g.drawString("Theme 1", 530, 440);
			g.setColor(Color.CYAN);
			g.drawString("Theme 2", 580, 440);
			if (handler.getTheme() != 1) {
				g.setColor(Color.RED);
				g.drawRect(527, 430, 47, 15);
			} else {
				g.setColor(Color.CYAN);
				g.drawRect(577, 430, 47, 15);
			}
			
			
		} else if (game.IsgameStateHelp()) {
			
			Font fnt = new Font("arial", 1, 30);
			
			Toolkit t = Toolkit.getDefaultToolkit();
			Image i = null;
			
			if (handler.getTheme() != 1) {
				i = t.getImage("../images/help.png");
			} else {
				i = t.getImage("../images/help1.png");
			}
		
	        g.drawImage(i, 0, 0, null);
			
			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawRect(210 - bouncer/2, 350 - bouncer/2, 200 + bouncer, 64 + bouncer);
			g.drawString("Back", 270, 390);
			
			
		} else if (game.IsgameStatePlayerMenu()){
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Sokoban", 205, 90);

			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Single Player", 217, 190);

			g.drawRect(210, 250, 200, 64);
			g.drawString("2 Player", 255, 290);

			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		} else if (game.isgameStateLevelTypeMenu()){
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 25);


			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Sokoban", 205, 90);

			g.setFont(fnt2);
			g.drawRect(210, 150, 200, 64);
			g.drawString("Select Level", 223, 190);

			g.setFont(fnt3);
			g.drawRect(210, 250, 200, 64);
			g.drawString("Random Level", 225, 290);

			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		} else if (game.isgameStateLevelSelectMenu()){
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 25);
			Font fnt4 = new Font("arial", 1, 25);
			
			g.setFont(fnt4);
			g.setColor(Color.white);
			g.drawString("0", 90, 225);
			g.drawRect(80, 200, 32, 32);
			
			g.drawString("1", 310, 175);
			g.drawString("2", 360, 175);
			g.drawString("3", 410, 175);
			g.drawString("4", 460, 175);
			
			g.drawString("5", 310, 225);
			g.drawString("6", 360, 225);
			g.drawString("7", 410, 225);
			g.drawString("8", 460, 225);
			
			g.drawString("9", 310, 275);
			g.drawString("10", 353, 275);
			g.drawString("11", 403, 275);
			g.drawString("12", 452, 275);

			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Sokoban", 205, 90);
			
			g.setFont(fnt3);
			g.drawString("Levels", 350, 130);
			
			g.drawString("Tutorial", 48, 175);
			
			g.setColor(Color.GREEN);
			g.drawString("Easy", 200, 175);
			g.drawRect(350, 150, 32, 32);
			g.drawRect(400, 150, 32, 32);
			g.drawRect(450, 150, 32, 32);
			g.drawRect(300, 150, 32, 32);
			
			g.setColor(Color.orange);
			g.drawString("Medium", 200, 225);
			g.drawRect(350, 200, 32, 32);
			g.drawRect(400, 200, 32, 32);
			g.drawRect(450, 200, 32, 32);
			g.drawRect(300, 200, 32, 32);
			
			g.setColor(Color.red);
			g.drawString("Hard", 200, 275);
			g.drawRect(350, 250, 32, 32);
			g.drawRect(400, 250, 32, 32);
			g.drawRect(450, 250, 32, 32);
			g.drawRect(300, 250, 32, 32);
			
			
			g.setColor(Color.WHITE);
			g.setFont(fnt2);
			g.drawRect(210, 350, 200, 64);
			g.drawString("Back", 270, 390);
		} else if (game.isgameStateLevelInbetween()){
			int steps = game.getSpawner().getNumberOfSteps();
			String score = Integer.toString(steps);
			String level = Integer.toString(game.getSpawner().getCurrentLevelNumber() - 1);
			
			Font fnt = new Font("arial", 1, 50);
			Font fnt2 = new Font("arial", 1, 30);
			Font fnt3 = new Font("arial", 1, 20);
			
			
			g.setFont(fnt);
			g.setColor(Color.white);
			g.drawString("Your Score", 190, 90);
			
			g.setFont(fnt2);
			g.drawString("Level", 250, 150);
			g.drawString(level, 335, 150);
			g.setFont(fnt);
			g.drawString(score, 280, 220);
			
			g.setFont(fnt2);
			g.drawRect(90, 300, 200, 64);
			g.drawString("Restart", 145, 340);
			
			g.setFont(fnt2);
			g.drawRect(310, 300, 200, 64);
			g.drawString("Next Level", 325, 340);
			
			g.setFont(fnt3);
			g.drawRect(200, 380, 200, 64);
			g.drawString("Back to level select", 210, 415);
			
		}
	}
	
	public static void startMenuTheme() {
		menuTheme.play();
	}
	
	public static void stopMenuTheme() {
		menuTheme.stop();
	}
}
