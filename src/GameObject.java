


import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject extends Canvas {
	
	protected float y, x;
	protected ID id;
	protected double velX, velY;
	protected String theme;
	protected Sound s;
	
	public GameObject(float x, float y, ID id){
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	// getters and setters
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public int getX(){
		return (int) x;
	}
	public int getY(){
		return (int) y;
	}
	public void setID(ID id){
		this.id = id;
	}
	public ID getID(){
		return id;
	}
	public void setVelX(int velX){
		this.velX = velX;
	}
	public void setVelY(int velY){
		this.velY = velY;
	}
	public double getVelX(){
		return velX;
	}
	public double getVelY(){
		return velY;
	}
	
	//tells us information about the object
	public boolean canObjectMoveRight(){
		if(x + 32 > Game.WIDTH - 64){
			return false;
		}
		return true;
	}
	
	public boolean canObjectMoveLeft(){
		if(x - 32 < 0){
			return false;
		}
		return true;
	}
	
	public boolean canObjectMoveUp(){
		if(y - 32 < 0){
			return false;
		}
		return true;
	}
	
	public boolean canObjectMoveDown(){
		if(y + 32 > 384){
			return false;
		}
		return true;
	}

	public void setTheme(String string) {
		this.theme = string;
	}
	
}
