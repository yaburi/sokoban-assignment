import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 */

/**
 * @author sturgesj
 *
 */
public class Map {
	
	//private ArrayList<ArrayList<String>> map;
	private Square puzzel[][];
	private int size;
	private int movesToSolve;
	private String path;
	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;
	
	public Map() {
		this.path = "";
		
		//this.generateMap(10);
		//this.generateMap(10);
	}
	
	/**
	 * Reads a map from file name.map
	 * Assumes the parameter name is a file with the side width of the map on the
	 * first line, and then the map in text form below it
	 * @param name
	 */
	public Map(String name) {
		//Still rocking the sample code from assignment 1
		
		Scanner sc = null;
		try {
			sc = new Scanner(new FileReader("../maps/" + name + ".map"));
		} catch (FileNotFoundException e) {System.err.println("Error opening file: " + e);}
		String input;
		int j = 0;
		//Read the first line of the infile, which should be the size of the map
		this.size = Integer.parseInt(sc.nextLine());
		this.puzzel = new Square[this.size][this.size];
		//Read the file line by line and populate the puzzel array row by row
		while (sc.hasNext()) {
			input = sc.nextLine();
			System.out.println(input);
			for (int i = 0; i < this.size; i++) {
				switch (input.charAt(i)) {
				case 'W':
					this.puzzel[i][j] = W();
					break;
				case 'G':
					this.puzzel[i][j] = G();
					break;
				case 'E':
					this.puzzel[i][j] = E();
					break;
				case 'B':
					this.puzzel[i][j] = B();
					break;
				//The function P() will have to be expanded for different players
				case '2':
					this.puzzel[i][j] = P();
					break;
				case 'P':
					this.puzzel[i][j] = P();
					break;
				case 'S':
					this.puzzel[i][j] = new Square(true, false, true, false);
					break;
				case 'C':
					this.puzzel[i][j] = new Square(true, true, false, false);
					break;
				default:
					System.err.println("Invalid file input. Brace for NPE");
				}
			}
			//Next row
			j++;
		}
		
		if (sc != null) sc.close();
	}
	
	
	
	/**
	 * The constructor for the Map class
	 * @param width the dimensions of the Map
	 * @return the built map
	 */
	public Map newMap(int width) {
		this.path = "";
		this.size = width;
		
		String soultion = null;
		
		if (this.generateMap(width)) soultion = this.findSoultion();

		
		while(soultion == null || soultion.length() <width) {

			if (this.generateMap(width)) {
				soultion = this.findSoultion();
			} else soultion = null;
		}
		
		this.removeUnnesEmpty(soultion);
		this.getRidBadWalls();
		return this;

	}
	
	/**
	 * Builds map predefined by the function to test the solver.
	 * 
	 */
	public void turnToHackMap() {
		this.puzzel = new Square[][] {
			{W(),W(),W(),W(),W(),W(),W(),W()},
			{W(),G(),G(),G(),E(),E(),W(),W()},
			{W(),E(),B(),P(),B(),E(),W(),W()},
			{W(),W(),E(),W(),W(),E(),E(),W()},
			{W(),W(),E(),E(),B(),E(),E(),W()},
			{W(),W(),E(),E(),W(),E(),E(),W()},
			{W(),W(),W(),W(),W(),W(),W(),W()},
			{W(),W(),W(),W(),W(),W(),W(),W()}


		};
//		this.puzzel = new Square[][] {
//			{W(),W(),W(),W(),W()},
//			{W(),E(),B(),G(),W()},
//			{W(),P(),B(),E(),W()},
//			{W(),E(),E(),G(),W()},
//			{W(),W(),W(),W(),W()}
//		};		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Map [puzzel=" + Arrays.toString(puzzel) + ", movesToSolve=" + movesToSolve + "]";
	}

	/**
	 * builds a Square object that is a wall
	 * @return the built wall
	 */
	private Square W() {
		return new Square(false, false, false, true);
	}
	
	/**
	 * builds a Square object that is a player
	 * @return the built player
	 */
	private Square P() {
		return new Square(false, false, true, false);
	}

