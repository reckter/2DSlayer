package me.reckter.Entity;

import me.reckter.Engine.Engine;
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
				level.add(new Coordinates(level.getEngine(), "{" + level.getPathCorrectedTileX(findTile().x) + "|" + level.getPathCorrectedTileY(findTile().y) + "}", (int) x, (int) y));
				if(Math.abs(target.x - x) < level.getLoadingRadiusX() * Engine.SPRITE_SIZE && Math.abs(target.y - y) < level.getLoadingRadiusY() * Engine.SPRITE_SIZE ){
					path = pathFinder.findPath(this, level.getPathCorrectedTileX(start.x), level.getPathCorrectedTileY(start.y), level.getPathCorrectedTileX(goal.x), level.getPathCorrectedTileY(goal.y));
				}
			}

			if(lastPath != null){

				for(int i = 0; i < lastPath.getLength(); i++){
					level.tiles.getTile(level.getPathOriginalTileX(lastPath.getStep(0).getX()), level.getPathOriginalTileY(lastPath.getStep(0).getY())).isHighligthed = false;
				}
			}
			if(path != null){
				Log.debug(path.getLength() + "");
				setMovement((float) (level.getPathOriginalTileX(path.getStep(0).getX()) * Engine.SPRITE_SIZE - this.x), (float) (level.getPathOriginalTileY(path.getStep(0).getY()) * Engine.SPRITE_SIZE - this.y));
				Log.debug("start path");
				for(int i = 0; i < path.getLength(); i++){
					level.tiles.getTile(level.getPathOriginalTileX(path.getStep(0).getX()), level.getPathOriginalTileY(path.getStep(0).getY())).isHighligthed = true;
					Log.debug("->[" + level.getPathOriginalTileX(path.getStep(0).getX()) + "|" + level.getPathOriginalTileY(path.getStep(0).getY()) + "]");
				}
			}
		}

		super.update(delta);
	}

	@Override
	public void onCollision(BaseEntity with) {
		if(with instanceof PlayerEntity){
			dealDamage(this, 10);
		}
	}
}
