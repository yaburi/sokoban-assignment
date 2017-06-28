import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class EnemyHorizontal extends GameObject{
	private Handler handler;
	private int time;
	
	public EnemyHorizontal(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		this.velX = 1;
		this.time = 0;
		this.s = new Sound("../sounds/laser.wav", 1);
	}

	@Override
	public void tick() {
		float player_x = handler.getPlayer2().getX();
		
		if(player_x < this.x){
			x -= velX;
		} else if (player_x > x){
			x += velX;
		}
		
		if(time == 100){
			time = 0;
			if(handler.getPlayer2().getY() < y){
				handler.addObject(new EnemyBullet(x + 8,y + 8,ID.EnemyBullet, 0, -4, handler));
			} else {
				handler.addObject(new EnemyBullet(x + 8,y + 8,ID.EnemyBullet, 0, 4, handler));
			}
		} else {
			time++;
		}
		
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int) x, (int) y, 32, 32);
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
}