	/**
	 * builds a Square object that is a box
	 * @return the built box
	 */
	private Square B() {
		return new Square(false, true, false, false);
	}
	
	/**
	 * builds a Square object that is a goal
	 * @return the built goal
	 */
	private Square G() {
		return new Square(true, false, false, false);
	}
	
	/**
	 * builds a Square object that is a empty
	 * @return the built empty
	 */
	private Square E() {
		return new Square(false, false, false, false);
	}
	
	


	/**
	 * checks whether every box in a given map is on a goal
	 * @return whether every box is on a goal
	 */
	private boolean isDone() {
		boolean flag = true;
		for(int y =0; y < puzzel.length; y++) {
			for(int x =0; x < puzzel[0].length; x++) {
				if (!puzzel[x][y].isComplete()) flag = false;
			}
		}

		return flag;
	}

	/**
	 * Checks whether a given move is valid
	 * @param direction the direction of the move
	 * @return whether the move is valid
	 */
	private boolean isValidMove (int direction) {
		
		int[] fromLoc = this.getPlayerLocation();
		int xFromCoord = fromLoc[0];
		int yFromCoord = fromLoc[1];
		
		int[] toLoc = this.getToCoord(direction, xFromCoord, yFromCoord);
		int xToCoord = toLoc[0];
		int yToCoord = toLoc[1];
		
		if (this.puzzel[yToCoord][xToCoord].canBeMoveTo()) return true;
		else if (this.puzzel[yToCoord][xToCoord].isPushable()) {
			return canPushBox(direction, xToCoord, yToCoord);
		}
		
		else return false;
	}
	
	/**
	 * Checks whether a box can be pushed a given direction
	 * @param direction the direction which the user wish's to push the box
	 * @param xFromCoord the xCoord of the player
	 * @param yFromCoord the yCoord of the player
	 * @return whether the box can be pushed the given direction
	 */
	private boolean canPushBox(int direction, int xFromCoord, int yFromCoord) {
		boolean flag;
		int[] toLoc = this.getToCoord(direction, xFromCoord, yFromCoord);
		int xToCoord = toLoc[0];
		int yToCoord = toLoc[1];
		
		if (this.puzzel[yToCoord][xToCoord].canBeMoveTo()) flag = true;
		else flag = false;
		
		return flag;
	}

	
	/**
	 * gets the coords if a player or box moves from a certain set of coords in a given direction.
	 * @param direction the direction the user wishes to moves
	 * @param xFromCoord
	 * @param yFromCoord
	 * @return
	 */
	private int[] getToCoord(int direction, int xFromCoord, int yFromCoord) {
		int[] toCoords = new int[2];
		if (direction == UP) {
			toCoords[0] = xFromCoord;
			toCoords[1] = yFromCoord - 1;
		}
		if (direction == DOWN) {
			toCoords[0] = xFromCoord;
			toCoords[1] = yFromCoord + 1;
		}
		if (direction == LEFT) {
			toCoords[0] = xFromCoord - 1;
			toCoords[1] = yFromCoord;
		}
		if (direction == RIGHT) {
			toCoords[0] = xFromCoord + 1;
			toCoords[1] = yFromCoord;
		}
		
		return toCoords;
	}
	
	/**
	 * Gets the coords of the player
	 * @return the location of the player stored in an array of the format [ycoord, xcoord]
	 */
	private int[] getPlayerLocation() {
		int[] location = new int[2];
		for(int y =0; y < puzzel.length; y++) {
			for(int x =0; x < puzzel[0].length; x++) {
				if (puzzel[y][x].isPlayer()) {
					location[0] = x;
					location[1] = y;
				}
			}
		}
		return location;
	}

	
	
	/**
	 * Clones a given Map
	 */
	public Map clone() {
		Map newMap = new Map();
		newMap.puzzel = new Square[this.puzzel.length][this.puzzel[0].length];
		newMap.path = this.path;
		
		for (int y = 0; y < this.puzzel.length; y++) {
			for (int x=0; x < this.puzzel[0].length; x++) {
				newMap.puzzel[y][x] = this.puzzel[y][x].clone();
			}
		}
		newMap.movesToSolve = this.movesToSolve;
		return newMap;
	}

