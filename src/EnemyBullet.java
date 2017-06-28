import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class EnemyBullet extends GameObject {
	private Handler handler;
	private int timer;
	private boolean disabletimer;

	public EnemyBullet(float x, float y, ID id, int velX, int velY, Handler handler) {
		super(x, y, id);
		this.velX = velX;
		this.velY = velY;
		this.handler = handler;
		this.timer = 0;
		this.disabletimer = false;
	}

	@Override
	public void tick() {
		if (x < 0 || y < 0 || x > Game.WIDTH || y > Game.HEIGHT) {
			handler.removeObject(this);
		}
		if (this.id == id.EnemyBullet) {
			handler.addObject(new Trail(x, y, ID.Trail, Color.RED, 16, 16, 0.05f, handler));
		} else if (this.id == id.SmartBullet) {
			
			if (timer == 50 && disabletimer == false) {
				if (velY != 0) {
					velX = 0;
					velY = 0;
					timer = 0;
				} else {
					fireAtEnemy(handler);
				}
			} else {
				timer++;
			}
			handler.addObject(new Trail(x, y, ID.Trail, Color.MAGENTA, 16, 16, 0.03f, handler));
		}
		x += velX;
		y += velY;
	}
	
	public void fireAtEnemy(Handler handler){
		disabletimer = true;
		//gets the center of player
		float playerx = handler.getPlayer2().getX() + 16;	
		float playery = handler.getPlayer2().getY() + 16;
		
		double diffx = this.x + 8 - playerx;
		double diffy = this.y + 8 - playery;
		
		double angle = Math.atan(diffy/diffx);

		velX =  (-8 * Math.cos(angle));
		velY =  (-8 * Math.sin(angle));
	}

	@Override
	public void render(Graphics g) {
		if(this.id == id.EnemyBullet){
			g.setColor(Color.red);
			g.fillRect((int) x, (int) y, 16, 16);
		} else if (this.id == id.SmartBullet){
			Toolkit t = Toolkit.getDefaultToolkit();
			Image i = null;
			i = t.getImage("../images/darkBolt.png");
	        g.drawImage(i, (int) x, (int) y, this);
		}
		
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

}
