package jumpingalien.model;

import be.kuleuven.cs.som.annotate.*;
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
 * @invar When isJumping is false, the vertical speed is zero.
 * 		| if (! isJumping())
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

	private double xPosition;
	private double yPosition;
	private double xVelocity;
	private double yVelocity;
	private boolean directionIsRight;
	private boolean isDucking;
	
	private double timeToLastStateChange;
	private XState lastState;
	private boolean lastDirectionIsRight;
	
	private Sprite[] sprites;
	
	//CONSTRUCTOR
	public Mazub(double XPosition, double YPosition, Sprite[] sprites) throws IllegalArgumentException {
		if (! isValidYPosition(YPosition) || ! isValidXPosition(XPosition)){
			throw new IllegalArgumentException("illegal Position");
		}
		setXPosition(XPosition);
		setYPosition(YPosition);
		if (YPosition > 0) {
			setIsJumping(true);
		}
		setXState(XState.STILL);
		this.sprites = sprites;
	}	
	
	//BASIC GETTERS & SETTERS
	/**
	 * 
	 * @return The horizontal position of Mazub in meters
	 */
	@Basic
	private double getXPosition() {
		return xPosition;
	}
	
	/**
	 * 
	 * @return The maximal horizontal position of Mazub in meters
	 */
	@Basic
	private double getMaxXPosition() {
		return this.MAX_X;
	}

	/**
	 * Sets the horizontal position to xPosition.
	 * @param xPosition The new position in meters
	 */
	@Basic
	private void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}
	
	/**
	 * 
	 * @return The vertical position of Mazub in meters
	 */
	@Basic
	private double getYPosition() {
		return yPosition;
	}
	
	/**
	 * 
	 * @return The maximal vertical position of Mazub in meters
	 */
	@Basic
	private double getMaxYPosition() {
		return this.MAX_Y;
	}

	/**
	 * Changes the vertical position of Mazub to yPosition
	 * @param yPosition The new position in meters
	 */
	@Basic
	private void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}
	
	/**
	 * 
	 * @return The horizontal velocity of Mazub in meters/seconds
	 */
	@Basic
	public double getXVelocity() {
		return xVelocity;
	}

	/**
	 * Changes the horizontal velocity of Mazub to xVelocity
	 * @param xVelocity The new velocity in meters/seconds
	 */
	@Basic
	private void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	/**
	 * 
	 * @return The vertical velocity of Mazub in meters/seconds
	 */
	@Basic
	public double getYVelocity() {
		return yVelocity;
	}

	/**
	 * Changes the vertical velocity of Mazub to yVelocity
	 * @param yVelocity The new velocity in meters/seconds
	 */
	@Basic
	private void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	/**
	 * Check what the horizontal state of Mazub is:
	 * 	XState.STILL: Mazub is not moving horizontally
	 * 	XState.ACCELERATING: Mazub is in his accelerating phase
	 * 	XState.MAXED_OUT: Mazub has reached its maximum speed
	 * @return The state of Mazub's horizontal movement
	 */
	@Basic
	private XState getXState() {
		return xState;
	}

	/**
	 * Changes the state of Mazub's horizontal movement to xState
	 * @param xState The new xState of Mazub (STILL, ACCELERATING or MAXED_OUT)
	 */
	@Basic
	private void setXState(XState xState) {
		this.xState = xState;
	}
	
	/**
	 * Returns whether Mazub is jumping or not
	 * @return True if Mazub is jumping, false if not
	 */
	@Basic
	private boolean isJumping() {
		return isJumping;
	}

	/**
	 * Changes the jumping state of Mazub to isJumping
	 * @param isJumping
	 */
	@Basic
	private void setIsJumping(boolean isJumping) {
		this.isJumping = isJumping;
	}
	
	/**
	 * Returns whether Mazub is ducking or not
	 * @return true if Mazub is ducking, false if not
	 */
	@Basic
	private boolean isDucking() {
		return isDucking;
	}

	/**
	 * Changes the ducking state of Mazub
	 * @param isDucking
	 */
	@Basic
	private void setIsDucking(boolean isDucking) {
		this.isDucking = isDucking;
	}
	
	/**
	 * Returns whether Mazub is facing right (positive x)
	 * @return true if Mazub is facing right, false if Mazub is facing left
	 */
	@Basic
	private boolean directionIsRight() {
		return directionIsRight;
	}

	/**
	 * Changes the facing of Mazub to the given direction
	 * @param directionIsRight True if the new direction is right, false if it's left
	 */
	@Basic
	private void setDirectionIsRight(boolean directionIsRight) {
		this.directionIsRight = directionIsRight;
	}

	/**
	 * Saves the given direction as the direction Mazub has been going in previously.
	 * @param lastDirectionIsRight True if the direction is right, false if it's left
	 */
	@Basic
	private void setLastDirectionIsRight(boolean lastDirectionIsRight) {
		this.lastDirectionIsRight = lastDirectionIsRight;
	}

	/**
	 * Returns whether the previous facing of Mazub was to the right (positive x)
	 * @return lastDirectionIsRight true if Mazub was facing right, false if Mazub was facing left
	 */
	@Basic
	private boolean lastDirectionIsRight() {
		return lastDirectionIsRight;
	}

	/**
	 * Returns the previous state of Mazub's horizontal movement
	 * @return XState.STILL, XState.ACCELERATING or XState.MAXED_OUT
	 */
	@Basic
	private XState getLastState() {
		return lastState;
	}
	
	/**
	 * Saves the given xState as the previous xState Mazub was in
	 * @param XState.STILL, XState.ACCELERATING or XState.MAXED_OUT
	 */
	@Basic
	private void setLastState(XState lastState) {
		this.lastState = lastState;
	}

	/**
	 * Returns the time that has passed since the last change of Mazub's xState
	 * @return amount of time in seconds
	 */
	@Basic
	private double getTimeToLastStateChange() {
		return timeToLastStateChange;
	}

	/**
	 * Changes the time that has passed since the last change of Mazub's state to 0
	 */
	@Basic
	private void resetTimeToLastStateChange() {
		this.timeToLastStateChange = 0;
	}
	
	/**
	 * Add time to the time that has passed since the last change of Mazub's state 
	 * @param step The amount of time that has to be added in seconds
	 */
	@Basic
	private void advanceTimeToLastStateChange(double step) {
		this.timeToLastStateChange += step;
	}
	
	/**
	 * Returns the Sprites that belong to Mazub
	 * @return the array of sprites
	 */
	@Basic
	private Sprite[] getSprites() {
		return this.sprites;
	}
	
	//OTHER INSPECTORS
	/**
	 * Returns the size of Mazub in the x-direction
	 * @return The current width of Mazub in pixels
	 */
	public int getWidth() {
		return getCurrentSprite().getWidth();
	}
	
	/**
	 * Returns the size of Mazub in the y-direction
	 * @return The current height of Mazub in pixels
	 */
	public int getHeight() {
		return getCurrentSprite().getHeight();
	}
	
	/**
	 * Returns the sprite at a given index
	 * @param index
	 * @return The sprite at index index of getSprites()
	 */
	private Sprite getSprite(int index) {
		return getSprites()[index];
	}
	
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
	 * Returns the x-component of Mazub's current acceleration.
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
	 * Returns the y-component of Mazub's current acceleration.
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
	 */
	@Raw
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
	 */
	@Raw
	public void endMove() {
		assert getXState() != XState.STILL;
		setLastState(getXState());
		setXState(XState.STILL);
		setXVelocity(0);
		resetTimeToLastStateChange();
	}
	
	/**
	 * Calculates the new horizontal velocity of Mazub after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	private double calculateNewXVelocity(double step) {
		if (getXState() == XState.ACCELERATING) {
			double newVelocity = getXVelocity() + X_ACCELERATION * step;
			if (! isDucking) {
				if (newVelocity >= MAX_X_VELOCITY) {	//Mazub is not ducking and has reached max speed.
					setXState(XState.MAXED_OUT);
					return MAX_X_VELOCITY;
				} else {	//Mazub is not ducking and is still accelerating.
					return newVelocity;
				}
			} else {
				if (newVelocity >= MAX_X_DUCKING_VELOCITY) {	//Mazub is ducking and has reached max speed.
					setXState(XState.MAXED_OUT);
					return MAX_X_DUCKING_VELOCITY;
				} else { 	//Mazub is ducking and is still accelerating.
					return newVelocity;
				}
			}
		} else return getXVelocity();
		//If Mazub has already reached max speed or is not moving horizontally, there is no change in speed.
	}
	
	/**
	 * Calculates the new horizontal position of Mazub after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	private double calculateNewXPosition(double step) {
		double distanceTraveled = 0;
		if (getXState() == XState.ACCELERATING) { //Speed is not constant.
			distanceTraveled = getXVelocity() * step + .5 * X_ACCELERATION * step * step;
		} else { //Speed is constant (0 or MAX_X_SPEED)
			distanceTraveled = getXVelocity() * step;
		}
		return directionIsRight() ? getXPosition()+distanceTraveled : getXPosition()-distanceTraveled;
		//When going to the left, the distance is subtracted from the old position.
		//When going to the right, the distance is added to the old position.
	}
	
	/**
	 * Check if a given position is a valid x-position
	 * @param position, x-coordinate of the bottom left coordinate in meters
	 * @return true if the position is in the interval [0,MAX_X]
	 */
	private boolean isValidXPosition(double position) {
		return (position >= 0) && (position <= MAX_X);
	}
	
	//VERTICAL MOVEMENT
	/**
	 * Starts Mazub's jump.
	 * @throws IllegalStateException
	 * 		Mazub is already jumping
	 * 		| isJumping()
	 */
	public void startJump() throws IllegalStateException {
		if (isJumping()) {
			throw new IllegalStateException();
		}
		setYVelocity(INITIAL_Y_VELOCITY);
		setIsJumping(true);
	}
	
	/**
	 * Immediately stops the upward movement of Mazub if there is one.
	 * @throws IllegalStateException
	 * 		Mazub is not jumping
	 * 		| ! isJumping()
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
	 * Calculates the new vertical velocity of Mazub after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	private double calculateNewYVelocity(double step) {
		if (isJumping()) {
			return (getYVelocity() + Y_ACCELERATION * step);
		}
		else return getYVelocity();
		// If Mazub is not jumping, there is no change in vertical speed.
	}
	
	/**
	 * Calculates the new vertical position of Mazub after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	private double calculateNewYPosition(double step) {
		if (isJumping()) {
			return getYPosition() + getYVelocity() * step + .5 * Y_ACCELERATION * step * step;
		} else {
			return 0; //Mazub is not jumping
		}
	}
	
	/**
	 * Check if a given position is a valid y-position
	 * @param position, y-coordinate of the bottom left coordinate in meters
	 * @return true if the position is in the interval [0,MAX_Y]
	 */
	private boolean isValidYPosition(double position) {
		return (position >= 0) && (position <= MAX_Y);
	}
	
	//DUCKING		(defensively)
	/**
	 * Starts the ducking motion 
	 * @throws IllegalStateException
	 */
	public void startDuck() throws IllegalStateException {
		if (isDucking()) {		//Mazub is already ducking
			throw new IllegalStateException();	
		} else {
			setIsDucking(true);
		}
	}
	
	/**
	 * Stops the ducking motion
	 * @throws IllegalStateException
	 */
	public void endDuck() throws IllegalStateException {
		if (! isDucking()) { //Mazub is not ducking
			throw new IllegalStateException();
		} else {
			setIsDucking(false);
			if (getXState() == XState.MAXED_OUT) { 
				//When coming out of ducking motion at MAX_X_DUCKING_VELOCITY, Mazub should start accelerating again
				setXState(XState.ACCELERATING);
			}
		}
	}
	
	
	//SPRITES  (nominal)
	public Sprite getCurrentSprite() {
		boolean justMoved = (getTimeToLastStateChange() <= 1 && getLastState() != XState.STILL);
		int m = (sprites.length - 8) / 2 - 1;
		if (getXState() == XState.STILL) {
			if (! justMoved) {
				return isDucking() ? getSprite(1) : getSprite(0);
			} else {
				if (isDucking) {
					return directionIsRight() ? getSprite(6) : getSprite(7); 
				} else {
					return directionIsRight() ? getSprite(2) : getSprite(3); 
				}
				
			}
		} else {
			if (directionIsRight()) {
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
	 * 			The amount of time that has to be advanced. This amount should be in [0, 0.2]
	 * @throws IllegalArgumentException
	 * 			Step is too large or too small.
	 * 			| (step < 0) || (step > 0.2)
	 * @throws IndexOutOfBoundsException
	 * 			The calculated next position of Mazub are outside of the game world. It is not a valid position.
	 * 			| (! isValidXPosition(calculateNewXPosition(step))) || (! isValidYPosition(calculateNewYPosition(step)))
	 */
	@Raw
	public void advanceTime(double step) throws IllegalArgumentException, IndexOutOfBoundsException { //defensively
		if (!(step>=0 && step<=0.2)) {
			throw new IllegalArgumentException();
		}
		double newXVelocity = calculateNewXVelocity(step);
		double newXPosition = calculateNewXPosition(step);
		double newYVelocity = calculateNewYVelocity(step);
		double newYPosition = calculateNewYPosition(step);
		
		if (newXPosition < 0) { //Mazub reaches the left edge
			setXPosition(0);
			setXVelocity(0);
			setXState(XState.STILL);
			throw new IndexOutOfBoundsException();
		} else if (newXPosition > getMaxXPosition()) { //Mazub reaches the right edge
			setXPosition(getMaxXPosition());
			setXVelocity(0);
			setXState(XState.STILL);
			throw new IndexOutOfBoundsException();		
		} else {
			setXPosition(newXPosition);
			setXVelocity(newXVelocity);
		}
		
		if (newYPosition <= 0) { //Mazub reaches the ground
			setYPosition(0); 
			setYVelocity(0);
			setIsJumping(false);
		} else if (newYPosition > getMaxYPosition()) { //Mazub reaches the top edge
			setYPosition(getMaxYPosition());
			setYVelocity(0);
		} else {
			setYPosition(newYPosition);
			setYVelocity(newYVelocity);
		}
		advanceTimeToLastStateChange(step);
	}
}
