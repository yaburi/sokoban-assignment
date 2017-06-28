import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyVertical extends GameObject {

	private Handler handler;
	private int time;
	private Sound s, s1;
	private boolean first;

	public EnemyVertical(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velY = 1;
		this.time = 0;
		this.s = new Sound("../sounds/laser.wav", 1);
		this.s1 = new Sound("../sounds/Magic Dark 2.wav", 1);
		this.first = true;
	}

	@Override
	public void tick() {
		float player_y = handler.getPlayer2().getY();

		if (player_y < this.y) {
			y -= velY;
		} else if (player_y > y) {
			y += velY;
		}

		if (time == 100) {
			time = 0;
			if (this.id == id.Enemy) {
				if (handler.getPlayer2().getX() < x) {
					handler.addObject(new EnemyBullet(x + 8, y + 8, ID.EnemyBullet, -4, 0, handler));
				} else {
					handler.addObject(new EnemyBullet(x + 8, y + 8, ID.EnemyBullet, 4, 0, handler));
				}
				s.play();
			} else if (this.id == id.SmartEnemy) {
				handler.addObject(new EnemyBullet(x + 8, y + 8, ID.SmartBullet, 0, 1, handler));
				handler.addObject(new EnemyBullet(x + 8, y + 8, ID.SmartBullet, 0, -1, handler));
				if (first) {
					first = false;
				} else {
					s1.play();
				}
			}

		} else {
			time++;
		}

	}

	@Override
	public void render(Graphics g) {
		if (this.id == id.Enemy) {
			g.setColor(Color.RED);
		} else if (this.id == id.SmartEnemy) {
			g.setColor(Color.MAGENTA);
		}
		g.fillRect((int) x, (int) y, 32, 32);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}

}