	/**
	 * moves the player a given direction.
	 * @param direction the direction to move the player
	 * @return the Map object after the player has moved
	 */
	private Map move(int direction) {
		if (this.isValidMove(direction)) {

			int[] fromLoc = this.getPlayerLocation();
			int xFromCoord = fromLoc[0];
			int yFromCoord = fromLoc[1];
			
			int[] toLoc = this.getToCoord(direction, xFromCoord, yFromCoord);
			int xToCoord = toLoc[0];
			int yToCoord = toLoc[1];

			if (!this.puzzel[yToCoord][xToCoord].canBeMoveTo()) {
				this.puzzel[yToCoord][xToCoord].removeBox();
				//this.puzzel[yToCoord + yToCoord - yFromCoord][xToCoord + xToCoord - yFromCoord].addBox();	
				this.puzzel[this.getToCoord(direction, xToCoord, yToCoord)[1]][this.getToCoord(direction, xToCoord, yToCoord)[0]].addBox();
			}
			
			
			this.puzzel[yFromCoord][xFromCoord].removePlayer();
			this.puzzel[yToCoord][xToCoord].addPlayer();

			return this;
		} else return this;
	}
	
	
	/**
	 * Returns the path one must take to solve the puzzle.
	 * @return the path to solve the puzzle null if there is no solution.
	 */
	public String findSoultion() {
		Queue<Map> q = new LinkedBlockingQueue<Map>();
		ArrayList<Map> l = new ArrayList<Map>();
		
		Map currentMap = this;
		Map currentPush = null;
		l.add(currentMap);
		for (int direction=0; direction < 4; direction ++) {
			if (currentMap.isValidMove(direction)) {
				currentPush = currentMap.clone().move(direction);
				currentPush.movesToSolve++;
				currentPush.path = currentPush.path + Integer.toString(direction);
			

				if (!currentPush.boxIsStuck() && !l.contains(currentPush)) {
					
					q.add(currentPush);
					l.add(currentPush);
				}
			}
		}
		



		while ( !q.isEmpty() && !currentMap.isDone()) {
			currentMap = q.poll();
			//currentMap.printMap();

			
			for (int direction=0; direction < 4; direction ++) {
				if (currentMap.isValidMove(direction)) {
					currentPush = currentMap.clone().move(direction);
					currentPush.movesToSolve++;
					currentPush.path = currentPush.path + Integer.toString(direction);
					if (!l.contains(currentPush) && !currentPush.boxIsStuck()) {
						q.add(currentPush);
						l.add(currentPush);
					}
				}
			}

		}
		if (currentMap.isDone()) return currentMap.path;
		else return null;
	}
	
	
	/**
	 * checks whether any of the boxes in the map are in a location where they can no longer be moved.
	 * @return
	 */
	private boolean boxIsStuck() {
		
		for (int y = 0; y < this.puzzel.length; y++) {
			for (int x = 0; x < this.puzzel[y].length; x++) {

				if (this.isStuck(y,x)) return true;

			}
		}
		
		
		return false;
	}
	
	
	/**
	 * checks whether a given box is stuck
	 * @param y the y coord of the box
	 * @param x the x coord of the box
	 * @return whether the box is stuck
	 */
	private boolean isStuck(int y, int x) {
		if (this.puzzel[y][x].isGoal()) return false;
		if (this.puzzel[y][x].isPushable()) {
			if (this.puzzel[y+1][x].isWall() && this.puzzel[y][x+1].isWall()) return true;
			if (this.puzzel[y+1][x].isWall() && this.puzzel[y][x-1].isWall()) return true;
			if (this.puzzel[y-1][x].isWall() && this.puzzel[y][x+1].isWall()) return true;
			if (this.puzzel[y-1][x].isWall() && this.puzzel[y][x-1].isWall()) return true;
		}
		
		return false;
	}
	
