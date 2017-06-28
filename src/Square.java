/**
 * 
 */

/**
 * @author sturgesj
 *
 */
public class Square {
	private String type;
	
	//
	private boolean goal;
	private boolean box;
	private boolean player;
	private boolean wall;
	
	private boolean player2;
	private boolean verticalEnemy;
	private boolean horizontalEnemy;
	
	/**
	 * @param goal
	 * @param box
	 * @param player
	 * @param wall
	 */
	public Square(boolean goal, boolean box, boolean player, boolean wall) {
		super();
		this.type = "";
		this.goal = goal;
		this.box = box;
		this.player = player;
		this.wall = wall;
	}
	
	public Square(String type) {
		this.type = type;
		this.goal = false;
		this.box = false;
		this.player = false;
		this.wall = false;
		this.player2 = false;
		this.verticalEnemy = false;
		this.horizontalEnemy = false;
		
		//
		switch (type) {
			case "box":
				this.box = true;
				break;
			case "wall":
				this.wall = true;
				break;
			case "goal":
				this.goal = true;
				break;
			case "player1":
				this.player = true;
				break;
			case "player2":
				this.player2 = true;
				break;
			case "horizontal":
				this.horizontalEnemy = true;
				break;
			case "vertical":
				this.verticalEnemy = true;
				break;
			case "empty":
				//do nothing
				break;
			default:
				System.err.println("Square construction failed");
		}
	}
	
	public boolean isPushable() {
		return (this.box || this.type.equals("box"));
	}
	public boolean isEmpty() {
		if (!this.box && !this.goal && !this.player && !this.wall) return true;
		else return false;	
	}
	
	public boolean canBeMoveTo() {
		if (this.box == false && this.player == false && this.wall == false) return true;
		else return false;
	}
	
	public boolean isComplete() {
		if (this.box == true && this.goal == false) return false;
		else return true;
	}
	
	public boolean isPlayer() {
		if (this.player == true) return true;
		else return false;
	}
	
	public boolean isPlayer2() {
		return (this.player2 || this.type.equals("player2"));
	}
	
	public boolean isBox() {
		return (this.box || this.type.equals("box"));
	}
	
	public boolean isWall() {
		return (this.wall || this.type.equals("wall"));
	}
	
	public boolean isGoal() {
		return (this.goal || this.type.equals("goal"));
	}
	
	public boolean isVerticalEnemy() {
		return (this.verticalEnemy || this.type.equals("vertical"));
	}
	
	public boolean isHorizontalEnemy() {
		return (this.horizontalEnemy || this.type.equals("horizontal"));
	}
	
	public String getType() {
		return this.type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Square [goal=" + goal + ", box=" + box + ", player=" + player + ", wall=" + wall + "]";
	}


	public Square clone() {
		return new Square(this.goal, this.box, this.player, this.wall);
	}
	
	public Square addPlayer() {
		this.player = true;
		return this;
	}
	
	public Square removePlayer() {
		this.player = false;
		return this;
	}
	
	public Square addBox() {
		this.box = true;
		return this;
	}
	
	public Square removeBox() {
		this.box = false;
		return this;
	}
	
	
	public String Printer() {
		String n = "";
		if (this.box == true) n += "B";
		else if (this.goal == true) n += "G";
		else if (this.player == true) n += "P";
		else if (this.wall == true) n += "W";
		else n += " ";
		
		if (this.box == true && this.wall == true) System.out.println("TEST");
		if (this.player == true && this.wall == true) System.out.println("TEST");
		
		return n;
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
		Square other = (Square) obj;
		if (box != other.box)
			return false;
		if (goal != other.goal)
			return false;
		if (player != other.player)
			return false;
		if (wall != other.wall)
			return false;
		return true;
	}
	
	
	public String encode() {
		if (this.box == true && this.goal == true) return "C";
		else if (this.player == true && this.goal == true) return "S";
		else if (this.box == true) return "B";
		else if (this.goal == true) return "G";
		else if (this.player == true) return "P";
		else if (this.wall == true) return "W";
		else return "E";
	}
	
	public void addGoal() {
		this.goal = true;
	}
	

}
