package me.reckter.Interface;

import me.reckter.Engine.Engine;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/1/13
 * Time: 7:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseInterface {

	public int x;
	public int y;
	public int width;
	public int height;
	protected Engine engine;

	public BaseInterface(Engine enginge){
		this.engine = enginge;
	}

	public Rectangle getBoundaries(){
		return new Rectangle(x,y,width,height);
	}

	public boolean isAlive(){
		return true;
	}

	public void update(int delta){

	}

	public void render(Graphics g){

	}

}
