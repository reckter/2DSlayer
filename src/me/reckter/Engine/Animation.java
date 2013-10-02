package me.reckter.Engine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import com.ilusionary.IxxFile.IxxFile;

/**
 * Class for basic animation
 */
public class Animation {

	protected String fileName;

	protected int imageCount;
	protected int fps;
	protected int startFrame;

	protected double multiplier;

	protected long lastRender;
	public int currentFrame;
	protected boolean running;
	protected Image sprite;

	public Image[] images;

	protected Engine engine;

	/**
	 * Create an empty Animation
	 * @param ae Reference to the game engine
	 */
	public Animation(Engine ae) {
		fileName = "";
		imageCount = 0;
		fps = 0;

		multiplier = 1;

		lastRender = 0;
		currentFrame = 0;
		running = false;

		engine = ae;
	}

	/**
	 * Load an animation from file
	 * @param ae Engine reference
	 * @param file Path to the animation folder
	 * @throws IOException
	 */
	public Animation(Engine ae, String file) throws IOException {
		lastRender = 0;
		currentFrame = 0;
		running = false;

		multiplier = 1;

		fileName = "";
		imageCount = 0;
		fps = 0;

		engine = ae;

		load(file);
	}

	/**
	 * Start the animation
	 */
	public void start() {
		running = true;
	}

	/**
	 * Pause the animation
	 */
	public void pause() {
		running = false;
	}

	/**
	 * Stop the animation
	 */
	public void stop() {
		running = false;
		currentFrame = startFrame;
	}

	/**
	 * @return The current Frame
	 */
	public int getCurrentFrame() {
		return currentFrame;
	}

	/**
	 * Sets the Frame to the argument given.
	 * @param currentFrame
	 */
	public void setCurrentFrame(int currentFrame) {
		if(currentFrame >= imageCount || currentFrame < 0) {
			currentFrame = 0;
		}
		this.currentFrame = currentFrame;
	}

	/**
	 * Set the speed multiplier
	 * @param am New multiplier
	 */
	public void setMultiplier(double am) {
		multiplier = am;
	}

	/**
	 * Draw the current frame to the specified position
	 * @param g Graphics reference
	 * @param screenx X position on the screen
	 * @param screeny Y position on the screen
	 */
	public void render(Graphics g, int screenx, int screeny) {
		g.drawImage(images[currentFrame], screenx, screeny);

		if(System.currentTimeMillis() - lastRender > (1000 / (fps * multiplier)) && running) {
			lastRender = System.currentTimeMillis();
			currentFrame++;
			if(currentFrame >= imageCount) {
				currentFrame = 0;
			}
		}
	}

	/**
	 * Load the animation from file with the specified parameters.
	 * This function expects a sprite.png with the right dimensions in the folder "file"
	 * @param file Folder of the sprite.png
	 * @param aimageCount Image count of the animation
	 * @param afps FPS at which the animation is supposed to be displayed
	 * @param astartFrame Startframe
	 * @param aspriteSizeW Width of one picture of the animation
	 * @param aspriteSizeH Height of one picture of the animation
	 * @throws IOException
	 */
	public void load(String file, int aimageCount, int afps, int astartFrame, int aspriteSizeW, int aspriteSizeH) throws IOException {
		imageCount = aimageCount;
		fps = afps;
		startFrame = astartFrame;

		int spriteSizeW = aspriteSizeW;
		int spriteSizeH = aspriteSizeH;

		images = new Image[imageCount];
		sprite = engine.loadImage(file + "/sprite.png");

		for(int i = 0; i < imageCount; i++) {
			images[i] = sprite.getSubImage(i * spriteSizeW, 0, spriteSizeW, spriteSizeH);
		}
	}

	/**
	 * Load an animation from a folder and read the necessary values from anim.info using ixxFile v4
	 * @param file Subfolder of ./res/graphics/ containing the anim.info
	 * @throws IOException
	 */
	public void load(String file) throws IOException {
		InputStream info = this.getClass().getResourceAsStream("/graphics/" + file + "/anim.info");
		if(info == null) {
			info = new FileInputStream("res/graphics/" + file + "/anim.info");
		}
		IxxFile ixxAnim = new IxxFile(info);
		imageCount = Integer.parseInt(ixxAnim.get("animation.imageCount"));
		fps = Integer.parseInt(ixxAnim.get("animation.fps"));
		startFrame = Integer.parseInt(ixxAnim.get("animation.startFrame"));
		currentFrame = startFrame;

		int spriteSizeW = Integer.parseInt(ixxAnim.get("sprite.width"));
		int spriteSizeH = Integer.parseInt(ixxAnim.get("sprite.height"));

		load(file, imageCount, fps, startFrame, spriteSizeW, spriteSizeH);
	}
}

