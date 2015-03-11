package jumpingalien.model;

import jumpingalien.util.Sprite;

/**
 * Class that implements the character of the game Jumping Alien.
 * 
 * @invar When the xState is STILL, the horizontal speed is zero.
 * 		| if (getXState() == XState.STILL)
 * 		    getXVelocity() == 0;	
 * @invar When the xState is not STILL, the horizontal speed is always greater than zero.
 * 		| if (getXState() != XState.STILL)
 * 			getXVelocity > 0;
 * @invar The horizontal speed is never greater than MAX_X_VELOCITY.
 * 		| getXVelocity() <= 3;
 * @invar When inAir is false, the vertical speed is zero.
 * 		| if (isInAir())
 * 			getYVelocity == 0;
 * @author Tim Geypens, Jeroen Bruin
 *
 */
public class Mazub {
	// VARIABLE DECLARATION
	private final double INITIAL_X_VELOCITY = 1;
	private static final double X_ACCELERATION = 0.9;
	private final double MAX_X_VELOCITY = 3;
	private final double MAX_X_DUCKING_VELOCITY = 1;
	private XState xState;
	
	private final double INITIAL_Y_VELOCITY = 8;
	private static final double Y_ACCELERATION = -10;
	private boolean isJumping;
	
	private final double MAX_X = 1023 * 0.01;
	private final double MAX_Y = 767 * 0.01;
	
	public int width;
	public int height;
	public double xPosition;
	public double yPosition;
	private double xVelocity;
	private double yVelocity;
	private boolean directionIsRight;
	private boolean isDucking;
	
	private double timeToLastStateChange;
	private XState lastState;
	private boolean lastDirectionIsRight;
	
	private Sprite[] sprites;
	
	//CONSTRUCTOR
	public Mazub(double XPosition, double YPosition, Sprite[] sprites) {
		setXPosition(XPosition);
		setYPosition(YPosition);
		setSprites(sprites);
	}	
	
	//BASIC GETTERS & SETTERS
	// TODO insert comments on getters and setters (at least @Basic on some of them)
	public int getWidth() {
		// TODO
		return 50;
	}

	public int getHeight() {
		// TODO
		return 50;
	}

	public double getXPosition() {
		return xPosition;
	}

	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public double getYPosition() {
		return yPosition;
	}

	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public XState getXState() {
		return xState;
	}

	public void setXState(XState xState) {
		this.xState = xState;
	}

	public boolean isJumping() {
		return isJumping;
	}

	public void setJumping(boolean inAir) {
		this.isJumping = inAir;
	}

	public boolean isDucking() {
		return isDucking;
	}

	public void setIsDucking(boolean isDucking) {
		this.isDucking = isDucking;
	}

	public boolean isDirectionIsRight() {
		return directionIsRight;
	}

	public void setDirectionIsRight(boolean directionIsRight) {
		this.directionIsRight = directionIsRight;
	}

	public void setLastDirectionIsRight(boolean lastDirectionIsRight) {
		this.lastDirectionIsRight = lastDirectionIsRight;
	}

	public boolean isLastDirectionIsRight() {
		return lastDirectionIsRight;
	}

	public XState getLastState() {
		return lastState;
	}

	public void setLastState(XState lastState) {
		this.lastState = lastState;
	}

	public double getTimeToLastStateChange() {
		return timeToLastStateChange;
	}

	public void resetTimeToLastStateChange() {
		this.timeToLastStateChange = 0;
	}
	
	public void advanceTimeToLastStateChange(double step) {
		this.timeToLastStateChange += step;
	}
	
	public Sprite[] getSprites() {
		return this.sprites;
	}
	
	public Sprite getSprite(int index) {
		return getSprites()[index];
	}
	
	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
	
	//OTHER INSPECTORS
	/**
	 * Get the x-coordinate of the bottom left pixel of Mazub.
	 * @return XPosition converted from meter to pixels and rounded down.
	 */
	public int getXPixel() {
		return (int) (getXPosition() * 100);
	}
	
	/**
	 * Get the y-coordinate of the bottom left pixel of Mazub.
	 * @return YPosition converted from meter to pixels and rounded down.
	 */
	public int getYPixel() {
		return (int) (getYPosition() * 100);
	}
	
	/**
	 * Returns the x-component of the current acceleration.
	 * @return X_ACCELERATION if Mazub is accelerating horizontally, zero if not.
	 */
	public double getXAcceleration() {
		if (getXState() == XState.ACCELERATING) {
			return X_ACCELERATION;
		} else {
			return 0;
		}
	}
	
