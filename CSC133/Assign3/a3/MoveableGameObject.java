package com.mycompany.a3;

import com.codename1.ui.geom.Point2D;
import com.mycompany.Interfaces.IStrategy;
import com.codename1.ui.geom.Dimension;

public abstract class MoveableGameObject extends ObjectAttributes {

	private int objSpeed;
	private int objHeading;
	
	public MoveableGameObject() {
		objSpeed = rand.nextInt(16);
		objHeading = 0;
	} //End of Constructor
	
	public void Move(double mapWid, double mapHght) //implementing Move here so each moveable object can use this
	{
		
		Point2D oldLocation = getLocation(); // Gets current location
		
		Point2D newLocation = new Point2D(0,0); // Initializes new location
		
		int properAngle = 90-objHeading; //Handles angle
		double deltaX = 0;
		double deltaY = 0;
		
		//Controls movement
		if(objHeading == 0 || objHeading == 180) //Handles vertical
		{
			deltaY = Math.sin(Math.toRadians(properAngle)) * objSpeed;
		} //End of if(objHeading == 0 || objHeading == 180)
		else if( objHeading == 90 || objHeading == 270 ) //handles horizontal
			deltaX = Math.cos(Math.toRadians(properAngle)) * objSpeed;
		else // if both vertical and horizontal movement required
		{
			deltaX = Math.cos(Math.toRadians(properAngle))*objSpeed; 
			deltaY = Math.sin(Math.toRadians(properAngle)) * objSpeed;
		} //End of else
		
		//New location is set
		newLocation.setX(deltaX + oldLocation.getX()); 
		newLocation.setY(deltaY + oldLocation.getY());
		
		if (newLocation.getX() >= mapWid)
		{
			newLocation.setX(newLocation.getX() - 50);
		}
		else if (newLocation.getX() <= 0.0)
		{
			newLocation.setX(Math.abs(newLocation.getX() - 50));
		}
		
		if (newLocation.getY() >= mapHght)
		{
			newLocation.setY(newLocation.getY() - 50);
		}
		else if (newLocation.getY() <= 0.0)
		{
			newLocation.setY(Math.abs(newLocation.getY() - 50));
			
		}
		newLocation.setX(newLocation.getX());
		newLocation.setY(newLocation.getY());
		
		setLocation(newLocation); //Changes location to new location
		
	} //End of Move()
	
	public String currentSpeedandDir() {
	//Prints current speed and direction.
		String str = " : Speed = " + getSpeed() + " : Heading = " + getHeading();
		return str;
	} //End of  currentSpeedandDir()
	
	
	
	///////////////
	//GET METHODS//
	///////////////
	
	public int getHeading() {
		return objHeading;
	} //End of objHeading()
	
	public int getSpeed() {
		return objSpeed;
	} //End of getSpeed()
	
	
	
	///////////////
	//SET METHODS//
	///////////////
	
	public void setExactSpeed(int x) {
		objSpeed = x;
	} //End of setExactSpeed()
	
	public void setHeading(int x) {
		objHeading = x;
		
		System.out.println("objHeading is " + objHeading);
	} //End of setHeading()
	
	public void setRandHeading() {
		objHeading = rand.nextInt(360);
	} //End of setRandHeading()
	
	public void setRandSpeed() {
		objSpeed = rand.nextInt(15);
	} //End of setRandSpeed()
	
	public abstract void setStrategy(IStrategy s);
	
	public abstract void setStratSelect(int stratSelect);

} //End of MoveableGameObject
