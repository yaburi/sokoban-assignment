import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;

public class GoalSquare extends GameObject {

	private Handler handler;
	public boolean BoxOnGoal;

	public GoalSquare(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.BoxOnGoal = false;
		this.theme = null;
	}

	@Override
	// can determine whether a box is on the Goal square
	public void tick() {
		ArrayList<GameObject> boxes = handler.getBoxes();
		BoxOnGoal = false;
		for (GameObject g : boxes) {
			if (g.getX() == x && g.getY() == y) {
				if (!BoxOnGoal) {
					BoxOnGoal = true;
				}
			} else {
				BoxOnGoal = false;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		// g.setColor(Color.green);
		// g.fillRect(x, y, 32, 32);

		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = null;
		if (theme == null) { // default
			i = t.getImage("../images/goal.png");
		} else if (theme.equals("Mario")) {
			i = t.getImage("../images/goal.png");
		} else if (theme.equals("BombMan")) {
			i = t.getImage("../images/goal1.png");
		}
		g.drawImage(i, (int) x, (int) y, this);

	}

	// getter
	public boolean getBoxOnGoal() {
		return BoxOnGoal;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTheme(String Theme) {
		this.theme = Theme;
	}
}