	/**
	 * Returns the y-component of the current acceleration.
	 * @return Y_ACCELERATION if Mazub is in the air, zero if not.
	 */
	public double getYAcceleration() {
		if (isJumping()) {
			return Y_ACCELERATION;
		}
		return 0;
	}
	
	
	//HORIZONTAL MOVEMENT	(total)
	/**
	 * Starts Mazub's horizontal movement.
	 * @pre Mazub is not moving horizontally.
	 * 		| getXState == XState.STILL
	 * @post The horizontal velocity of Mazub equals the initial horizontal velocity. Mazub is moving.
	 * 		| (new.getVelocity() == INITIAL_X_VELOCITY) && (new.xState == XState.ACCELERATING)
	 * @post Mazub is facing the direction denoted by the boolean directionIsRight
	 * 		(true means right, false means left)
	 * 		| new.directionIsRight == directionIsRight
	 * @Raw
	 */
	public void startMove(boolean directionIsRight) {
		assert getXState() == XState.STILL;
		setDirectionIsRight(directionIsRight);
		setLastState(XState.STILL); //Equivalent to setLastState(getXState())
		setXVelocity(INITIAL_X_VELOCITY);
		setXState(XState.ACCELERATING);
		resetTimeToLastStateChange();
	}
	
	/**
	 * Stops Mazub's horizontal movement immediately.
	 * @pre Mazub is moving horizontally.
	 * 		| getXState != XState.STILL
	 * @post Mazub is standing still in the x-direction.
	 * 		| new.getXState = XState.STILL
	 * @Raw
	 */
	public void endMove() {
		assert getXState() != XState.STILL;
		setLastState(getXState());
		setXState(XState.STILL);
		setXVelocity(0);
		resetTimeToLastStateChange();
	}
	
	/**
	 * Updates the horizontal velocity of Mazub to its new value after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	public void updateXVelocity(double step) {
		if (getXState() == XState.ACCELERATING) {
			double newVelocity = getXVelocity() + X_ACCELERATION * step;
			if (! isDucking) {
				if (newVelocity >= MAX_X_VELOCITY) {	//Mazub is not ducking and has reached max speed.
					setXVelocity(MAX_X_VELOCITY);
					setXState(XState.MAXED_OUT);
				} else {	//Mazub is not ducking and is still accelerating.
					setXVelocity(newVelocity);
				}
			} else {
				if (newVelocity >= MAX_X_DUCKING_VELOCITY) {	//Mazub is ducking and has reached max speed.
					setXVelocity(MAX_X_DUCKING_VELOCITY);
					setXState(XState.MAXED_OUT);
				} else { 	//Mazub is ducking and is still accelerating.
					setXVelocity(newVelocity);
				}
			}
		}
		//If Mazub has already reached max speed or is not moving horizontally, there is no change in speed.
	}
	
	/**
	 * Updates the horizontal position of Mazub to its new value after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	public void updateXPosition(double step) {
		double distanceTraveled = 0;
		if (getXState() == XState.ACCELERATING) { //Speed is not constant.
			distanceTraveled = getXVelocity() * step + .5 * X_ACCELERATION * step * step;
		} else { //Speed is constant (0 or MAX_X_SPEED)
			distanceTraveled = getXVelocity() * step;
		} //If Mazub is not moving horizontally, the distance traveled is the initial value of distanceTraveled (0)
		if (isDirectionIsRight()) { // When going to the right, the distance is added to the old position.
			setXPosition(getXPosition() + distanceTraveled);
		} else { //When going to the left, the distance is subtracted from the old position.
			setXPosition(getXPosition() - distanceTraveled);
		}
		setXPosition(isDirectionIsRight() ? getXPosition()+distanceTraveled : getXPosition()-distanceTraveled);
	
	}
	
	/**
	 * Check if a given position is a valid x-position
	 * @param position, x-coordinate of the bottom left coordinate in meters
	 * @return true if the position is in the interval [0,MAX_X]
	 */
	public boolean isValidXPosition(double position) {
		return (position >= 0) && (position <= MAX_X);
	}
	
	//VERTICAL MOVEMENT
	/**
	 * Starts Mazub's jump.
	 * @throws IllegalStateException
	 * 		Mazub is already jumping
	 * 		| isInAir()
	 */
	public void startJump() throws IllegalStateException {
		if (isJumping()) {
			throw new IllegalStateException();
		}
		setYVelocity(INITIAL_Y_VELOCITY);
		setJumping(true);
	}
	
