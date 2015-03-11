package jumpingalien.part1.facade;

import jumpingalien.model.Mazub;
import jumpingalien.util.Sprite;

public class Facade implements IFacade {

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) {
		return new Mazub(pixelLeftX * 0.01, pixelBottomY * 0.01, sprites);
	}

	@Override
	public int[] getLocation(Mazub alien) {
		int[] position = {alien.getXPixel(), alien.getYPixel()};
		return position;
	}

	@Override
	public double[] getVelocity(Mazub alien) {
		double[] velocities = {alien.getXVelocity(), alien.getYVelocity()};
		return velocities;
	}

	@Override
	public double[] getAcceleration(Mazub alien) {
		double[] accelerations = {alien.getXAcceleration(), alien.getYAcceleration()};
		return accelerations;
	}

	@Override
	public int[] getSize(Mazub alien) {
		int[] size = {alien.getWidth(), alien.getHeight()};
		return size;
	}

	@Override
	public Sprite getCurrentSprite(Mazub alien) {
		return alien.getCurrentSprite();
	}

	@Override
	public void startJump(Mazub alien) {
		alien.startJump();
	}

	@Override
	public void endJump(Mazub alien) {
		alien.endJump();
	}

	@Override
	public void startMoveLeft(Mazub alien) {
		alien.startMove(false);
	}

	@Override
	public void endMoveLeft(Mazub alien) {
		alien.endMove();
	}

	@Override
	public void startMoveRight(Mazub alien) {
		alien.startMove(true);
	}

	@Override
	public void endMoveRight(Mazub alien) {
		alien.endMove();
	}

	@Override
	public void startDuck(Mazub alien) {
		alien.startDuck();
	}

	@Override
	public void endDuck(Mazub alien) {
		alien.endDuck();
	}

	@Override
	public void advanceTime(Mazub alien, double dt) {
		alien.advanceTime(dt);
	}
}
