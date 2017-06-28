import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class FloorTile extends GameObject{

	public FloorTile(float x, float y, ID id) {
		super(x, y, id);
		
	}

	@Override
	public void tick() {
		//Do nothing
		
	}

	@Override
	public void render(Graphics g) {
		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = null;
		if(theme == null) {		//default
        	i = t.getImage("../images/tile_grass.png");
		} else if (theme.equals("Mario")){
			i = t.getImage("../images/tile_grass.png");
		} else if (theme.equals("BombMan")){
			i = t.getImage("../images/tile_concrete.png");
		}
        g.drawImage(i, (int) x, (int) y, this);
		
	}

	@Override
	public Rectangle getBounds() {
		//no rectangle
		return null;
	}

}
