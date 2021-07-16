package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.Interfaces.ICollider;

public class EnergyStation extends FixedObject implements ICollider {

	private int capacity;
	public boolean collisionFlag;
	
	public EnergyStation () {
		super.setFixed(true);
		super.setMovable(false);
		super.setSteerable(false);
		capacity = rand.nextInt(100) + 10;
		super.setRandSize();
		collisionFlag = false;
	} //End of Constructor
	
	public void stationCollision () {
	//This is in place for any and all
	//things affecting the Energy Station
	//During collision
		capacity = 0;
		setColor('2');
	} //End of stationCollision
	
	
	public int getCapacity () {
		return capacity;
	} //End of getCapacity
	
	@Override
	public void setColor(char state) {
		//This will set the color based on 
		//how much damage an object has.
		//This will be overridden depending 
		//on object
			int r, g, b;
			switch(state)
			{
			case '1': 
				r = 102;
				g = 204;
				b = 0;
				setSpecificColor(r, g, b);
				
				//System.out.println("Object color changed!");
				break;
			case '2':
				r = 178;
				g = 255;
				b = 102;
				setSpecificColor(r, g, b);
				
			//	System.out.println("Object color changed!");
				break;
			} //End of switch
		} //End of setColor(int state)
	
	@Override
	public String printCurrentStats() {
		//Overrides CurrentStats with Energy Station specific stats to print
		String showing = "Energy Station: " + printTheLocation() + " : " + printTheColor() + " : Size = " + super.getSize() + " : Capacity = " + getCapacity();
		
		System.out.println(showing);
		
		return showing;
		
	} //End of printCurrentStats()

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();
		
		int r = this.getSize();
		if (r < 0)
			r = 15;
		
		if (this.isSelected())
			g.drawArc(xLoc, yLoc, r, r, 0, 360);
		else	
		{
			g.drawArc(xLoc, yLoc, r, r, 0, 360);
			g.fillArc(xLoc, yLoc, r, r, 0, 360);
		}
		
		g.setColor(ColorUtil.BLACK);
		g.drawString (Integer.toString(this.capacity), 
				(int) this.getLocation().getX()+pCmpRelPrnt.getX()+10, 
				(int) this.getLocation().getY()+pCmpRelPrnt.getY()+10);
	} //End of draw

	
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {

		int px = pPtrRelPrnt.getX(); 
		int py = pPtrRelPrnt.getY(); 
		double xLoc = pCmpRelPrnt.getX()+ this.location.getX();
		double yLoc = pCmpRelPrnt.getY()+ this.location.getY();
		System.out.println("px and py: (" + px + ", " + py + ") - xLoc and yLoc: (" + xLoc + ", " + yLoc + ")" );
		if ( (px >= xLoc) && (px <= xLoc+this.size) && (py >= yLoc) && (py <= yLoc+this.size) )
		{
			System.out.println("Energy Station is selected.");
			return true; 
		}
		else
			return false;
	} //End of contains
	
	@Override
	public boolean collidesWith(ICollider other) {
		boolean result = false;
		
		double thisCenterX = this.getLocation().getX();
		double thisCenterY = this.getLocation().getY();
		
		double otherCenterX = ((GameObject)other).getLocation().getX();
		double otherCenterY = ((GameObject)other).getLocation().getY();
		
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		
		double distBetweenCentersSqr = (dx * dx + dy * dy);
		
		int thisRadius= this.getSize() / 2;
		int otherRadius= ((GameObject)other).getSize() / 2;
		
		int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) { result = true ; }
		
		return result;
	} //End of collidesWith

	@Override
	public void handleCollision(ICollider other) {
		if (other instanceof NonPlayerCyborg || other instanceof Base || other instanceof Drone || other instanceof PlayerCyborg)
		{
			setCollisionFlag();
			other.setCollisionFlag();
		}
	} //End of handleCollision

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

	@Override
	public void setLocation(Point pointerLoc) {
		// TODO Auto-generated method stub
		
	}

} //End of EnergyStation
