package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.Interfaces.ICollider;
import com.mycompany.Interfaces.IStrategy;

public class NonPlayerCyborg extends Cyborg implements IStrategy, ICollider  {
	
	private int stratSelect;

	private IStrategy currentStrategy;
	public boolean collisionFlag;
	
	public NonPlayerCyborg() {
		setMaximumSpeed(25);
	//	setEnergyLevel(100);
	//	setEnergyConsumptionRate(2);
	//	setDamageLevel(0);
		setLastBaseReached(1); 
		setNumberOfLives(3);
	//	setSpeedDamage();
		setStratSelect(0);
		super.setFixed(false);
		super.setMovable(true);
		super.setSteerable(true);   
		super.setSize(45);
		super.setAtkValue(3);
		collisionFlag = false;
	
	} //End of Constructor
	
	public void setStrategy(IStrategy s) {
		currentStrategy = s;
	}
	
	public void invokeStrategy(IStrategy s, GameObject gameObject) {
		s.apply(gameObject);
	}
	
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
				r = 235;
				g = 64;
				b = 52;
				setSpecificColor(r, g, b);
				
			//	System.out.println("Object color changed!");
				break;
			case '2':
				r = 163;
				g = 54;
				b = 46;
				setSpecificColor(r, g, b);
				
			//	System.out.println("Object color changed!");
				break;

			} //End of switch
		} //End of setColor(char state)

	public void setCyborgDamageLevel(int th) {
		setDamageLevel(getDamageLevel() + th);
		setSpeedDamage();

		if (getDamageLevel() < 15)
		{
			setColor('1');
		}
		else if (getDamageLevel() > 14)
		{
			setColor('2');
		}

	} //End of setCyborgDamageLevel(int th)
	
	public String toString() {
		String myDesc = "";
		myDesc = "Current Strategy: " + getStratSelect() + " (" + currentStrategy + ")";
		 return myDesc;
	}
	
	
	@Override
	public String printCurrentStats() { 
		//Overrides CurrentStats with Cyborg specific stats to print
		int max = getCyborgMaximumSpeed() - getCyborgDamageLevel();
		String showing = "NPC Cyborg: " + printTheLocation() + " : " + printTheColor() + " : Size = " + super.getSize() + "" + currentSpeedandDir() + " : Max Speed = " + max + 
				"\n " + toString();
		System.out.println(showing);

		return showing;
		
		//Dialog.show("About", info, "Ok", null);
	} //End of printCurrentStats() 

	public int getStratSelect() {
		return stratSelect;
	}

	public void setStratSelect(int stratSelect) {
		this.stratSelect = stratSelect;
	}
	
	
	@Override
	public void cyborgSlowDown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cyborgSpeedUp() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetCyborg() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void apply(GameObject gameObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCyborgEnergyConsumptionRate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCyborgEnergyLevel(int th) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		g.setColor(this.getColor());
		int xLoc = (int)this.getLocation().getX() + pCmpRelPrnt.getX();
		int yLoc = (int)this.getLocation().getY() + pCmpRelPrnt.getY();
		
		int r = this.getSize();
		
	//	int[] xPoints = { xLoc, (xLoc - 35), (xLoc + 35), xLoc };
		
	//	int[] yPoints = { (yLoc + 35), (yLoc - 30), (yLoc - 30), (yLoc + 30) };
		
	//	int nPoints = 4;
		
		g.drawRoundRect(xLoc, yLoc, r, r, 0, 360);
	//	g.fillRoundRect(xLoc, yLoc, r, r, 0, 360);
		
	//	g.drawPolygon(xPoints, yPoints, nPoints);
	//	g.fillPolygon(xPoints, yPoints, nPoints);
		
	}

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
	}

	@Override
	public void handleCollision(ICollider other) {
		if (other instanceof PlayerCyborg || other instanceof Base || other instanceof Drone || other instanceof EnergyStation)
		{
			setCollisionFlag();
			other.setCollisionFlag();
		}
		
	}

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
	public void turn(char c) {

	} //End of turnCyborg(char c)
	



} //End of NonPlayerCyborg
