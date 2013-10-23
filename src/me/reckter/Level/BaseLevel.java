package me.reckter.Level;

import me.reckter.Engine.Engine;
import me.reckter.Entity.BaseEntity;
import me.reckter.Entity.EnemyEntity;
import me.reckter.Entity.PlayerEntity;
import me.reckter.Interface.BaseInterface;
import me.reckter.Interface.FPSlabel;
import me.reckter.Interface.HealthBar;
import me.reckter.Level.Generation.BaseGeneration;
import me.reckter.Level.Tiles.BaseTile;
import me.reckter.Level.Tiles.TilesHandler;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.pathfinding.Mover;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 2:21 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseLevel implements TileBasedMap{
	protected Engine engine;

	protected long seed;
	protected Random random;

	public TilesHandler tiles;
	protected BaseGeneration generation;
	protected int loadingRadiusX;
	protected int loadingRadiusY;

	protected int updates;

	protected int timeSinceLastSpawn;

	public ArrayList<BaseEntity> entities;
	public ArrayList<BaseInterface> interfaces;

	public BaseLevel(Engine engine){
		this.engine = engine;
		updates = 0;
		timeSinceLastSpawn = 0;
		entities = new ArrayList<BaseEntity>();
		interfaces = new ArrayList<BaseInterface>();
	}

	public BaseLevel(Engine engine, BaseGeneration generation){
		this(engine);
		this.generation = generation;
		this.loadingRadiusX = (Engine.SCREEN_WIDTH / Engine.SPRITE_SIZE) / 2 + 2;
		this.loadingRadiusY = (Engine.SCREEN_HEIGHT / Engine.SPRITE_SIZE) / 2 + 2;

		random = new Random(5);

		tiles = new TilesHandler(generation, this);
		tiles.manageTiles(0, 0, loadingRadiusX, loadingRadiusY);
	}

	public void init() throws IOException {
		this.add(new EnemyEntity(this, 500, 50));
		this.add(new FPSlabel(engine));
        this.add(new HealthBar(engine));

		for(BaseEntity ent: entities) {
			ent.init();
		}
	}

	public int getLoadingRadiusX() {
		return loadingRadiusX;
	}

	public int getLoadingRadiusY() {
		return loadingRadiusY;
	}

	/**
	 * updates the logic of the level and all the entities and such
	 * @param delta time since last update
	 */
	public void logic(int delta) {
		updates++;
		if(updates < 100){
			updates = 0;
		}

		//timeSinceLastSpawn += delta;
		if(timeSinceLastSpawn > 1 * 1000){
			timeSinceLastSpawn = 0;
			BaseEntity enemy = new EnemyEntity(this, (int) engine.getPlayer().x + random.nextInt(200) - 100, (int) engine.getPlayer().y + random.nextInt(200) - 100);
			try {
				enemy.init();
			} catch (IOException e) {
				e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
			}
			add(enemy);
		}


		ArrayList<BaseEntity> entTempCpll = new ArrayList<BaseEntity>();
		for(BaseEntity ent: entities) {
			ent.update(delta);
			if(!ent.shouldBeLoaded()){
				entTempCpll.add(ent);
			}
		}
		entities.removeAll(entTempCpll);

		for(int i = 0; i < entities.size(); i++){
			for(int j = i; j < entities.size(); j++){
				if(entities.get(i).checkColl(entities.get(j))){
					entities.get(i).onCollision(entities.get(j));
					entities.get(j).onCollision(entities.get(i));
				}
			}
			
			BaseTile standingOnTile = entities.get(i).findTile();
			for(int x = standingOnTile.x - (int) (entities.get(i).width / Engine.SPRITE_SIZE + 0.5); x <= standingOnTile.x + (int) (entities.get(i).width / Engine.SPRITE_SIZE + 0.5); x++){
				for(int y = standingOnTile.y - (int) (entities.get(i).width / Engine.SPRITE_SIZE + 0.5); y <= standingOnTile.y + (int) (entities.get(i).width / Engine.SPRITE_SIZE + 0.5); y++){
					
					BaseTile with = tiles.getTile(x, y);
					if(entities.get(i).checkColl(with)){
						entities.get(i).onCollision(with);
						with.onCollision(entities.get(i));
					}
				}
			}
		}
		if(updates % 10 == 0){
			tiles.manageTiles((int) engine.getPlayer().x / Engine.SPRITE_SIZE, (int) engine.getPlayer().y / Engine.SPRITE_SIZE, loadingRadiusX, loadingRadiusY);
		}
		
		tiles.update(delta);

		ArrayList<BaseInterface> tempColl = new ArrayList<BaseInterface>();
		for(BaseInterface face: interfaces){

			face.update(delta);

			if(!face.isAlive()){
				tempColl.add(face);
			}
		}
		interfaces.removeAll(tempColl);

	}

	/**
	 * returns the corrected x value so the pathfinding works
	 *
	 * @param x the original x
	 * @return the corrected x
	 */
	public int getPathCorrectedTileX(int x){
		return x - (engine.getPlayer().findTile().x - loadingRadiusX);
	}

	/**
	 * returns the corrected y value so the pathfinding works
	 *
	 * @param y the original y
	 * @return the corrected y
	 */
	public int getPathCorrectedTileY(int y){
		return y - (engine.getPlayer().findTile().y - loadingRadiusY);
	}

	/**
	 * returns the original x value for an pathfinding x value
	 *
	 * @param x the corrected x
	 * @return the original x
	 */
	public int getPathOriginalTileX(int x){
		return x + (engine.getPlayer().findTile().x - loadingRadiusX);
	}

	/**
	 * returns the original x value for an pathfinding y value
	 *
	 * @param y the corrected y
	 * @return the original y
	 */
	public int getPathOriginalTileY(int y){
		return y + (engine.getPlayer().findTile().y - loadingRadiusY);
	}


	/**
	 *  render
	 * @param g the graphic instance
	 */
	public void render(Graphics g)
	{
		tiles.render(g);
		for(BaseEntity ent: entities){
			ent.render(g);
		}
		for(BaseInterface face: interfaces){
			face.render(g);
		}
	}

	/**
	 * returns the engine
	 * @return engine object
	 */
	public Engine getEngine(){
		return engine;
	}

	/**
	 * adds an entity
	 * @param entity the entity to be added
	 */
	public void add(BaseEntity entity){
		entities.add(entity);
	}

	/**
	 * adds an interface
	 * @param face the interface to be added
	 */
	public void add(BaseInterface face){
		interfaces.add(face);
	}

	/**
	 * delets an entity
	 * @param entity the entity to be deleted
	 */
	public void del(BaseEntity entity){
		entities.remove(entity);
	}


	public void spawnPlayer(PlayerEntity player){
		this.add(player);
		player.x = 0;
		player.y = 0;
	}


	@Override
	public int getWidthInTiles() {
		return loadingRadiusX * 2;
	}

	@Override
	public int getHeightInTiles() {
		return loadingRadiusY * 2;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
	}

	@Override
	public boolean blocked(PathFindingContext pathFindingContext, int x, int y) {
		x = getPathOriginalTileX(x);
		y = getPathOriginalTileY(y);

		Mover mover = pathFindingContext.getMover();
		if(mover instanceof BaseEntity){
			return ((BaseEntity) mover).wouldColide(tiles.getTile(x, y)) && tiles.getTile(x, y).wouldColide((BaseEntity) mover);
		}

		return false;
	}

	@Override
	public float getCost(PathFindingContext pathFindingContext, int x, int y) {
		return 1;
	}
}
