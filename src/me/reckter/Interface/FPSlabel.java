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

	private int updatesShown;
	private int updates;
	private int updatesTime;

	private int frames;
	private int framesShown;
	private long framesTime;

	private int delta;

	public FPSlabel(Engine enginge) {
		super(enginge);
		updates = 0;
		updatesTime = 0;
		updatesShown = 0;

		frames = 0;
		framesShown = 0;
		framesTime = 0;

		fixedX = 10;
		fixedY = 10;
	}


	@Override
	public void update(int delta) {
		this.delta = delta;
		updatesTime += delta;
		updates++;
		if(updatesTime >= 1000){
			updatesShown = updates;
			updates = 0;
			updatesTime = 0;
		}
		super.update(delta);
	}

	@Override
	public void render(Graphics g) {

		frames ++;
		if(framesTime < System.currentTimeMillis() - 1000){
			framesShown = frames;
			framesTime = System.currentTimeMillis();
			frames = 0;
		}
		super.render(g);
		g.setColor(Color.white);
		g.drawString("UPS: " + updatesShown + "; " + (int) (((float) updates / (float) updatesTime) * 1000) + ", " + (int) (1000f / (float) delta) + "; delta: " + delta, x, y);
		g.drawString("FPS: " +  framesShown, x, y + 20);
		g.drawString("(" + (int) engine.getPlayer().x + "|" + (int) engine.getPlayer().y + ") [" + engine.getPlayer().findTile().x + "|" + engine.getPlayer().findTile().y + "] {" + engine.getLevel().getPathCorrectedTileX(engine.getPlayer().findTile().x) + "|" +  engine.getLevel().getPathCorrectedTileY(engine.getPlayer().findTile().y) + "}" , x, y + 40);
		g.drawString("EntityCount: " + engine.getLevel().entities.size(), x, y + 60);
	}
}