	/**
	 * Used to print the Map
	 */
	public void printMap() {
		for(int y =0; y < puzzel.length; y++) {
			for(int x =0; x < puzzel[0].length; x++) {
				System.out.printf(this.puzzel[y][x].Printer() + " ");
				
			}
			System.out.printf("\n");
		}
	}
	
	public void writeMapToFile(String mapID) {
		try {
			PrintWriter writer = new PrintWriter("../maps/" + mapID + ".map", "UTF-8");
			System.out.println("printing to file ../maps/" + mapID + ".map");
			writer.println(this.getSize());
			System.out.println(this.getSize());

			for (int i = 0; i < this.getSize(); i++) {
				System.out.print("Adding line: ");
				for (int j = 0; j < this.getSize(); j++) {
					writer.print(this.getSquare(i, j).encode());
					System.out.print(this.getSquare(i, j).encode());
				}
				writer.print('\n');
				System.out.print('\n');
			}
			writer.close();
			System.out.println("FileWriter closed");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Map other = (Map) obj;
		if (!Arrays.deepEquals(puzzel, other.puzzel))
			return false;
		return true;
	}

	
	/**
	 * outputs the map to a txt
	 * @param fileName the name of the txt to output to.
	 */
	public void toTxt(String fileName) {
		try{
		    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
		    writer.println(this.getTxtOutputString());
		    writer.println(this.path);
		    writer.close();
		} catch (IOException e) {
		   // do something
		}
	}
	
	/**
	 * encodes a map into a string
	 * @return the encoded map
	 */
	public String getTxtOutputString() {
		String encode = "";
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				encode += this.puzzel[y][x].encode();
			}
		}
		
		
		return encode;
	}

	
	/**
	 * builds a random Map may or may not be solvable
	 * @param width the dimensions of the map
	 * @return whether the map is solvable
	 */
	private boolean generateMap(int width) {
		this.puzzel = new Square[width][width];
		//this.printMap();
		

			
		for (int y =0; y< this.puzzel.length; y++) {
			this.puzzel[y][0] = W();
			this.puzzel[y][this.puzzel[y].length-1] = W();
		}
		
		for (int x =0; x< this.puzzel.length; x++) {
			this.puzzel[0][x] = W();
			this.puzzel[this.puzzel.length-1][x] = W();
		}
		
		int addfactor = 0;
		if (this.puzzel.length%2 == 1) addfactor = 1;

		for (int d=1; d<this.puzzel.length/2 + addfactor; d++) {
			for (int y =d; y< this.puzzel.length-d; y++) {
				this.popSquare(y,d);
				this.popSquare(y,this.puzzel[y].length-1 -d);
			}
			
			for (int x =d; x< this.puzzel.length-d; x++) {
				this.popSquare(d,x);
				this.popSquare(this.puzzel.length-1-d,x);
			}
		}
		
		this.makeOneChamber();	
		
		return this.placeKeyObjectives(width);
		
	}
	
	/**
	 * places goals players and boxes on the map
	 * @param width the dimensions of the map
	 * @return whether the place was successful
	 */
	private boolean placeKeyObjectives(int width) {
		int noBoxes = howManyBoxes(width);
		
		for (int i =0; i < noBoxes; i++) {
			if (!this.placeBox()) return false;
		}
		for (int i =0; i < noBoxes; i++) {
			if (!this.placeGoals()) return false;
		}
		
		if (!this.placePlayer()) return false;
		
		return true;
	}
	
	
	/**
	 * Places goals
	 * @return whether the method was successful.
	 */
	private boolean placeGoals() {
		Random r = new Random();
		int noEmptySquares =0;
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty()) noEmptySquares++;
			}
		}
		
		if (noEmptySquares == 0) return false;
		
		boolean flag = false;
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty() && flag == false) {
					boolean val = r.nextInt(noEmptySquares)==0;
					if (val) {
						this.puzzel[y][x].addGoal();
						flag = true;
					}
					noEmptySquares--;
				}
			}
		}
		
		
		return true;
	}
	
	/**
	 * Places Player
	 * @return whether the method was successful.
	 */
	private boolean placePlayer() {
		int noEmptySquares = 0;
		Random r = new Random();
		
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty()) noEmptySquares+= 1;
			}
		}
		if (noEmptySquares == 0) return false;
		boolean flag = false;
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty() && flag == false) {
					boolean val = r.nextInt(noEmptySquares)==0;
					if (val) {
						System.out.println(this.puzzel[y][x].isWall());
						System.out.println(this.puzzel[y][x].isPlayer());
						System.out.println(this.puzzel[y][x].isGoal());
						System.out.println("TEST");
						this.puzzel[y][x].addPlayer();
						System.out.println(this.puzzel[y][x].isPlayer());
						flag = true;
					}
					noEmptySquares--;
				}
			}
		}
		
		
		
		return true;
	}
	

	/**
	 * Places Box
	 * @return whether the method was successful.
	 */
	private boolean placeBox() {
		int weightEmptySquares = 0;
		Random r = new Random();
		
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty()) {
					weightEmptySquares+= this.findWeightEmptySquare(y, x);
				}
			}
		}		
		
		if (weightEmptySquares == 0) return false;
		boolean flag = false;
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isEmpty()){
					
					
					float numer = (float) this.findWeightEmptySquare(y, x);
					float denom = (float) weightEmptySquares;
					
					boolean val = r.nextFloat() < numer/denom;
					
					
					if (flag == false && val) {
						this.puzzel[y][x].addBox();
						flag = true;
						
					}
					weightEmptySquares-=this.findWeightEmptySquare(y, x);
					
				}
			}
		}
		
		//System.out.println(weightEmptySquares);
		return true;
	}
	
	/**
	 * Calculates how much a given square should be weight when trying to place a box there
	 * @param y the ycoord of the box
	 * @param x the xcoord of the box
	 * @return
	 */
	private int findWeightEmptySquare(int y, int x) {
		int noTouchedWalls = 0;
		if (this.puzzel[y+1][x] != null && this.puzzel[y+1][x].isWall()) noTouchedWalls++;
		if (this.puzzel[y-1][x] != null && this.puzzel[y-1][x].isWall()) noTouchedWalls++;
		if (this.puzzel[y][x+1] != null && this.puzzel[y][x+1].isWall()) noTouchedWalls++;
		if (this.puzzel[y][x-1] != null && this.puzzel[y][x-1].isWall()) noTouchedWalls++;
				
		if (noTouchedWalls==3) {
			return 0;
		}
		
		if (noTouchedWalls == 2) {
			if ((this.puzzel[y+1][x].isWall() && this.puzzel[y-1][x].isWall())||(this.puzzel[y][x+1].isWall() && this.puzzel[y][x-1].isWall())) return 1;
			else return 0;
		}
		
		if (noTouchedWalls == 1) {
			return 1;
		}
		
		if (noTouchedWalls == 0) {
			return 1;
		}
		
		return 0;
	}
	
	/**
	 * Decides how many boxes to place on a given map
	 * @param width the width of the map
	 * @return the number of boxes to place.
	 */
	private int howManyBoxes(int width){
		int maxBoxes = width/3 + 1;
		int noBoxes = 1;
		Random r = new Random();
		for (int i=0; i < maxBoxes-1; i++) {
			boolean val = r.nextInt(2)==0;
			if (val) noBoxes++;
		}
		
		return noBoxes;
	}
	
	/**
	 * Decides whether to place a empty square or a wall in a square.
	 * @param y the y coord of the square to pop.
	 * @param x the x coord of the square to pop.
	 */
	private void popSquare(int y, int x) {
		int noTouchedWalls =0;
		Random r = new Random();
		if (this.puzzel[y+1][x] != null && this.puzzel[y+1][x].isWall()) noTouchedWalls++;
		if (this.puzzel[y-1][x] != null && this.puzzel[y-1][x].isWall()) noTouchedWalls++;
		if (this.puzzel[y][x+1] != null && this.puzzel[y][x+1].isWall()) noTouchedWalls++;
		if (this.puzzel[y][x-1] != null && this.puzzel[y][x-1].isWall()) noTouchedWalls++;
		

		if (noTouchedWalls==4) this.puzzel[y][x] = W();
		
		if (noTouchedWalls==3) {
			boolean val = r.nextInt(1)==0;
			if (val == true) this.puzzel[y][x] = E();
			else this.puzzel[y][x] = W();
		}
		
		if (noTouchedWalls == 2) {
			if ((this.puzzel[y+1][x] != null && this.puzzel[y+1][x].isWall() && this.puzzel[y-1][x] != null && this.puzzel[y-1][x].isWall())||(this.puzzel[y][x+1] != null && this.puzzel[y][x+1].isWall() && this.puzzel[y][x-1] != null && this.puzzel[y][x-1].isWall())) this.puzzel[y][x] =E();
			else {
				boolean val = r.nextInt(1)==0;
				if (val == true) this.puzzel[y][x] = E();
				else this.puzzel[y][x] = W();
			}
		}
		
		if (noTouchedWalls == 1) {
			boolean val = r.nextInt(2)==0;
			if (val == true) this.puzzel[y][x] = E();
			else this.puzzel[y][x] = W();
		}
		
		if (noTouchedWalls == 0) {
			boolean val = r.nextInt(3)==0;
			if (val == true) this.puzzel[y][x] = E();
			else this.puzzel[y][x] = W();
		}
		
		//System.out.println("test");
		//this.puzzel[y][x] = E();
	}
	
	/**
	 * Gets rid of all continuous empty spaces in the map besides the largest
	 * @return the now modified map
	 */
	private Map makeOneChamber() {
		ArrayList<Integer> chamberSize = new ArrayList<Integer>();
		int[][] chamberMarkers = new int[this.puzzel.length][this.puzzel[0].length];
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				chamberMarkers[y][x] = -1;
			}
		}
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {
				if (!this.puzzel[y][x].isWall() && chamberMarkers[y][x] == -1) this.fillChamber(y,x,chamberSize, chamberMarkers);
			}
		}
		
