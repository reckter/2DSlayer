package me.reckter.Entity;

import me.reckter.Engine.Animation;
import me.reckter.Engine.Engine;
import me.reckter.Level.BaseLevel;
import me.reckter.Log;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlayerEntity extends BaseEntity{

	public boolean isCasting;

	public PlayerEntity(BaseLevel level) {
		super(level);

	}

	@Override
	public void init() throws IOException {
		Log.verbose("init player");
		anims[Engine.SOUTH * FACING_FACTOR + STANDING * WALKING_FACTOR + IDLE * ATTACKING_FACTOR] = new Animation(level.getEngine(), "objects/main_m.xen/down.xanim/standing/idle");
		anims[Engine.SOUTH * FACING_FACTOR + STANDING * WALKING_FACTOR + CASTING * ATTACKING_FACTOR] = new Animation(level.getEngine(), "objects/main_m.xen/down.xanim/standing/casting");

		anims[Engine.SOUTH * FACING_FACTOR + WALKING * WALKING_FACTOR + IDLE * ATTACKING_FACTOR] = new Animation(level.getEngine(), "objects/main_m.xen/down.xanim/walking/idle");
		anims[Engine.SOUTH * FACING_FACTOR + WALKING * WALKING_FACTOR + CASTING * ATTACKING_FACTOR] = new Animation(level.getEngine(), "objects/main_m.xen/down.xanim/walking/casting");

		anims[Engine.NORTH * FACING_FACTOR + STANDING * WALKING_FACTOR + IDLE * ATTACKING_FACTOR] = new Animation(level.getEngine(), "objects/main_m.xen/up.xanim/standing/idle");

	}

	/**
	 * Sets the animation variables to the correct values
	 */
	public void updateAnimation() {
		super.updateAnimation();

		if(movement.x + movement.y == 0) {
			walking = STANDING;
		} else {
			walking = WALKING;
		}

		if(isCasting && attack == IDLE) {
			attack = CASTING;
		} else if(!isCasting && attack ==  CASTING) {
			attack = IDLE;
		}
	}


	/**
	 * processes the input
	 * @param input input object
	 */
	public void updateInput(Input input) {

		if(input.isKeyDown(Input.KEY_LSHIFT)){
			speed = 400;
		} else {
			speed = 100;
		}

		int moveX = 0, moveY = 0;
		if(input.isKeyDown(Input.KEY_W)){
			moveY--;
		}
		if(input.isKeyDown(Input.KEY_S)){
			moveY++;
		}
		if(input.isKeyDown(Input.KEY_D)){
			moveX++;
		}
		if(input.isKeyDown(Input.KEY_A)){
			moveX--;
		}
		setMovement(moveX, moveY);
	}


}