	/**
	 * Immediately stops the upward movement of Mazub if there is one.
	 * @throws IllegalStateException
	 * 		Mazub is not jumping
	 * 		| ! isInAir()
	 */
	public void endJump() throws IllegalStateException {
		if (! isJumping()) {
			throw new IllegalStateException();
		}
		if (getYVelocity() > 0) {
			setYVelocity(0);
		}
	}
	
	/**
	 * Updates the vertical velocity of Mazub to its new value after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	public void updateYVelocity(double step) {
		if (isJumping()) {
			setYVelocity(getYVelocity() + Y_ACCELERATION * step);
		}
		// If Mazub is not jumping, there is no change in vertical speed.
	}
	
	/**
	 * Updates the vertical position of Mazub to its new value after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	public void updateYPosition(double step) {
		if (isJumping()) {
			double newPosition = getYPosition() + getYVelocity() * step + .5 * Y_ACCELERATION * step * step;
			if (newPosition > 0) { //Mazub is still in the air.
				setYPosition(newPosition);
			} else { //Mazub has reached the ground again.
				setYPosition(0);
				setYVelocity(0);
				setJumping(false);
			}
		}
		// If Mazub is not jumping, there is no change in vertical position.
	}
	
	/**
	 * Check if a given position is a valid y-position
	 * @param position, y-coordinate of the bottom left coordinate in meters
	 * @return true if the position is in the interval [0,MAX_Y]
	 */
	public boolean isValidYPosition(double position) {
		return (position >= 0) && (position <= MAX_Y);
	}
	
	//DUCKING		(defensively)
	/**
	 * Starts the ducking motion 
	 * @throws IllegalStateException
	 */
	public void startDuck() throws IllegalStateException {
		if (isDucking()){		//Mazub is ducking
			throw new IllegalStateException();	
		} else setIsDucking(true);
	}
	
	/**
	 * Stops the ducking motion
	 * @throws IllegalStateException
	 */
	public void endDuck() throws IllegalStateException {
		if (!isDucking()){ //Mazub is not ducking
			throw new IllegalStateException();
		} else {
			setIsDucking(false);
			if (getXState() != XState.STILL) {
				setXState(XState.ACCELERATING);
			}
		}
	}
	
	
	//SPRITES  (nominal)
	public Sprite getCurrentSprite() {
		//TODO Maybe clean up cases
		boolean justMoved = (getTimeToLastStateChange() <= 1 && getLastState() != XState.STILL);
		int m = (sprites.length - 8) / 2 - 1;
		if (getXState() == XState.STILL) {
			if (! justMoved) {
				return isDucking() ? getSprite(1) : getSprite(0);
			} else {
				if (isDucking) {
					return isDirectionIsRight() ? getSprite(6) : getSprite(7); 
				} else {
					return isDirectionIsRight() ? getSprite(2) : getSprite(3); 
				}
				
			}
		} else {
			if (isDirectionIsRight()) {
				if (! (isDucking() || isJumping())) {
					return getSprite(8 + ((int) (getTimeToLastStateChange() / 0.075)) % m);
				} else {
					return isDucking() ? getSprite(6) : getSprite(4);
				}
			} else {
				if (! (isDucking() || isJumping())) {
					return getSprite(9 + m + ((int) (getTimeToLastStateChange() / 0.075)) % m);
				} else {
					return isDucking() ? getSprite(7) : getSprite(5);
				}
			}
		}
	}
	
	//GLOBAL
	/**
	 * Advances the time with a given step
	 * @param step
	 * @throws IllegalArgumentException
	 */
	public void advanceTime(double step) throws IllegalArgumentException, IndexOutOfBoundsException { //defensively
		if (!(step>=0 && step<=0.2)) {
			throw new IllegalArgumentException();
		}
		updateXVelocity(step);
		updateXPosition(step);
		updateYVelocity(step);
		updateYPosition(step);
		if (! isValidXPosition(getXPosition())) { //Mazub is at one of the side edges.
			if (getXPosition() < 0) { //Mazub is at the left edge.
				setXPosition(0);
			} else { //Mazub is at the right edge.
				setXPosition(MAX_X);
			}
			setXVelocity(0);
			setXState(XState.STILL);
			throw new IndexOutOfBoundsException();		
		}
		if (! isValidYPosition(getYPosition())) {
			setYPosition(MAX_Y);
			setYVelocity(0);
			throw new IndexOutOfBoundsException();
		}
		advanceTimeToLastStateChange(step);
	}
}