//		for (int y =0; y< this.puzzel.length; y++) {
//			for (int x =0; x< this.puzzel[y].length; x++) {
//				System.out.print(chamberMarkers[y][x]);
//			}
//			System.out.println("");
//		}
		
		int largestChamber = 0;
		for(int i=0; i < chamberSize.size(); i++) {
			if (chamberSize.get(i) > chamberSize.get(largestChamber)) largestChamber = i;
		}
		
		for (int y =0; y< this.puzzel.length; y++) {
			for (int x =0; x< this.puzzel[y].length; x++) {	
				//System.out.println(chamberMarkers[y]);
				if (chamberMarkers[y][x] != largestChamber) this.puzzel[y][x] = W(); 
			}
		}
		
		
		return this;
	}
	
	/**
	 * fills the chamber marker array for a single chamber with the index of the chamber
	 * @param y the ycoord.
	 * @param x the x coord.
	 * @param chamberSize an array holding the size of a particular chambers size
	 * @param chamberMarkers a 2d array holding the index of the particular chamber which the given square belongs to
	 */
	private void fillChamber(int y, int x, ArrayList<Integer> chamberSize,int[][] chamberMarkers){
		int chamberNumber = chamberSize.size();
		chamberSize.add(1);
		chamberMarkers[y][x] = chamberNumber;
		
		Queue<int[]> q = new LinkedBlockingQueue<int[]>();
		
		int[] currCoord = new int[] {y,x};
		int[] currPush;
		
		currPush = new int[]{currCoord[0]+1,currCoord[1]};

		if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
			chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
			chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
			q.add(currPush);
		}
		currPush = new int[]{currCoord[0]-1,currCoord[1]};
		if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
			chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
			chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
			q.add(currPush);
		}
		currPush = new int[]{currCoord[0],currCoord[1]+1};
		if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
			chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
			chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
			q.add(currPush);
		}
		currPush = new int[]{currCoord[0],currCoord[1]-1};
		if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
			chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
			chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
			q.add(currPush);
		}
		
		while (!q.isEmpty()) {
			currCoord = q.poll();
			
			currPush = new int[]{currCoord[0]+1,currCoord[1]};
			if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
				chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
				chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
				q.add(currPush);
			}
			currPush = new int[]{currCoord[0]-1,currCoord[1]};
			if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
				chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
				chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
				q.add(currPush);
			}
			currPush = new int[]{currCoord[0],currCoord[1]+1};
			if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
				chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
				chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
				q.add(currPush);
			}
			currPush = new int[]{currCoord[0],currCoord[1]-1};
			if (!this.puzzel[currPush[0]][currPush[1]].isWall() && chamberMarkers[currPush[0]][currPush[1]] == -1) {
				chamberSize.set(chamberNumber, chamberSize.get(chamberNumber)+1);
				chamberMarkers[currPush[0]][currPush[1]] = chamberNumber;
				q.add(currPush);
			}
		}
	}


	private void getRidBadWalls() {
		boolean[][] toBeDeleted = new boolean[this.puzzel.length][this.puzzel.length];

		for(int y =0; y < this.puzzel.length; y++) {
			for(int x =0; x < this.puzzel[y].length; x++) {
				if (this.puzzel[y][x].isWall() && this.isBadWall(y,x)) toBeDeleted[y][x] = true;
				else toBeDeleted[y][x] = false;
			}			
		}
		
		for(int y =0; y < this.puzzel.length; y++) {
			for(int x =0; x < this.puzzel[y].length; x++) {
				if (toBeDeleted[y][x] == true) this.puzzel[y][x] = E();
			}
		}
		
		
	}

	private boolean isBadWall(int y, int x) {
		if (y+1 < this.puzzel.length && !this.puzzel[y+1][x].isWall()) return false;
		else if (y-1 > 0 && !this.puzzel[y-1][x].isWall()) return false;
		else if (x+1 < this.puzzel.length && !this.puzzel[y][x+1].isWall()) return false;
		else if (x-1 > 0 && !this.puzzel[y][x-1].isWall()) return false;
		
		else if (y+1 < this.puzzel.length && x+1 < this.puzzel.length && !this.puzzel[y+1][x+1].isWall()) return false;
		else if (y-1 > 0 && x-1 > 0 && !this.puzzel[y-1][x-1].isWall()) return false;
		else if (y+1 < this.puzzel.length && x-1 > 0 && !this.puzzel[y+1][x-1].isWall()) return false;
		else if (y-1 > 0 && x+1 < this.puzzel.length && !this.puzzel[y-1][x+1].isWall()) return false;
		else return true;
	}
	
	private void removeUnnesEmpty(String sol) {
		Map m = this.clone();
		boolean[][] marker = new boolean[this.puzzel.length][this.puzzel[0].length];
		for(int y =0; y < this.puzzel.length; y++) {
			for(int x =0; x < this.puzzel[y].length; x++) {
				marker[y][x] = false;
			}
		}
	
		for(int y =0; y < m.puzzel.length; y++) {
			for(int x =0; x < m.puzzel[y].length; x++) {
				if (!m.puzzel[y][x].isEmpty()) marker[y][x] = true;
			}
		}
		
		
		for (int i=0; i<sol.length(); i++) {
			m.move(Character.getNumericValue(sol.charAt(i)));
			for(int y =0; y < m.puzzel.length; y++) {
				for(int x =0; x < m.puzzel[y].length; x++) {
					if (!m.puzzel[y][x].isEmpty()) marker[y][x] = true;
				}
			}
		}
		
		for(int y =0; y < this.puzzel.length; y++) {
			for(int x =0; x < this.puzzel[y].length; x++) {
				if (marker[y][x] == false) this.puzzel[y][x] = W();
			}
		}
		

	}
	
	public int getSize() {
		return this.size;
	}
	
	public Square getSquare(int x, int y) {
		return this.puzzel[x][y];
	}
}
