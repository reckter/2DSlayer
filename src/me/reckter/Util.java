package me.reckter;

import org.newdawn.slick.geom.Rectangle;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 9/25/13
 * Time: 6:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class Util {

	/**
	 * Check for a collision between Rectangles
	 * @param a Rectangle a
	 * @param b Rectangle b
	 * @return True if a and b collide
	 */
	public static boolean checkColl(Rectangle a, Rectangle b) {
		float leftA = a.getX();
		float leftB = b.getX();
		float rightA = a.getX() + a.getWidth();
		float rightB = b.getX() + b.getWidth();
		float topA = a.getY();
		float topB = b.getY();
		float botA = a.getY() + a.getHeight();
		float botB = b.getY() + b.getHeight();

		if(botA <= topB) {
			return false;
		}
		if(topA >= botB) {
			return false;
		}
		if(rightA <= leftB) {
			return false;
		}
		if(leftA >= rightB) {
			return false;
		}

		return true;
	}
}
