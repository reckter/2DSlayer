package me.reckter.Entity;

import me.reckter.Engine.Engine;
import me.reckter.Entity.Modifyer.DamageModifier;
import me.reckter.Interface.Coordinates;
import me.reckter.Interface.DamageText;
import me.reckter.Level.BaseLevel;
import me.reckter.Level.Tiles.BaseTile;
import me.reckter.Log;
import org.newdawn.slick.util.pathfinding.Path;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/1/13
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class EnemyEntity extends BaseEntity {

	protected int timeSinceLastPath;
	protected Path lastPath;

	public EnemyEntity(BaseLevel level, int x, int y) {
		super(level, x, y);
	}

	public EnemyEntity(BaseLevel level) {
		super(level);
	}

	@Override
	public void init() throws IOException {
		loadObject("twilight");
		speed = 60;
		target = level.getEngine().getPlayer();
        modifiers.add(new DamageModifier(10 * 1000, 0, 2));

	}

	@Override
	public void update(int delta) {
		if(target != null){

			lastPath = path;

			BaseTile start = findTile();
			BaseTile goal = target.findTile();
			timeSinceLastPath += delta;
			if(timeSinceLastPath > 3 * 1000){
				timeSinceLastPath = 0;

                //level.add(new Coordinates(level.getEngine(), "{" + level.getPathCorrectedTileX(findTile().x) + "|" + level.getPathCorrectedTileY(findTile().y) + "}", (int) x, (int) y));

                if(Math.abs(target.x - x) < level.getLoadingRadiusX() * Engine.SPRITE_SIZE && Math.abs(target.y - y) < level.getLoadingRadiusY() * Engine.SPRITE_SIZE ){
					try {
                        path = pathFinder.findPath(this, level.getPathCorrectedTileX(start.x), level.getPathCorrectedTileY(start.y), level.getPathCorrectedTileX(goal.x), level.getPathCorrectedTileY(goal.y));
				    } catch (ArrayIndexOutOfBoundsException e){
                        Log.error("ArrayIndexOutOfBoundsException: " + e.getMessage());
                        path = null;
                    }
                }
			}

			if(lastPath != null){

				for(int i = 0; i < lastPath.getLength(); i++){
					level.tiles.getTile(level.getPathOriginalTileX(lastPath.getStep(i).getX()), level.getPathOriginalTileY(lastPath.getStep(i).getY())).isHighligthed = false;
				}
			}
			if(path != null){
				if(path.contains(level.getPathCorrectedTileX(start.x), level.getPathCorrectedTileY(start.y))){
					for(int i = 0; i < path.getLength() - 1; i++){
						if(level.getPathOriginalTileX(path.getStep(i).getX()) == start.x && level.getPathOriginalTileY(path.getStep(i).getY()) == start.y){
							setMovement((float) (level.getPathOriginalTileX(path.getStep(i + 1).getX()) * Engine.SPRITE_SIZE - this.x), (float) (level.getPathOriginalTileY(path.getStep(i + 1).getY()) * Engine.SPRITE_SIZE - this.y));

						}
					}
				} else {
					setMovement((float) (level.getPathOriginalTileX(path.getStep(0).getX()) * Engine.SPRITE_SIZE - this.x), (float) (level.getPathOriginalTileY(path.getStep(0).getY()) * Engine.SPRITE_SIZE - this.y));
				}
				//Log.debug("start path from [" + start.x + "|" + start.y + "] to [" + goal.x + "|" + goal.y + "]");
				for(int i = 0; i < path.getLength(); i++){
					//level.tiles.getTile(level.getPathOriginalTileX(path.getStep(i).getX()), level.getPathOriginalTileY(path.getStep(i).getY())).isHighligthed = true;
					//Log.debug("->[" + level.getPathOriginalTileX(path.getStep(i).getX()) + "|" + level.getPathOriginalTileY(path.getStep(i).getY()) + "]");
				}
			}
			if(start.x == goal.x && start.y == goal.y){
				setMovement(0,0);
			}
		}

		super.update(delta);
	}

	@Override
	public void onCollision(BaseEntity with) {
		if(with instanceof PlayerEntity){
			dealDamage(with, 10);
		}
	}
}
