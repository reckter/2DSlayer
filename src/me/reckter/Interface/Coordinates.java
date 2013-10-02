package me.reckter.Interface;

import me.reckter.Engine.Engine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/2/13
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class Coordinates extends BaseText {
	int life = 3 * 1000;
	public Coordinates(Engine engine, String text, int x, int y) {
		super(engine, text, x, y);
	}

	@Override
	public void update(int delta) {
		life -= delta;
	}

	@Override
	public boolean isAlive() {
		if(life > 0){
			return true;
		}
		return false;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawString(text, x, y);
	}
}
