package me.reckter.Entity.Ability;

import me.reckter.Entity.BaseEntity;
import org.newdawn.slick.Graphics;

/**
 * Created with IntelliJ IDEA.
 * User: mediacenter
 * Date: 03.10.13
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public class BaseAbility {
    protected BaseEntity caster;

    protected int maxCastTime;
    protected int castTime;

    protected int maxCooldownTime;
    protected int cooldownTime;

    protected boolean casting;
    protected boolean onColldown;

    public BaseAbility(BaseEntity caster){
        this.caster = caster;
    }


    /**
     * checks if spell could cast and if gets casted
     */
    public boolean cast(int x, int y){
        if(!onColldown && !casting){
            onCast();
            return true;
        }
        return false;
    }


    /**
     * gets called when the Ability is casted
     */
    public void onCast(){
        castTime = maxCastTime;
        casting = true;
    }

    /**
     * gets called after the casting time is over
     */
    public void onResulution(){
    }

    public void update(int delta){
        if(casting){
            castTime -= delta;
        }

        if(castTime < 0 && casting){
            casting = false;
            onResulution();
            cooldownTime = maxCooldownTime;
        }

        if(onColldown){
            cooldownTime -= delta;
        }

        if(cooldownTime < 0 && onColldown){
            onColldown = false;
        }
    }

    public void render(Graphics g){

    }
}
