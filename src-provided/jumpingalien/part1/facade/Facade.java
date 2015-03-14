package jumpingalien.part1.facade;

import jumpingalien.model.Mazub;
import jumpingalien.util.ModelException;
import jumpingalien.util.Sprite;

public class Facade implements IFacade {

	@Override
	public Mazub createMazub(int pixelLeftX, int pixelBottomY, Sprite[] sprites) throws ModelException {
		try {
			return new Mazub(pixelLeftX * 0.01, pixelBottomY * 0.01, sprites);
		} catch (IllegalArgumentException e) {
			throw new ModelException("IllegalArgumentException");
		}
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
	public void startJump(Mazub alien) throws ModelException {
		try {
			alien.startJump();
		} catch (IllegalStateException e) {
			throw new ModelException("Already jumping.");
		}
	}

	@Override
	public void endJump(Mazub alien) throws ModelException {
		try {
			alien.endJump();
		} catch (IllegalStateException e) {
			throw new ModelException("Not jumping.");
		}
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
	public void startDuck(Mazub alien) throws ModelException {
		try {
			alien.startDuck();
		} catch (IllegalStateException e) {
			throw new ModelException("Already ducking.");
		}
	}

	@Override
	public void endDuck(Mazub alien) throws ModelException {
		try {
			alien.endDuck();
		} catch (IllegalStateException e) {
			throw new ModelException("Not ducking.");
		}
	}

	@Override
	public void advanceTime(Mazub alien, double dt) throws ModelException {
		try {
			alien.advanceTime(dt);
		} catch (IllegalArgumentException e) {
			throw new ModelException("The time step should be in [0, 0.2]");
		} catch (IndexOutOfBoundsException e) {
			throw new ModelException("Reached edge.");
		}
	}
}
