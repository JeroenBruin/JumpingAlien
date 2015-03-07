/**
 * Class that implements the character of the game Jumping Alien.
 * 
 * @invar When the xState is STILL, the horizontal speed is 0
 * 		| if (this.getXState() == XState.STILL)
 * 		    this.getXVelocity() == 0;	
 * @invar When the xState is not STILL, the horizontal speed is always greater than 0
 * 		| if (this.getXState() != XState.STILL)
 * 			this.getXVelocity > 0;
 * @author Tim Geypens, Jeroen Bruin
 *
 */
public class Mazub {
	// VARIABLE DECLARATION
	private final double INITIAL_X_VELOCITY = 1;
	private static final double X_ACCELERATION = 0.9;
	private final double MAX_X_VELOCITY = 3;
	private final double MAX_DUCKING_X_VELOCUTY = 1;
	private XState xState;
	
	private final double INITIAL_Y_VELOCITY = 8;
	private static final double Y_ACCELERATION = -10;
	private boolean inAir;
	
	public int width;
	public int height;
	public int xPosition;
	public int yPosition;
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
	
	//GETTERS & SETTERS

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

	public int getXPosition() {
		return xPosition;
	}

	public int getYPosition() {
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

	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public void setYPosition(int yPosition) {
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

	public char getOrientation() {
		return 'N';
	}
	
	//HORIZONTAL MOVEMENT
	/**
	 * @post Mazub is moving.
	 * 		| (new.xState != XState.STILL)
	 */
	public void startMove(boolean directionIsRight) {
		this.setDirectionIsRight(directionIsRight);
		if (this.getXState() == XState.STILL) {
			this.setLastState(this.getXState());
			this.setXState(XState.ACCELERATING);
			this.resetTimeToLastStateChange();
		}
	}
	
	/**
	 * @post The horizontal velocity of Mazub is zero. It is standing still in the x-direction.
	 * 		| (new.getVelocity() == 0) && (new.getXState = XState.STILL)
	 * @Raw
	 */
	public void endMove() {
		if (this.getXState() != XState.STILL) {
			this.setLastState(this.getXState());
			this.setXState(XState.STILL);
			this.setXVelocity(0);
			this.resetTimeToLastStateChange();
		}
	}
	
	public void updateXVelocity(double step) {
	}
	
	public void updateXPosition(double step) {
	}
	
	//VERTICAL MOVEMENT
	
	//DUCKING
	
	//GLOBAL
	public void advanceTime(double step) {
	
	}
}
