package me.reckter.Entity.Ability;

import me.reckter.Entity.BaseEntity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 03.10.13
 * Time: 13:01
 * To change this template use File | Settings | File Templates.
 */
public class BaseAoeAbility extends BaseAbility{

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public BaseAoeAbility(BaseEntity entity) {
        super(entity);
    }

    @Override
    public boolean cast(int middleX, int middleY) {
        x = middleX - width / 2;
        y = middleY - height / 2;

        return super.cast(x, y);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.draw(new Rectangle(x, y, width, height));
    }
}
