
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.Random;


public class Player extends GameObject {

	Random r = new Random();
	private Handler handler;
	public boolean playerHit, facingLeft;
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.playerHit = false;
		this.theme = null;
		this.facingLeft = false;
	}

	public void tick() {
		//x += velX;
		//y += velY;
		
		//x = Game.clamp(x, Game.WIDTH - 44, 0);
		//y = Game.clamp(y, Game.HEIGHT - 72, 0);
		collision();
	}
	
	private void collision(){
		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getID() == ID.EnemyBullet || tempObject.getID() == ID.SmartBullet) {
				if (getBounds().intersects(tempObject.getBounds())) {
					playerHit = true;
				}
			}
		}
	}

	/**
	 * draws the player
	 */
	public void render(Graphics g) {
		
		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = null;
		if(theme == null) {		//default
			if(!facingLeft){
				i = t.getImage("../images/player.png");
			} else {
				i = t.getImage("../images/player_left.png");
			}
		} else if (theme.equals("Mario")){
			if(!facingLeft){
				i = t.getImage("../images/player.png");
			} else {
				i = t.getImage("../images/player_left.png");
			}
		} else if (theme.equals("BombMan")){
			if(!facingLeft){
				i = t.getImage("../images/player1.png");
			} else {
				i = t.getImage("../images/player1_left.png");
			}
		}
        g.drawImage(i, (int) x, (int) y, this);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int) x,(int) y,32,32);
	}
	
	public void setTheme(String Theme){
		this.theme = Theme;
	}

	public void SetplayerHit(boolean b) {
		this.playerHit = b;
	}


}
