package me.reckter.Level.Tiles;

import me.reckter.Entity.BaseEntity;
import me.reckter.Level.BaseLevel;

public class Spike extends PassableTile {
	
	protected int lastDamage;
	public final static int DAMAGE_COOLDOWN = 1 * 1000;
	
	public Spike(BaseLevel level, int x, int y) {
		super(level, x, y);
		lastDamage = 0;
	}
	
	@Override 
	public void onCollision(BaseEntity with){
		if(lastDamage <= 0) {
			lastDamage = DAMAGE_COOLDOWN;
			with.onDamage(this, 3);
		}
	}
	
	
	@Override
	public void update(int delta){
		if(lastDamage > 0){
			lastDamage -= delta;
		}
	}

}
