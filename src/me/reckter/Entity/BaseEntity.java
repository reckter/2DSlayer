package me.reckter.Entity;

import me.reckter.Engine.Animation;
import me.reckter.Engine.Engine;
import me.reckter.Entity.Ability.BaseAbility;
import me.reckter.Entity.Modifyer.BaseModifier;
import me.reckter.Entity.Modifyer.HealthModifier;
import me.reckter.Entity.Modifyer.ModifierHandler;
import me.reckter.Interface.BaseText;
import me.reckter.Interface.DamageText;
import me.reckter.Level.BaseLevel;
import me.reckter.Level.Tiles.BaseTile;
import me.reckter.Log;
import me.reckter.Util;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.Path;

import java.awt.font.GraphicAttribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 2:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseEntity implements Mover{


	protected static int ANIM_MAX = 32;

	public static int FACING_FACTOR = 4;
	public static int WALKING_FACTOR = 4;
	public static int ATTACKING_FACTOR = 1;

	public static int STANDING = 0;
	public static int WALKING = 1;

	public static int IDLE = 0;
	public static int TRANSITION_CASTING = 1;
	public static int CASTING = 2;
	public static int ATTACKING = 3;

	public static int MAX_COOLDOWN = 3 * 1000;

	public int cooldown;

	public int speed = 100;
	public double x;
	public double y;
	public int width;
	public int height;

	protected Vector2f movement;

	public BaseLevel level;

	public int facing;
	public int walking;
	public int attack;


    protected double maxHealthOrigin;

    protected double health; // percentage of maxHealth (0.0 to 1.0)
    protected double maxHealth;

	protected BaseEntity target;

	protected Animation anims[];

    protected BaseAbility[] abilities;
    protected static int MAX_ABILITIES = 4;

    public ModifierHandler modifiers;

	protected AStarPathFinder pathFinder;
	protected Path path;


	public BaseEntity(BaseLevel level){
		this.level = level;
		anims = new Animation[ANIM_MAX];
		pathFinder = new AStarPathFinder(level, 500, true);

        maxHealth = 100;;
		width = 32;
		height = 32;
		movement = new Vector2f(0,0);

        abilities = new BaseAbility[MAX_ABILITIES];
        modifiers = new ModifierHandler();
        health = 1.0;
        modifiers.add(new HealthModifier(20 * 1000,30,1));
        modifiers.add(new HealthModifier(10 * 1000,0,2));
	}

    public BaseEntity(BaseLevel level, int x, int y){
        this(level);
        this.x = x;
        this.y = y;
    }

	/**
	 * initialized the entity
	 */
	public void init() throws IOException {
	}

    public double getMaxHealth() {
        return modifiers.getHealth(maxHealth);
    }

    public double getHealth() {
        return health * getMaxHealth() ;
    }

    /**
	 * sets the movement to (x,y) and normalizes it
	 * @param x
	 * @param y
	 */
	public void setMovement(float x, float y){
		movement.set(x, y);
		movement.normalise();
	}

	/**
	 * Returns the tile the Entity is standing on
	 * @return the tile
	 */
	public BaseTile findTile(){
		return level.tiles.getTile((int) (this.x / Engine.SPRITE_SIZE), (int) (this.y / Engine.SPRITE_SIZE));
	}

	/**
	 * Sets the animation variables to the correct values
	 */
	public void updateAnimation() {
		if(movement.x > 0) {
			facing = Engine.EAST;
		}
		if(movement.x < 0) {
			facing = Engine.WEST;
		}

		if(movement.y > 0 && Math.abs(movement.getX()) <= 1) {
			facing = Engine.SOUTH;
		}
		if(movement.y < 0 && Math.abs(movement.getX()) <= 1) {
			facing = Engine.NORTH;
		}
	}

	/**
	 * returns the Colliosion box
	 * @return Rectangle descibing the collision box
	 */
	public Rectangle getCollisionBox(){
		return new Rectangle((int)x, (int)y, width, height);
	}

	/**
	 * Checks the collision with an entity
	 * @param with the entity
	 * @return true in case of collision
	 */
	public boolean checkColl(BaseEntity with){
		return Util.checkColl(getCollisionBox(), with.getCollisionBox());
	}

	/**
	 * Checks the collision with a tile
	 * @param with the tile
	 * @return true in case of collision
	 */
	public boolean checkColl(BaseTile with){
		return Util.checkColl(getCollisionBox(), with.getCollisionBox());
	}

	/**
	 * checks if the entity can collide with the tile
	 * @param with tile to check
	 * @return true if they can collide
	 */
	public boolean wouldColide(BaseTile with){
		double xTemp = x;
		double yTemp = y;

		x = with.getMiddleX();
		y = with.getMiddleY();

		boolean ret = checkColl(with);

		x = xTemp;
		y = yTemp;
		return ret;
	}

	/**
	 * checks if the entity is still alive
	 * @return true in case of death
	 */
	public boolean isDead(){
		return health <=  0;
	}


    public boolean shouldBeLoaded(){
        return !isDead() && Math.abs(this.x - level.getEngine().getPlayer().x) < Engine.SCREEN_WIDTH  && Math.abs(this.y - level.getEngine().getPlayer().y) < Engine.SCREEN_HEIGHT ;
    }

	/**
	 * gets Called when receives Damage
	 * @param from the damage dealer
	 */
	public void onDamage(BaseEntity from, double dmg){
		health = (getHealth() - dmg) / getMaxHealth();
        if(health <= 0){
            health = 0;
        }
        if(health >= 1){
            health = 1;
        }
		level.add(new DamageText(level.getEngine(), (int) dmg, this));
		if(isDead()){
			onDeath(from);
		}
	}
	
	/**
	 * gets Called when receives Damage
	 * @param from the tile that deals the damage
	 */
	public void onDamage(BaseTile from, double dmg){
		health = (getHealth() - dmg) / getMaxHealth();
        if(health <= 0){
            health = 0;
        }
        if(health >= 1){
            health = 1;
        }
		level.add(new DamageText(level.getEngine(), (int) dmg, this));
		if(isDead()){
			onDeath(from);
		}
	}

	/**
	 * gets called from onDamage if the entity just dies
	 * @param killer the entity that dealt the last dmg
	 */
	public void onDeath(BaseEntity killer){
		Log.verbose(this.getClass().getCanonicalName() + " just died!");
	}

	/**
	 * gets called from onDamage if the entity just dies
	 * @param killer the tile that dealt the last dmg
	 */
	public void onDeath(BaseTile killer){
		Log.verbose(this.getClass().getCanonicalName() + " just died!");
	}
	
	/**
	 * checks basic attack cooldown and calls to.onDamage() if no cooldown
	 * @param to Entity to damage
	 * @param dmg the amount of damage
	 */
	public void dealDamage(BaseEntity to, double dmg){
		if(cooldown <= 0){
			to.onDamage(this, modifiers.getDamage(dmg));
			cooldown = MAX_COOLDOWN;
		}
	}

	/**
	 * gets Called when the entity collided with an entity
	 * @param with the entity it collided with
	 */
	public void onCollision(BaseEntity with){

	}

	/**
	 * gets Called when the entity collided with a tile
	 * @param with the tile it collided with
	 */
	public void onCollision(BaseTile with){

	}

	/**
	 * updates the Entity
	 * @param delta time since last update
	 */
	public void update(int delta){
		updateAnimation();

        modifiers.update(delta);

		Vector2f tmp = new Vector2f(movement);
		tmp.scale((float) delta / 1000);
		x += tmp.x * modifiers.getSpeed(speed);
		y += tmp.y * modifiers.getSpeed(speed);

		if(cooldown > 0){
			cooldown -= delta;
		}
	}

	/**
	 * Load a game object from the disk. This loads 4 animations in the specified folder
	 * @param aname The name of the object (e.g.: "main_m" to load "./res/graphics/objects//main_m.xen/down.xanim, .../up.xanim, ...)
	 * @throws IOException
	 */
	public void loadObject(String aname) throws IOException { //TODO change to the new anim system
		anims[Engine.SOUTH * FACING_FACTOR] = new Animation(level.getEngine(), "objects/" + aname + ".xen/down.xanim");
		anims[Engine.NORTH * FACING_FACTOR]  = new Animation(level.getEngine(), "objects/" + aname + ".xen/up.xanim");
		anims[Engine.WEST * FACING_FACTOR] = new Animation(level.getEngine(), "objects/" + aname + ".xen/left.xanim");
		anims[Engine.EAST * FACING_FACTOR] = new Animation(level.getEngine(), "objects/" + aname + ".xen/right.xanim");
	}

	/**
	 * Renders the entity
	 * @param g Graphics instants
	 */
	public void render(Graphics g){
		if(anims[facing * FACING_FACTOR + walking * WALKING_FACTOR + attack * ATTACKING_FACTOR] == null) {
			return;
		}

		anims[facing * FACING_FACTOR + walking * WALKING_FACTOR + attack * ATTACKING_FACTOR].start();
		for(int i = 0; i < ANIM_MAX; i++) {
			if(facing * FACING_FACTOR + walking * WALKING_FACTOR + attack * ATTACKING_FACTOR != i && anims[i] != null) {
				anims[i].stop();
			}
		}
		anims[facing * FACING_FACTOR + walking * WALKING_FACTOR + attack * ATTACKING_FACTOR].render(g,(int) x , (int) y);
		for(int i = 0; i < ANIM_MAX; i++) {
			if(facing * FACING_FACTOR + walking * WALKING_FACTOR + attack * ATTACKING_FACTOR != i && anims[i] != null) {
				anims[i].setCurrentFrame(anims[facing * FACING_FACTOR + walking * WALKING_FACTOR + attack * ATTACKING_FACTOR].getCurrentFrame());
			}
		}
	}

}
