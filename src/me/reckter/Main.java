package me.reckter;

import me.reckter.Engine.Engine;
import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
	static public void main(String args[]) throws SlickException {
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("user.dir"), "lib/native"), LWJGLUtil.getPlatformName()).getAbsolutePath());

		Engine dsm = new Engine();

		AppGameContainer app = new AppGameContainer(dsm);

		app.setShowFPS(false);
		app.setDisplayMode(Engine.SCREEN_WIDTH, Engine.SCREEN_HEIGHT, false);

		app.start();

	}
}
