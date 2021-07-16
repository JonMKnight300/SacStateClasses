package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.Interfaces.ICollider;

public class Base extends FixedObject implements ICollider  {

	private int segment;
	public boolean collisionFlag;
	
	public Base () {
		super.setFixed(true);
		super.setMovable(false);
		super.setSteerable(false);
		super.setSize(40);
		setSegment();
		collisionFlag = false;
		setLocation(location);
		System.out.println("Base location: " + location);
	} //End of Constructor
	
	public void setSegment() {
		segment = super.getIDPartB();
	} //End of setSegment()
	
	public int getSegment() {
		return segment;
	} //End of getSegment()
	
	@Override
	public void setColor(char state) {
	// Orange: 255, 128, 0
		setSpecificColor(255, 128, 0);
	} //End of setColor(char state)
	
	@Override
	public String printCurrentStats() {
		//Overrides CurrentStats with Base specific stats to print
		String showing = "Base: " + printTheLocation() + " : " + printTheColor() + " : " + super.getSize() + " Segment = " + getSegment();
		
		System.out.println(showing);
		
		return showing;
	} //End of printCurrentStats()

	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {

		// Parent's location
		int px = pPtrRelPrnt.getX(); 
		int py = pPtrRelPrnt.getY();
		//Shape location related to parent's
		double xLoc = pCmpRelPrnt.getX()+ this.location.getX();
		double yLoc = pCmpRelPrnt.getY()+ this.location.getY();

		if ( (px >= xLoc) && (px <= xLoc+this.size) && (py >= yLoc) && (py <= yLoc+this.size) )
			return true; 
		else
			return false;
	} //End of contains(Point pPtrRelPrnt, Point pCmpRelPrnt)
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();
		
		int[] xPoints = { xLoc, (xLoc - getSize()), (xLoc + getSize()), xLoc };
		int[] yPoints = { (yLoc + getSize()), (yLoc - getSize()), (yLoc - getSize()), (yLoc + getSize()) };
		
		if (this.isSelected())
			g.drawPolygon(xPoints, yPoints, 3);
		else	
		{
			g.drawPolygon(xPoints, yPoints, 3);
			g.fillPolygon(xPoints, yPoints, 3);
		}
	
		
		g.setColor(ColorUtil.BLACK);
		g.drawString (Integer.toString(this.segment), 
				(int) this.getLocation().getX()+pCmpRelPrnt.getX()-10, 
				(int) this.getLocation().getY()+pCmpRelPrnt.getY()-25);
		
	} //End of draw(Graphics g, Point pCmpRelPrnt) 


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
		
		int thisRadius= this.getSize();
		int otherRadius= ((GameObject)other).getSize();
		
		int radiiSqr= (thisRadius * thisRadius + 6 * thisRadius * otherRadius + otherRadius * otherRadius);
		
		if (distBetweenCentersSqr <= radiiSqr) { result = true ; }
		
		return result;
	}

	@Override
	public void handleCollision(ICollider other) {
		if (other instanceof NonPlayerCyborg || other instanceof PlayerCyborg || other instanceof Drone || other instanceof EnergyStation)
		{
			setCollisionFlag();
			other.setCollisionFlag();
		}
		
	}

	@Override
	public void setCollisionFlag() {
		collisionFlag = true;
		System.out.println("Base Collision Detected!");
		
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
	
} //End of Base