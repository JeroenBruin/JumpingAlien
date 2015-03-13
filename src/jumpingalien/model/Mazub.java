package jumpingalien.model;

import be.kuleuven.cs.som.annotate.Basic;
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
	public Mazub(double XPosition, double YPosition, Sprite[] sprites) throws IllegalArgumentException {
		if (! isValidYPosition(YPosition) || ! isValidXPosition(XPosition)){
			throw new IllegalArgumentException("illegal Position");
		}
		//TODO setIsJumping(true) if YPosition>0?
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

	@Basic
	/**
	 * Returns the horizontal position of Mazub.
	 */
	public double getXPosition() {
		return xPosition;
	}
	
	@Basic
	/**
	 * Returns the maximal horizontal position of Mazub.
	 */
	public double getMaxXPosition() {
		return this.MAX_X;
	}

	@Basic
	/**
	 * Sets the horizontal position to xPosition.
	 * @param xPosition
	 */
	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}
	
	@Basic
	/**
	 * Returns the vertical position of Mazub.
	 * @return
	 */
	public double getYPosition() {
		return yPosition;
	}
	
	@Basic
	/**
	 * Returns the maximal vertical position.
	 */
	public double getMaxYPosition() {
		return this.MAX_Y;
	}

	@Basic
	/**
	 * Changes the vertical position of Mazub to yPosition
	 * @param yPosition
	 */
	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}
	
	@Basic
	/**
	 * Returns the horizontal velocity of Mazub
	 * @return
	 */
	public double getXVelocity() {
		return xVelocity;
	}

	@Basic
	/**
	 * Changes the horizontal velocity of Mazub to xVelocity
	 * @param xVelocity
	 */
	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	@Basic
	/**
	 * Returns the vertical velocity of Mazub
	 * @return
	 */
	public double getYVelocity() {
		return yVelocity;
	}

	@Basic
	/**
	 * Changes the vertical velocity of Mazub to yVelocity
	 * @param yVelocity
	 */
	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	@Basic
	/**
	 * Returns the state of Mazub's horizontal movement 
	 * @return the state of Mazub's horizontal movement  
	 */
	public XState getXState() {
		return xState;
	}

	@Basic
	/**
	 * Changes the state of Mazub's horizontal movement to xState
	 * @param xState
	 */
	public void setXState(XState xState) {
		this.xState = xState;
	}
	
	@Basic
	/**
	 * Returns whether Mazub is jumping or not
	 * @return true if Mazub is jumping, else false
	 */
	public boolean isJumping() {
		return isJumping;
	}

	@Basic
	/**
	 * Changes the jumping state of Mazub to inAir
	 * @param inAir
	 */
	public void setIsJumping(boolean inAir) {
		this.isJumping = inAir;
	}
	
	@Basic
	/**
	 * Returns whether Mazub is ducking or not
	 * @return true if Mazub is ducking, else false
	 */
	public boolean isDucking() {
		return isDucking;
	}

	@Basic
	/**
	 * Changes the ducking state of Mazub
	 * @param isDucking
	 */
	public void setIsDucking(boolean isDucking) {
		this.isDucking = isDucking;
	}
	
	@Basic
	//TODO clarifying documentation 
	/**
	 * Returns if Mazub is facing right
	 * @return
	 */
	public boolean isDirectionIsRight() {
		return directionIsRight;
	}

	@Basic
	/**
	 * Changes the direction of Mazub
	 * @param directionIsRight
	 */
	public void setDirectionIsRight(boolean directionIsRight) {
		this.directionIsRight = directionIsRight;
	}

	@Basic
	//TODO Clarifying the documentation as well as the next one
	/**
	 * Changes the last direction of Mazub
	 * @param lastDirectionIsRight
	 */
	public void setLastDirectionIsRight(boolean lastDirectionIsRight) {
		this.lastDirectionIsRight = lastDirectionIsRight;
	}

	@Basic
	/**
	 * Retu
	 * @return
	 */
	public boolean isLastDirectionIsRight() {
		return lastDirectionIsRight;
	}

	@Basic
	/**
	 * Returns the previous state of Mazub's horizontal movement
	 * @return
	 */
	public XState getLastState() {
		return lastState;
	}

	@Basic
	/**
	 * Change the value of the previous state of Mazub to lastState
	 * @param lastState
	 */
	public void setLastState(XState lastState) {
		this.lastState = lastState;
	}

	@Basic
	/**
	 * Returns the time that has passed since the last change of Mazub's state
	 * @return
	 */
	public double getTimeToLastStateChange() {
		return timeToLastStateChange;
	}

	@Basic
	/**
	 * Changes the time that has passed since the last change of Mazub's state to 0
	 */
	public void resetTimeToLastStateChange() {
		this.timeToLastStateChange = 0;
	}
	
	@Basic
	/**
	 * Add time to the time that has passed since the last change of Mazub's state 
	 * @param step
	 */
	public void advanceTimeToLastStateChange(double step) {
		this.timeToLastStateChange += step;
	}
	
	@Basic
	/**
	 * Returns the Sprites that belong to Mazub
	 * @return
	 */
	public Sprite[] getSprites() {
		return this.sprites;
	}
	
	@Basic
	//TODO Do you have to change the sprites? Not given to the constructor?
	public void setSprites(Sprite[] sprites) {
		this.sprites = sprites;
	}
	
	//OTHER INSPECTORS
	/**
	 * Returns the sprite at a given index
	 * @param index
	 * @return The sprite at index index of getSprites()
	 */
	public Sprite getSprite(int index) {
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
	public double calculateNewXVelocity(double step) {
		if (getXState() == XState.ACCELERATING) {
			double newVelocity = getXVelocity() + X_ACCELERATION * step;
			if (! isDucking) {
				if (newVelocity >= MAX_X_VELOCITY) {	//Mazub is not ducking and has reached max speed.
					return MAX_X_VELOCITY;
				} else {	//Mazub is not ducking and is still accelerating.
					return newVelocity;
				}
			} else {
				if (newVelocity >= MAX_X_DUCKING_VELOCITY) {	//Mazub is ducking and has reached max speed.
					return MAX_X_DUCKING_VELOCITY;
				} else { 	//Mazub is ducking and is still accelerating.
					return newVelocity;
				}
			}
		} else return getXVelocity();
		//If Mazub has already reached max speed or is not moving horizontally, there is no change in speed.
	}
	
	/**
	 * Updates the horizontal position of Mazub to its new value after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	public double calculateNewXPosition(double step) {
		double distanceTraveled = 0;
		if (getXState() == XState.ACCELERATING) { //Speed is not constant.
			distanceTraveled = getXVelocity() * step + .5 * X_ACCELERATION * step * step;
		} else { //Speed is constant (0 or MAX_X_SPEED)
			distanceTraveled = getXVelocity() * step;
		} //If Mazub is not moving horizontally, the distance traveled is the initial value of distanceTraveled (0)
		return isDirectionIsRight() ? getXPosition()+distanceTraveled : getXPosition()-distanceTraveled;
		//When going to the left, the distance is subtracted from the old position.
		//When going to the right, the distance is added to the old position.
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
		setIsJumping(true);
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
	 * Calculates the new vertical velocity of Mazub after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	public double calculateNewYVelocity(double step) {
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
	public double calculateNewYPosition(double step) {
		if (isJumping()) {
			double newPosition = getYPosition() + getYVelocity() * step + .5 * Y_ACCELERATION * step * step;
			if (newPosition > 0) { //Mazub is still in the air.
				return newPosition;
			} else { //Mazub has reached the ground again.
				return 0;
			}
		} return 0;
		//TODO nog ff checken
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
		double newXVelocity = calculateNewXVelocity(step);
		double newXPosition = calculateNewXPosition(step);
		double newYVelocity = calculateNewYVelocity(step);
		double newYPosition = calculateNewYPosition(step);
		
		if (! isValidXPosition(getXPosition())) { //Mazub is at one of the side edges.
			if (getXPosition() < 0) { //Mazub is at the left edge.
				setXPosition(0);
			} else { //Mazub is at the right edge.
				setXPosition(MAX_X);
			}
			setXVelocity(0);
			setXState(XState.STILL);
			throw new IndexOutOfBoundsException();		
		} else {
			setXPosition(newXPosition);
			setXVelocity(newXVelocity);
		}
		if (! isValidYPosition(getYPosition())) { //Mazub is at top edge
			setYPosition(MAX_Y); 
			setYVelocity(0);
			throw new IndexOutOfBoundsException();
		}else {
			if (newYPosition <= 0){ //Mazub reaches the ground
				setYPosition(0);
				setYVelocity(0);
				setIsJumping(false);
			} else {
				setYPosition(newYPosition);
				setYVelocity(newYVelocity);
			}
			
		}
		advanceTimeToLastStateChange(step);
	}
}
