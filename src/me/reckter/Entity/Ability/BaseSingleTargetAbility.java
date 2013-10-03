package me.reckter.Entity.Ability;

import me.reckter.Entity.BaseEntity;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 03.10.13
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public class BaseSingleTargetAbility extends BaseAbility {

    protected BaseEntity target;

    public BaseSingleTargetAbility(BaseEntity entity) {
        super(entity);
    }

    @Override
    public boolean cast(int x, int y) {
        target = null;
        for(BaseEntity ent: caster.level.entities){
            if(ent.getCollisionBox().contains(x, y)){
                target = ent;
                break;
            }
        }

        if(target != null){
            return super.cast(x, y);    //To change body of overridden methods use File | Settings | File Templates.
        }
        return false;
    }
}
