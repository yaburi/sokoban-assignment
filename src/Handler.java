
import java.awt.Graphics;
import java.util.ArrayList;

public class Handler {

	ArrayList<GameObject> object = new ArrayList<GameObject>();
	public int theme = 0; // 0 for mario and 1 for bombman

	/**
	 * runs the tick function on each object in the Array
	 */
	public void tick() {
		for (int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);

			tempObject.tick();
		}
	}

	/**
	 * runs the render function on each object
	 * 
	 * @param g
	 *            which is a Graphics
	 */
	public void render(Graphics g) {
		if (object.size() != 0) {
			for (int i = 0; i < object.size(); i++) {
				GameObject tempObject = object.get(i);
				if (tempObject != null) {
					tempObject.render(g);
				}

			}
		}
	}

	/**
	 * adds object to the array
	 */
	public void addObject(GameObject object) {
		this.object.add(object);
	}

	/**
	 * remove object from the array
	 * 
	 * @param object
	 */
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}

	// this works when there is only one player2 on the screen
	// if player2 does not exist, then it returns null.
	public GameObject getPlayer2() {
		for (int i = 0; i < object.size(); i++) {
			if (object.get(i).getID() == ID.Player2) {
				return object.get(i);
			}
		}
		return null;
	}

	// only works when there is one box
	// if no boxes exist, then return null
	public ArrayList<GameObject> getBoxes() {
		ArrayList<GameObject> boxes = new ArrayList<GameObject>();
		for (int i = 0; i < object.size(); i++) {
			if (object.get(i).getID() == ID.Box) {
				boxes.add(object.get(i));
			}
		}
		return boxes;
	}

	/**
	 * returns all goal squares in the array
	 * 
	 * @return
	 */
	public ArrayList<GameObject> getGoalSquares() {
		ArrayList<GameObject> GoalSquares = new ArrayList<GameObject>();
		for (int i = 0; i < object.size(); i++) {
			if (object.get(i).getID() == ID.GoalSquare) {
				GoalSquares.add(object.get(i));
			}
		}
		return GoalSquares;
	}

	/**
	 * returns all walls in the array
	 * 
	 * @return
	 */
	public ArrayList<GameObject> getWalls() {
		ArrayList<GameObject> walls = new ArrayList<GameObject>();
		for (int i = 0; i < object.size(); i++) {
			if (object.get(i).getID() == ID.Wall) {
				walls.add(object.get(i));
			}
		}
		return walls;
	}

	public ArrayList<GameObject> getBullets() {
		ArrayList<GameObject> Bullets = new ArrayList<GameObject>();
		for (int i = 0; i < object.size(); i++) {
			if (object.get(i).getID() == ID.EnemyBullet) {
				Bullets.add(object.get(i));
			}
		}
		return Bullets;
	}

	public int getTheme() {
		return theme;
	}

	public void setTheme(int theme) {
		this.theme = theme;
	}
}
