package jumpingalien.model;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import jumpingalien.util.Util;

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
	
	//CONSTRUCTOR
	@Test
	public void constructor_LegalCase(){
		Mazub maz= new Mazub(0, 5, null);
		Util.fuzzyEquals(0.0,maz.getXPosition()); 
		Util.fuzzyEquals(5, maz.getYPosition());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_IllegalXCase() throws Exception{
		new Mazub(-1,0,null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void constructor_IllegalYCase() throws Exception{
		new Mazub(10,-2,null);
	}
	//TODO Another test with MaxY or in this one?
	
	//JUMPING
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
		assertEquals(0,maz.getYVelocity(),0.01);
	}
	
	@Test(expected=IllegalStateException.class)
	public void endJump_IllegalCase() throws Exception {
		Mazub maz= new Mazub(0, 0, null);
		maz.setIsJumping(false);
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
		maz.setIsDucking(false);
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
	@Test
	public void advanceTime_LegalCase(){
		//TODO test every option with different Mazubs? (Landing, moving to the right,...)
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void advanceTime_IllegalStepCase() throws Exception{
		Mazub maz = new Mazub(0,0,null); 
		maz.advanceTime(-0.1); //advancetime 0.3 second test?
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void advanceTime_OverRightEdgeXPositionCase() throws Exception{
		Mazub maz = new Mazub(0,0,null); 
		maz.setXPosition(maz.getMaxXPosition()); 
		maz.setXVelocity(2);
		maz.setDirectionIsRight(true);
		maz.advanceTime(0.2); 
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void advanceTime_OverLeftEdgeXPositionCase() throws Exception{
		Mazub maz = new Mazub(-1,0,null); 
		maz.setXPosition(maz.getMaxXPosition()); 
		maz.setXVelocity(2);
		maz.setDirectionIsRight(false);
		maz.advanceTime(0.2); 
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void advanceTime_OverTopEdgeYPositionCase() throws Exception{
		Mazub maz = new Mazub(0,0,null); 
		maz.setYPosition(maz.getMaxYPosition());
		maz.setYVelocity(2);
		maz.setIsJumping(true);
		maz.advanceTime(0.2); 
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void advanceTime_OverTopEdgeYPositionCase() throws Exception{
		Mazub maz = new Mazub(0,0,null); 
		maz.setYPosition(maz.getMaxYPosition());
		maz.setYVelocity(2);
		maz.setIsJumping(true);
		maz.advanceTime(0.2); 
	}
	
