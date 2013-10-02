package me.reckter.Interface;

import me.reckter.Engine.Engine;
import me.reckter.Entity.BaseEntity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/1/13
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class DamageText extends BaseText {

	private int life;
	private static int MAX_LIFE = 3 * 1000;
	public boolean isDamage;

	public DamageText(Engine engine, int dmg){
		super(engine, "-" + dmg);
		isDamage = true;
		life = MAX_LIFE;
		if(dmg < 0){
			dmg = -dmg;
			text = "+" + dmg;
			isDamage = false;
		}

	}

	public DamageText(Engine engine, int dmg, BaseEntity on){
		this(engine, dmg);
		this.x = (int) (on.x );
		this.y = (int) (on.y - on.height);
	}


	@Override
	public boolean isAlive() {
		if(life <= 0){
			return false;
		}
		return true;
	}

	@Override
	public void update(int delta) {
		life -= delta;
	}

	@Override
	public void render(Graphics g) {
		if(isDamage){
			g.setColor(new Color(255,0,0, life / 10));
			g.drawString(text, x, y + life / 200);
		} else {
			g.setColor(new Color(0,255,0, life / 10));
			g.drawString(text, x, y + life / 200);
		}
	}
}
