package me.reckter.Interface;

import me.reckter.Engine.Engine;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 03.10.13
 * Time: 00:43
 * To change this template use File | Settings | File Templates.
 */
public class HealthBar extends BaseHudItem {

    double health;
    double healthDisplayed;
    double maxHealth;

    public HealthBar(Engine enginge) {
        super(enginge);
        height = 20;
        width = 300;

        maxHealth = enginge.getPlayer().getMaxHealth();
        health = enginge.getPlayer().getHealth();
        healthDisplayed = 0;

        fixedX = Engine.SCREEN_WIDTH / 2 - width / 2;
        fixedY = Engine.SCREEN_HEIGHT - height;
    }

    @Override
    public boolean isAlive() {
        return true;
    }


    @Override
    public void update(int delta) {
        health = engine.getPlayer().getHealth();
        healthDisplayed += (health - healthDisplayed) / (800 / delta);
    }

    @Override
    public void render(Graphics g) { //TODO immages and filling in and stuff
        super.render(g);
        g.setColor(Color.black);
        g.draw(new Rectangle(x - 2, y - 2, width + 4, height +4));

        g.setColor(Color.red);
       // g.draw(new SHx, y, (float) (width * (healthDisplayed / maxHealth)), height);
        g.draw(new Rectangle(x,  y, (float) ((healthDisplayed / maxHealth) * (double)width), height));
        g.setColor(Color.white);
        g.drawString("" + (int) health, x + width / 2 - 10, y);
    }
}
