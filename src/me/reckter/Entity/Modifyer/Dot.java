package me.reckter.Entity.Modifyer;

import me.reckter.Entity.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 03.10.13
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class Dot extends BaseModifier {

    BaseEntity target;
    BaseEntity caster;

    protected int timeToTick;
    protected int timeSinceLastTick;

    public Dot(BaseEntity target, BaseEntity caster, int timeToLive, int timeToTick, int damage) {
        super(timeToLive, damage, 0);
        this.target = target;
        this.caster = caster;
        this.timeToTick = timeToTick;
        this.timeSinceLastTick = 0;
    }

    public Dot(BaseEntity target, BaseEntity caster, int timeToLive, int damage) {
        this(target,caster, timeToLive, 1000, damage);
    }

    @Override
    public void update(int delta) {
        super.update(delta);    //To change body of overridden methods use File | Settings | File Templates.
        timeSinceLastTick += delta;
        if(timeSinceLastTick >= timeToTick){
            timeSinceLastTick = 0;
            target.onDamage(caster, flatModifier);
        }
    }
}
