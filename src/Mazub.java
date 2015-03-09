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
	private boolean inAir;
	
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
	
	//CONSTRUCTOR
	public Mazub(int position) {
	}	
	
	//BASIC GETTERS & SETTERS
	public XState getXState() {
		return xState;
	}

	public boolean isInAir() {
		return inAir;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public double getXPosition() {
		return xPosition;
	}

	public double getYPosition() {
		return yPosition;
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public boolean isDirectionIsRight() {
		return directionIsRight;
	}

	public boolean isDucking() {
		return isDucking;
	}

	public double getTimeToLastStateChange() {
		return timeToLastStateChange;
	}

	public XState getLastState() {
		return lastState;
	}

	public boolean isLastDirectionIsRight() {
		return lastDirectionIsRight;
	}

	public void setXState(XState xState) {
		this.xState = xState;
	}

	public void setInAir(boolean inAir) {
		this.inAir = inAir;
	}

	public void setXPosition(double xPosition) {
		this.xPosition = xPosition;
	}

	public void setYPosition(double yPosition) {
		this.yPosition = yPosition;
	}

	public void setXVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	public void setYVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	public void setDirectionIsRight(boolean directionIsRight) {
		this.directionIsRight = directionIsRight;
	}

	public void setIsDucking(boolean isDucking) {
		this.isDucking = isDucking;
	}

	public void setLastState(XState lastState) {
		this.lastState = lastState;
	}

	public void setLastDirectionIsRight(boolean lastDirectionIsRight) {
		this.lastDirectionIsRight = lastDirectionIsRight;
	}
	
	public void resetTimeToLastStateChange() {
		this.timeToLastStateChange = 0;
	}
	
	public void advanceTimeToLastStateChange(double step) {
		this.timeToLastStateChange += step;
	}
	
	//OTHER INSPECTORS
	public char getOrientation() {
		return 'N';
	}
	
	/**
	 * Get the displayed horizontal position of Mazub
	 * @return rounded down version of the current vertical position.
	 */
	public int getEffectiveXPosition() {
		return (int) getXPosition();
	}
	
	/**
	 * Get the displayed vertical position of Mazub
	 * @return rounded down version of the current vertical position.
	 */
	public int getEffectiveYPosition() {
		return (int) getYPosition();
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
		setXPosition(getXPosition() + distanceTraveled);
	}
	
	//VERTICAL MOVEMENT
	/**
	 * Starts Mazub's jump.
	 * @throws IllegalStateException
	 * 		Mazub is already jumping
	 * 		| isInAir()
	 */
	public void startJump() throws IllegalStateException {
		if (isInAir()) {
			throw new IllegalStateException();
		}
		setYVelocity(INITIAL_Y_VELOCITY);
		setInAir(true);
	}
	
	/**
	 * Immediately stops the upward movement of Mazub if there is one.
	 * @throws IllegalStateException
	 * 		Mazub is not jumping
	 * 		| ! isInAir()
	 */
	public void endJump() throws IllegalStateException {
		if (! isInAir()) {
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
		if (isInAir()) {
			setYVelocity(getYVelocity() + Y_ACCELERATION * step);
		}
		// If Mazub is not jumping, there is no change in vertical speed.
	}
	
	/**
	 * Updates the vertical position of Mazub to its new value after a given amount of time.
	 * @param step: the amount of time that has to be advanced.
	 */
	public void updateYPosition(double step) {
		if (isInAir()) {
			double newPosition = getYPosition() + getYVelocity() * step + .5 * Y_ACCELERATION * step * step;
			if (newPosition > 0) { //Mazub is still in the air.
				setYPosition(newPosition);
			} else { //Mazub has reached the ground again.
				setYPosition(0);
				setYVelocity(0);
				setInAir(false);
			}
		}
		// If Mazub is not jumping, there is no change in vertical position.
	}
	
	//DUCKING		(defensively)
	/**
	 * Starts the ducking motion 
	 * @throws IllegalStateException
	 */
	public void startDuck() throws IllegalStateException {
		if (isDucking() || isInAir()){		//Mazub is ducking or still in the air
			throw new IllegalStateException();	
		}else setIsDucking(true);
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
		}
		
	}
	
	//GLOBAL
	/**
	 * Advances the time with a given step
	 * @param step
	 * @throws IllegalArgumentException
	 */
	public void advanceTime(double step) throws IllegalArgumentException,OutOfRangeException { //defensively
		if (!(step>=0 && step<=0.2)){
			throw new IllegalArgumentException();
		if //can't check if Mazub stays in boundaries of game world, change last step in update functions and update manually??
		} else{
		updateXVelocity(step);
		updateXPosition(step);
		updateYVelocity(step);
		updateYPosition(step);
		}
	}
	
	//SPRITES  (nominal)
	public int getCurrentSprite(){
		return 1;
	}
}
