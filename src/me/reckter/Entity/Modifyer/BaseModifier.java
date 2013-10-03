package me.reckter.Entity.Modifyer;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 03.10.13
 * Time: 13:27
 * To change this template use File | Settings | File Templates.
 */
public class BaseModifier {
    protected int timeToLive;

    protected double flatModifier; // just gets added;
    protected double percentageModifier; //gets multiplied so +10% is 1.1

    public BaseModifier(int timeToLive, double flatModifier, double percentageModifier){
        this.timeToLive = timeToLive;
        this.flatModifier = flatModifier;
        this.percentageModifier = percentageModifier;
    }


    /**
     * calculates the change of the input calue
     * @param value to modify
     * @return modified value
     */
    protected double addModifier(double value){
        return (value + flatModifier) * percentageModifier;
    }

    /**
     * health modifier
     * @param health  to modifiy
     * @return modified health
     */
    public double health(double health){
        return health;
    }

    /**
     * damage modifier
     * @param damage to modify
     * @return modiefied damage
     */
    public double damage(double damage){
        return damage;
    }

    /**
     * speed modifier
     * @param speed  to modify
     * @return modified speed
     */
    public double speed(double speed){
        return speed;
    }

    /**
     * cast speed modifier
     * @param castTime to modify
     * @return modified castTime
     */
    public int castTime(int castTime){
        return castTime;
    }


    /**
     * returns false if the entity is stunned or silenced
     * @return if the entity can cast right now
     */
    public boolean canCast(){
        return true;
    }


    public void update(int delta){

    }
}
