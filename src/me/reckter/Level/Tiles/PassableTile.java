package me.reckter.Level.Tiles;

import me.reckter.Entity.BaseEntity;
import me.reckter.Level.BaseLevel;

/**
 * Created with IntelliJ IDEA.
 * User: reckter
 * Date: 10/1/13
 * Time: 9:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class PassableTile extends BaseTile {
	public PassableTile(BaseLevel level, int x, int y) {
		super(level, x, y);
	}

	@Override
	public boolean wouldColide(BaseEntity with) {
		return false;
	}
}
