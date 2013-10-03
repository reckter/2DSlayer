package me.reckter.Entity.Modifyer;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 03.10.13
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class Stun extends BaseModifier {
    public Stun(int timeToLive) {
        super(timeToLive, 0, 0);
    }

    @Override
    public double speed(double speed) {
        return 0;
    }

    @Override
    public boolean canCast() {
        return false;
    }
}
