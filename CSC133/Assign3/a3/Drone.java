package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.Interfaces.ICollider;
import com.mycompany.Interfaces.IStrategy;

public class Drone extends MoveableGameObject implements ICollider  {
	
	int aTimer;
	public boolean collisionFlag;
	
	public Drone () {
		super.setFixed(false);
		super.setMovable(true);
		super.setSteerable(false);
		super.setSize(30);
		super.setAtkValue(4);
		setRandHeading();
		setRandSpeed();
		aTimer = 31;
		collisionFlag = false;
	} //End of Constructor

	@Override
	public void Move(double mapWid, double mapHght) {
		//Overrides Move() from Game Object to add 
		//additional conditions specific to Drones
		if (aTimer > 30)
		{
		setRandHeading();
		setRandSpeed();
		aTimer = 0;
		}
		else 
		{
			aTimer++;
		}
		super.Move(mapWid, mapHght);
	} //End of Move
	
	@Override
	public String printCurrentStats() {
		//Overrides CurrentStats with Drone specific stats to print
		
		String showing = "Drone: " + printTheLocation() + " : " + printTheColor() + " : " + currentSpeedandDir() + " Size = " + super.getSize();
		
		System.out.println(showing);
		
		return showing;
	} //End of printCurrentStats() 
	
	@Override
	public void setColor(char state) {
	// Orange: 255, 128, 0
		setSpecificColor(96, 96, 96);
	}//End of setColor(char state)

	@Override
	public void setStrategy(IStrategy s) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setStratSelect(int stratSelect) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();
		
		int[] xPoints = { xLoc, (xLoc - getSize()), (xLoc + getSize()), xLoc };
		
		int[] yPoints = { (yLoc + getSize()), (yLoc - getSize()), (yLoc - getSize()), (yLoc + getSize()) };
		
		int nPoints = 4;
		
		g.drawPolygon(xPoints, yPoints, nPoints);
	//	g.fillPolygon(xPoints, yPoints, nPoints);
		
	}

	@Override
	public boolean collidesWith(ICollider other) {
		boolean result = false;
		
		double thisCenterX = this.getLocation().getX();
		double thisCenterY = this.getLocation().getY();
		
		double otherCenterX = ((GameObject)other).getLocation().getX();
		double otherCenterY = ((GameObject)other).getLocation().getY();
		
		double dx = (thisCenterX + 10) - otherCenterX;
		double dy = (thisCenterY + 10) - otherCenterY;
		
		double distBetweenCentersSqr = (dx * dx + dy * dy);
		
		int thisRadius= this.getSize() / 2;
		int otherRadius= ((GameObject)other).getSize() / 2;
		
		int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) { result = true ; }
		
		return result;
	} //End of collidesWith(ICollider other)

	@Override
	public void handleCollision(ICollider other) {
		if (other instanceof NonPlayerCyborg || other instanceof Base || other instanceof PlayerCyborg || other instanceof EnergyStation)
		{
			setCollisionFlag();
			other.setCollisionFlag();
		}
		
	} //End of handleCollision(ICollider other)

	@Override
	public void setCollisionFlag() {
		collisionFlag = true;
	}

	@Override
	public boolean getCollisionFlag() {
		return collisionFlag;
	}

	@Override
	public void setCollisionFlagFalse() {
		collisionFlag = false;
		
	}

	
} //End of Drone
