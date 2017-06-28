import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Box extends GameObject {

	private boolean isOnGoal;
	private boolean playOnce;

	/**
	 * constructor
	 * 
	 * @param x
	 *            position
	 * @param y
	 *            position
	 * @param id
	 *            always id.box but it is possible to do id.wall if we want to
	 *            trick players
	 */
	public Box(int x, int y, ID id) {
		super(x, y, id);
		this.theme = null;
		this.isOnGoal = false;
		this.s = new Sound("../sounds/Magic Poof.wav", 1);
		this.playOnce = true;
	}

	public void tick() {

	}

	/**
	 * uses the image box.gif to draw the image
	 */
	public void render(Graphics g) {
		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = null;
		try {
			if (theme.equals("Mario") || theme == null) { // default
				if (!isOnGoal) {
					i = t.getImage("../images/box.gif");
				} else {
					i = t.getImage("../images/box_goal.png");
				}
			} else if (theme.equals("BombMan")) {
				if (!isOnGoal) {
					i = t.getImage("../images/box1.gif");
				} else {
					i = t.getImage("../images/box1_goal.png");
				}
			}
		} catch (Exception e) {
			
		}
		g.drawImage(i, (int) x, (int) y, this);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getIsOnGoal() {
		return this.isOnGoal;
	}

	public void setTheme(String Theme) {
		this.theme = Theme;
	}

	public void setIsOnGoal(boolean bool) {
		this.isOnGoal = bool;
		if (bool == true && playOnce == true) {
			s.play();
			playOnce = false;
		}
	}

}
