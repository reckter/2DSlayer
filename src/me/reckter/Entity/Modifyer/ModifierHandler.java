package me.reckter.Entity.Modifyer;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 03.10.13
 * Time: 14:30
 * To change this template use File | Settings | File Templates.
 */
public class ModifierHandler {
    protected ArrayList<BaseModifier> modifiers;

    public ModifierHandler(){
        modifiers = new ArrayList<BaseModifier>();
    }

    public void add(BaseModifier modifier){
        modifiers.add(modifier);
    }

    /**
     * checks if the entity can cast
     * @return true if the entity is free to cast
     */
    public boolean canCast(){
        for(BaseModifier modifier: modifiers){
            if(!modifier.canCast()){
                return false;
            }
        }
        return true;
    }

    /**
     * calculates the castTime
     * @param castTime to modifie
     * @returnmodified castTime
     */
    public int getCastTime(int castTime){
        for (BaseModifier modifier: modifiers){
            castTime = modifier.castTime(castTime);
        }
        return castTime;
    }

    /**
     * calculates the speed
     * @param speed to modifie
     * @returnmodified speed
     */
    public double getSpeed(double speed){
        for (BaseModifier modifier: modifiers){
            speed = modifier.speed(speed);
        }
        return speed;
    }

    /**
     * calculates the damage
     * @param damage to modifie
     * @returnmodified damage
     */
    public double getDamage(double damage){
        for (BaseModifier modifier: modifiers){
            damage = modifier.damage(damage);
        }
        return damage;
    }

    /**
     * calculates the health
     * @param health to modifie
     * @returnmodified health
     */
    public double getHealth(double health){
        for (BaseModifier modifier: modifiers){
            health = modifier.health(health);
        }
        return health;
    }

    public void update(int delta){

        ArrayList<BaseModifier> tempModifiers = new ArrayList<BaseModifier>();
        for(BaseModifier modifier: modifiers){
            modifier.update(delta);
            if(!modifier.isActive()){
                tempModifiers.add(modifier);
            }
        }
        modifiers.removeAll(tempModifiers);
    }
}
