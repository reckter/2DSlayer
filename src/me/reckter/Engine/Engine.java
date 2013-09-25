package me.reckter.Engine;

import com.ilusionary.IxxFile.IxxFile;
import me.reckter.Entity.PlayerEntity;
import me.reckter.Level.BaseLevel;
import me.reckter.Level.Generation.BasicGeneration;
import me.reckter.Log;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.*;

import java.io.File;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 2:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Engine extends BasicGame{

	public static int SCREEN_WIDTH = 1280;
	public static int SCREEN_HEIGHT = 720;

	public static int SPRITE_SIZE = 32;

	public static int NORTH = 0;
	public static int NORTHEAST = 1;
	public static int EAST = 2;
	public static int SOUTHEAST = 3;
	public static int SOUTH = 4;
	public static int SOUTHWEST = 5;
	public static int WEST = 6;
	public static int NORTHWEST = 7;


	protected HashMap<String, Image> images;
	protected HashMap<String, IxxFile> ixxFiles;

	protected BaseLevel level;
	protected PlayerEntity player;

	protected int camX;
	protected int camY;

	protected Input input;

	public Engine() {
		super("2DSlayer");
	}

	public int getCamX() {
		return camX;
	}

	public int getCamY() {
		return camY;
	}

	/**
	 * Load an image and keep the reference to it in RAM
	 * If the same image is accessed again later, return the cached version instead of loading it again
	 * @param path Path to the image relative to ./res/graphics
	 * @return
	 */
	public Image loadImage(String path) {
		path = "res/graphics/" + path;
		File file = new File(path);

		if(file.isDirectory()) {
			IxxFile properties = null;

			while(file.exists() && file.isDirectory()) {
				if(ixxFiles.containsKey(path + "/properties.ixx")){
					properties = ixxFiles.get(path + "/properties.ixx");
				} else {
					properties = new IxxFile(path + "/properties.ixx");
					ixxFiles.put(path + "/properties.ixx",properties);
				}

				double random = Math.random();

				int lengthOfAlternatives = Integer.parseInt(properties.get("alternatives.length"));
				int chosen = -1;

				for(int i = 0; i < lengthOfAlternatives; i++) {
					if(random <= Double.parseDouble(properties.get("alternatives." + i + ".chance"))) {
						chosen = i;
						break;
					} else {
						random -= Double.parseDouble(properties.get("alternatives." + i + ".chance"));
					}
				}

				if(chosen == -1) {
					path += "/" + properties.get("default.name");
				} else {
					path += "/" + properties.get("alternatives." + chosen + ".name");
				}
				file = new File(path);
			}
			path += ".png";
		}

		if(images.containsKey(path)) {
			return images.get(path);
		}

		Image buf;

		try {
			Log.verbose("Loading " + path + "...");
			buf = new Image(path);
		} catch (SlickException e) {
			Log.error("Couldn't find " + "res/graphics/" + path);
			Log.error("This might lead to a crash...");
			return null;
		}

		images.put(path, buf);

		return buf;
	}


	@Override
	public void init(GameContainer gameContainer) throws SlickException {
		Log.verbose("init");

		images = new HashMap<String, Image>();
		ixxFiles = new HashMap<String, IxxFile>();

		level = new BaseLevel(this, new BasicGeneration(), 100);

	}

	@Override
	public void update(GameContainer gameContainer, int delta) throws SlickException {
		//To change body of implemented methods use File | Settings | File Templates.
		level.logic(delta);
	}

	@Override
	public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
		graphics.translate(-camX, -camY);
		level.render(graphics);
	}
}
