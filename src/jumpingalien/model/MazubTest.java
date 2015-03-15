package jumpingalien.model;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MazubTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	//OTHER INSPECTORS
	@Test
	public void getXPixel() {
		Mazub maz = new Mazub(1.239, 0, null);
		assertEquals(123, maz.getXPixel(), 0.001);
	}
	
	@Test
	public void getYPixel() {
		Mazub maz = new Mazub(0, 6.5401, null);
		assertEquals(654, maz.getYPixel(), 0.001);
	}
	
	//CONSTRUCTOR
	@Test
	public void constructor_LegalCase() { 
		Mazub maz = new Mazub(0, 5, null);
		assertEquals(0,maz.getXPosition(), 0.001);
		assertEquals(5,maz.getYPosition(), 0.001);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_IllegalXCase() throws Exception {
		new Mazub(-1,0,null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_IllegalYCase() throws Exception {
		new Mazub(10,-2,null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_IllegalXCase2() throws Exception {
		new Mazub(2000,0,null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_IllegalYCase2() throws Exception {
		new Mazub(20,2000,null);
	}
	
	//HORIZONTAL MOVEMENT
	@Test
	public void startMove() {
		Mazub maz = new Mazub(0, 0, null);
		maz.startMove(true);
		maz.advanceTime(0.2);
		assertEquals(1*0.2+0.5*0.9*0.2*0.2, maz.getXPosition(), 0.001);
	}
	
	@Test
	public void endJump() {
		Mazub maz = new Mazub(0, 0, null);
		maz.startMove(true);
		maz.advanceTime(0.2);
		maz.endMove();
		assertEquals(0, maz.getXVelocity(), 0.001);
		maz.advanceTime(0.2);
		assertEquals(1*0.2+0.5*0.9*0.2*0.2, maz.getXPosition(), 0.001);
	}
	
	//VERTICAL MOVEMENT
	@Test
	public void startJump_LegalCase() {
		Mazub maz= new Mazub(0, 0, null);
		maz.setIsJumping(false);
		maz.startJump();
		assertTrue(maz.isJumping());
	}
	
	@Test(expected=IllegalStateException.class)
	public void startJump_IllegalCase() throws Exception {
		Mazub maz= new Mazub(0, 0, null);
		maz.setIsJumping(true);
		maz.startJump();
	}
	
	@Test
	public void endJump_LegalCase() {
		Mazub maz= new Mazub(0, 1, null);
		maz.setIsJumping(true);
		maz.setYVelocity(1);
		maz.endJump();
		assertEquals(0, maz.getYVelocity(), 0.001);
	}
	
	@Test(expected=IllegalStateException.class)
	public void endJump_IllegalCase() throws Exception {
		Mazub maz= new Mazub(0, 0, null);
		maz.setIsJumping(true);
		maz.startJump();
	}
	
	// DUCKING
	@Test
	public void startDuck_LegalCase() {
		Mazub maz= new Mazub(0, 0, null);
		maz.setIsDucking(false);
		maz.startDuck();
		assertTrue(maz.isDucking());
	}
	
	@Test(expected=IllegalStateException.class)
	public void startDuck_IllegalCase() throws Exception {
		Mazub maz= new Mazub(0, 0, null);
		maz.setIsDucking(true);
		maz.startDuck();
	}
	
	@Test
	public void endDuck_LegalCase() {
		Mazub maz= new Mazub(0, 0, null);
		maz.setIsDucking(true);
		maz.endDuck();
		assertFalse(maz.isDucking());
	}
	
	@Test(expected=IllegalStateException.class)
	public void endDuck_IllegalCase() throws Exception {
		Mazub maz= new Mazub(0, 0, null);
		maz.setIsDucking(false);
		maz.endDuck();
	}
	
	//ADVANCETIME
	@Test(expected=IllegalArgumentException.class)
	public void advanceTime_IllegalStepCase() throws Exception{
		Mazub maz = new Mazub(0,0,null); 
		maz.advanceTime(-0.1);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void advanceTime_IllegalStepCase2() throws Exception{
		Mazub maz = new Mazub(0,0,null); 
		maz.advanceTime(0.3);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void advanceTime_IllegalXPositionCase() throws Exception{
		Mazub maz = new Mazub(0,0,null); 
		maz.setXPosition(maz.getMaxXPosition());
		maz.setXVelocity(2);
		maz.setDirectionIsRight(true);
		maz.advanceTime(0.2); 
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void advanceTime_IllegalYPositionCase() throws Exception{
		Mazub maz = new Mazub(0,0,null); 
		maz.setYPosition(maz.getMaxYPosition());
		maz.setYVelocity(2);
		maz.setIsJumping(true);
		maz.advanceTime(0.2);
	}
}

