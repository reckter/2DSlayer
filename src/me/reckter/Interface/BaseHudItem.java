package me.reckter.Interface;

import me.reckter.Engine.Engine;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/1/13
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseHudItem extends BaseInterface {

	protected int fixedX;
	protected int fixedY;

	public BaseHudItem(Engine enginge) {
		super(enginge);
	}

	@Override
	public void render(Graphics g) {
		x = fixedX - engine.getCamX();
		y = fixedX - engine.getCamY();
	}
}
