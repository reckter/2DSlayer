package me.reckter.Interface;

import me.reckter.Engine.Engine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/1/13
 * Time: 7:48 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseText extends BaseInterface {

	public String text;
	public BaseText(Engine enginge, String text) {
		super(enginge);
		this.text = text;
	}

	public BaseText(Engine engine, String text, int x, int y){
		this(engine,text);
		this.x = x;
		this.y = y;
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawString(text, x + engine.getCamX() , y + engine.getCamY());
	}
}
