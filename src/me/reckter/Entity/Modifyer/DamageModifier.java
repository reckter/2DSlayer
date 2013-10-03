package me.reckter.Entity.Modifyer;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 03.10.13
 * Time: 14:25
 * To change this template use File | Settings | File Templates.
 */
public class DamageModifier extends BaseModifier{
    public DamageModifier(int timeToLive, double flatModifier, double percentageModifier) {
        super(timeToLive, flatModifier, percentageModifier);
    }

    @Override
    public double damage(double damage) {
        return addModifier(damage);
    }
}
