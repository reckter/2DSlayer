package me.reckter.Interface;

import me.reckter.Engine.Engine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/1/13
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class FPSlabel extends BaseHudItem {

	private int framesShown;
	private int frames;
	private int framesTime;
	private int delta;

	public FPSlabel(Engine enginge) {
		super(enginge);
		frames = 0;
		framesTime = 0;
		framesShown = 0;

		fixedX = 10;
		fixedY = 10;
	}


	@Override
	public void update(int delta) {
		this.delta = delta;
		framesTime += delta;
		frames++;
		if(framesTime >= 1000){
			framesShown = frames;
			frames = 0;
			framesTime = 0;
		}
		super.update(delta);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.white);
		g.drawString("FPS: " + framesShown + "; " + (int) (((float) frames / (float) framesTime) * 1000) + ", " + (int) ( 1000f / (float) delta ) + "; delta: " + delta, x, y);
		g.drawString("(" + engine.getPlayer().x + "|" + engine.getPlayer().y + ")", x, y + 20);
		g.drawString("[" + engine.getPlayer().findTile().x + "|" + engine.getPlayer().findTile().y + "] {" + engine.getLevel().getPathCorrectedTileX(engine.getPlayer().findTile().x) + "|" +  engine.getLevel().getPathCorrectedTileY(engine.getPlayer().findTile().y) + "}" , x, y + 40);
	}
}
