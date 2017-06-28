import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Header extends GameObject {

	private int steps;

	public Header(int x, int y, ID id) {
		super(x, y, id);
		this.steps = 0;
	}

	@Override
	public void tick() {

	}

	@Override
	public void render(Graphics g) {
		if (steps < 100) {
			Font fnt = new Font("arial", 1, 40);

			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(steps), 580, 40);
		} else {
			Font fnt = new Font("arial", 1, 30);

			g.setFont(fnt);
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString(steps), 575, 35);
		}
		
		Font fnt = new Font("arial", 1, 10);
		g.setFont(fnt);
		g.drawString("Q - Quit", 580, 60);
		g.drawString("R - Reset", 580, 75);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

	public void UpdateSteps(int i) {
		this.steps = i;
	}

}
