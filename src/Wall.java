import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class Wall extends GameObject{

	public Wall(int x, int y, ID id) {
		super(x, y, id);
		this.theme = null;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * loads image to draw
	 */
	@Override
	public void render(Graphics g) {
		g.setColor(Color.darkGray);
//		g.fillRect(x, y, 32, 32);
		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = null;
		if(theme == null) {		//default
        	i = t.getImage("../images/wall.png");
		} else if (theme.equals("Mario")){
			i = t.getImage("../images/wall.png");
		} else if (theme.equals("BombMan")){
			i = t.getImage("../images/wall1.jpg");
		}
        g.drawImage(i, (int) x, (int) y, this);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setTheme(String Theme){
		this.theme = Theme;
	}

}
