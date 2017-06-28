import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Spawn {
	private Handler handler;
	private Game game;
	public int currentLevelNumber, numberOfSteps;
	private static Sound s;

	public Spawn(Handler handler, Game game) {
		this.handler = handler;
		this.game = game;
		this.currentLevelNumber = 0;
		this.numberOfSteps = 0;
		this.s = new Sound("../sounds/loop.wav", -1);
	}

	/**
	 * checks if all boxes are on goal squares
	 */
	public void tick() {
		ArrayList<GameObject> GoalSquares = handler.getGoalSquares();
		ArrayList<GameObject> Boxes = handler.getBoxes();

		int i = 0;
		for (GameObject g1 : Boxes) {
			for (GameObject g : GoalSquares) {

				if (g1.getX() == g.getX() && g1.getY() == g.getY()) {
					i++;
					Box temp = (Box) g1;
					temp.setIsOnGoal(true);
					break;
				} else {
					Box temp = (Box) g1;
					temp.setIsOnGoal(false);
				}

			}
		}
		if (i == GoalSquares.size() && i != 0) {
			System.out.println("You Win!");
			handler.object.clear();
			game.setgameState("LevelInbetween");
			numberOfSteps = game.getKeyinput().getCurrentSteps();

			if (currentLevelNumber == 13 || currentLevelNumber == 12) {

				game.setgameState("MainMenu");
				game.removeKeyListener(game.getKeyinput());

			} else if (currentLevelNumber < 12 && currentLevelNumber >= 0) {
				game.removeKeyListener(game.getKeyinput());
				currentLevelNumber++;
				levelLoader(currentLevelNumber, handler);

				game.setKeyinput(new KeyInput(handler, game));
				game.addKeyListener(game.getKeyinput());
			} else {

				game.setgameState("MainMenu");
				game.removeKeyListener(game.getKeyinput());
			}
		}
		if (currentLevelNumber == 11 || currentLevelNumber == 12) {
			if (handler.object.size() != 0) {
				Player temp = (Player) handler.getPlayer2();
				if (temp != null) {
					if (temp.playerHit == true) {
						int currentLevel = game.getSpawner().getCurrentLevelNumber();
						handler.object.clear();
						game.removeKeyListener(game.getKeyinput());
						game.getSpawner().levelLoader(currentLevel, handler);
						game.setKeyinput(new KeyInput(handler, game));
						game.addKeyListener(game.getKeyinput());
					}
				}
			}
		}

	}

	/**
	 * test level
	 * 
	 * @param handler
	 */
	public void loadtest(Handler handler) {
		handler.addObject(new Player(32 * 1, 32 * 1, ID.Player2, handler));

		handler.addObject(new Wall(32 * 8, 32 * 8, ID.Wall));
	}

	/**
	 * loads the corresponding level
	 * 
	 * @param levelNumber
	 *            the level to be loaded -1 to load a random level
	 * @param handler
	 *            the current handler
	 */
	public void levelLoader(int levelNumber, Handler handler) {
		handler.object.clear();
		switch (levelNumber) {
		// -1 means load random level
		case -1:
			Map randomMap = new Map();
			Random r = new Random();
			randomMap.newMap(8);
			// System.out.println(randomMap.getTxtOutputString());
			randomMap.printMap();
			int randLevelID = r.nextInt();
			this.setCurrentLevelNumber(randLevelID);
			String filename = Integer.toString(randLevelID);
			// randomMap.toTxt(filename + ".map");
			// This will break if the filename already exists. Roll them dice
			// baby
			randomMap.writeMapToFile(filename);
			// loadMapFile(handler, randomMap);
			loadPremadeLevel(handler, filename);
			break;
		// Load tutorial level
		case 0:
			loadLevel0(handler);
			break;
		// Load level 11 TODO add this functionality to map reader
		case 11:
			loadLevel11(handler);
			break;
		case 12:
			// Load level 12 TODO see above
			loadLevel12(handler);
			break;
		// Load level 13 TODO see above
		case 13:
			loadLevel13(handler);
			break;
		// Otherwise load level from file
		default:
			loadPremadeLevel(handler, Integer.toString(levelNumber));
			break;
		}

		if (handler.getTheme() == 0) {
			for (GameObject b : handler.object) {
				b.setTheme("Mario");
			}
		} else if (handler.getTheme() == 1) {
			for (GameObject b : handler.object) {
				b.setTheme("BombMan");
			}
		}
		s.play();
	}

	/**
	 * Level loader function for built-in levels
	 * 
	 * @param handler
	 * @param levelID
	 */
	public void loadPremadeLevel(Handler handler, String levelID) {

		if (handler == null)
			System.err.println("handler is null");
		if (levelID == null) {
			System.err.println("levelID is null");
		} else
			System.out.println(levelID);
		loadMapFile(handler, new Map(levelID));
	}

	public void loadMapFile(Handler handler, Map map) {
		// Load goal squares
		if (map == null)
			System.err.println("Map is null you moron");
		for (int i = 0; i < map.getSize(); i++) {
			for (int j = 0; j < map.getSize(); j++) {
				if (map.getSquare(i, j).isGoal())
					handler.addObject(new GoalSquare(32 * i, 32 * j, ID.GoalSquare, handler));
			}
		}
		// Load Player(s)
		for (int i = 0; i < map.getSize(); i++) {
			for (int j = 0; j < map.getSize(); j++) {
				if (map.getSquare(i, j).isPlayer())
					handler.addObject(new Player(32 * i, 32 * j, ID.Player2, handler));
			}
		}
		// Load Boxes
		for (int i = 0; i < map.getSize(); i++) {
			for (int j = 0; j < map.getSize(); j++) {
				if (map.getSquare(i, j).isBox())
					handler.addObject(new Box(32 * i, 32 * j, ID.Box));
			}
		}
		// Load Walls
		for (int i = 0; i < map.getSize(); i++) {
			for (int j = 0; j < map.getSize(); j++) {
				if (map.getSquare(i, j).isWall())
					handler.addObject(new Wall(32 * i, 32 * j, ID.Wall));
			}
		}

		// Load Enemies
		for (int i = 0; i < map.getSize(); i++) {
			for (int j = 0; j < map.getSize(); j++) {

			}
		}
	}

	private void loadLevel12(Handler handler) {
		loadbackground(handler);
		for (int i = 2; i < 17; i++) {
			handler.addObject(new Wall(32 * i, 32 * 1, ID.Wall));
			handler.addObject(new Wall(32 * i, 32 * 12, ID.Wall));
		}

		for (int i = 1; i < 13; i++) {
			handler.addObject(new Wall(32 * 1, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 17, 32 * i, ID.Wall));
		}

		handler.addObject(new Wall(32 * 1, 32 * 1, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 12, ID.Wall));

		for (int i = 3; i < 11; i += 2) {
			handler.addObject(new GoalSquare(32 * 14, 32 * i, ID.GoalSquare, handler));
		}
		for (int i = 3; i < 11; i += 2) {
			handler.addObject(new Box(32 * 4, 32 * i, ID.Box));
		}

		handler.addObject(new Player(32 * 9, 32 * 7, ID.Player2, handler));

		handler.addObject(new EnemyVertical(32 * 18, 32 * 7, ID.SmartEnemy, handler));

	}

	/**
	 * hard coded for level 13
	 */

	public void loadLevel13(Handler handler) {
		handler.addObject(new Player(32, 32, ID.Player, handler));
		handler.addObject(new Player(32, 32 * 4, ID.Player2, handler));

		for (int i = 0; i < 8; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
			if (i != 4) {
				handler.addObject(new Wall(32 * i, 32 * 3, ID.Wall));
			}
			handler.addObject(new Wall(32 * i, 32 * 6, ID.Wall));
		}

		for (int i = 1; i < 6; i++) {
			if (i != 3) {
				handler.addObject(new Wall(32 * 7, 32 * i, ID.Wall));
				handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			}
		}

		handler.addObject(new GoalSquare(32 * 6, 32 * 4, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 4, 32 * 5, ID.GoalSquare, handler));

		handler.addObject(new Box(32 * 4, 32 * 3, ID.Box));
		handler.addObject(new Box(32 * 4, 32 * 4, ID.Box));
	}

	

	public void loadLevel11(Handler handler) {
		for (int i = 2; i < 17; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
			handler.addObject(new Wall(32 * i, 32 * 12, ID.Wall));
		}

		for (int i = 0; i < 13; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 17, 32 * i, ID.Wall));
		}

		handler.addObject(new Wall(32 * 1, 32 * 0, ID.Wall));
		handler.addObject(new Wall(32 * 1, 32 * 12, ID.Wall));

		for (int i = 1; i < 13; i += 2) {
			handler.addObject(new GoalSquare(32 * 16, 32 * i, ID.GoalSquare, handler));
		}
		for (int i = 1; i < 13; i += 2) {
			handler.addObject(new Box(32 * 2, 32 * i, ID.Box));
		}

		handler.addObject(new Player(32 * 1, 32 * 1, ID.Player2, handler));

		handler.addObject(new EnemyVertical(32 * 18, 32 * 1, ID.Enemy, handler));
		handler.addObject(new EnemyHorizontal(32 * 1, 32 * 13, ID.Enemy, handler));
	}

	public void loadLevel0(Handler handler) {
		for (int i = 0; i < 12; i++) {
			handler.addObject(new Wall(32 * i, 32 * 0, ID.Wall));
			handler.addObject(new Wall(32 * i, 32 * 11, ID.Wall));
		}
		for (int i = 0; i < 11; i++) {
			handler.addObject(new Wall(32 * 0, 32 * i, ID.Wall));
			handler.addObject(new Wall(32 * 11, 32 * i, ID.Wall));
		}

		handler.addObject(new Wall(32 * 5, 32 * 7, ID.Wall));
		handler.addObject(new Wall(32 * 6, 32 * 7, ID.Wall));

		handler.addObject(new Wall(32 * 3, 32 * 6, ID.Wall));
		handler.addObject(new Wall(32 * 3, 32 * 5, ID.Wall));

		handler.addObject(new Wall(32 * 8, 32 * 6, ID.Wall));
		handler.addObject(new Wall(32 * 8, 32 * 5, ID.Wall));

		handler.addObject(new GoalSquare(32 * 5, 32 * 9, ID.GoalSquare, handler));
		handler.addObject(new GoalSquare(32 * 6, 32 * 9, ID.GoalSquare, handler));

		handler.addObject(new Box(32 * 5, 32 * 5, ID.Box));
		handler.addObject(new Box(32 * 6, 32 * 5, ID.Box));

		handler.addObject(new Player(32 * 1, 32 * 1, ID.Player2, handler));
	}

	public int getCurrentLevelNumber() {
		return currentLevelNumber;
	}

	public void setCurrentLevelNumber(int currentLevelNumber) {
		this.currentLevelNumber = currentLevelNumber;
	}

	public int getNumberOfSteps() {
		return numberOfSteps;
	}

	public void setNumberOfSteps(int numberOfSteps) {
		this.numberOfSteps = numberOfSteps;
	}

	public static void stopLoopMusic() {
		s.stop();
	}

	public static void startLoopMusic() {
		s.play();
	}

	public void loadbackground(Handler handler) {
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 14; j++) {
				handler.addObject(new FloorTile(32 * i, 32 * j, ID.FloorTile));
			}
		}
	}
}
