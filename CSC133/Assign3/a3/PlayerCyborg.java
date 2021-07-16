package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.Interfaces.ICollider;
import com.mycompany.Interfaces.IStrategy;
import com.codename1.ui.geom.Dimension;

public class PlayerCyborg extends Cyborg implements ICollider {
	
	public boolean collisionFlag;

	public PlayerCyborg() {
		setMaximumSpeed(40);
		setEnergyLevel(450);
		setEnergyConsumptionRate(1);
		setDamageLevel(0);
		setLastBaseReached(1); 
		setNumberOfLives(3);
		setSpeedDamage();
		super.setFixed(false);
		super.setMovable(true);
		super.setSteerable(true);   
		super.setSize(45);
		super.setAtkValue(18);
		collisionFlag = false;

	} //End of Constructor
	
	
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
				r = 0;
				g = 0;
				b = 153;
				setSpecificColor(r, g, b);
				
		//		System.out.println("1 Object color changed!");
				break;
			case '2':
				r = 51;
				g = 51;
				b = 255;
				setSpecificColor(r, g, b);
				
			//	System.out.println("2 Object color changed!");
				break;
			case '3':
				r = 153;
				g = 153;
				b = 255;
				setSpecificColor(r, g, b);
				
			//	System.out.println("3 Object color changed!");
				break;
			case '4':
				r = 204;
				g = 204;
				b = 255;
				setSpecificColor(r, g, b);
				
			//	System.out.println("4 Object color changed!");
				break;
			} //End of switch
		} //End of setColor(char state)
	
	@Override
	public String printCurrentStats() { 
		//Overrides CurrentStats with Cyborg specific stats to print
		int max = getCyborgMaximumSpeed() - getCyborgDamageLevel();
		String showing = "Player Cyborg: " + printTheLocation() + " : " + printTheColor() + " : Size = " + super.getSize() + "" + currentSpeedandDir() + " : Max Speed = " + max + 
				" : Energy Level = " + getCyborgEnergyLevel() + " : Damage Level = " + getCyborgDamageLevel() + " : Lives = " + getNumberOfLives() + "";
		System.out.println(showing);

		
		return showing;
		
		//Dialog.show("About", info, "Ok", null);
	} //End of printCurrentStats() 
	
	
	
	///////////////////////
	//MAIN CYBORG METHODS//
	//////////////////////

	public void setCyborgEnergyConsumptionRate() {
	//Important for how much energy is
	//being consumed for each tick
		setEnergyLevel(getCyborgEnergyLevel() - getCyborgEnergyConsumptionRate());
	} //End of setCyborgEnergyConsumptionRate() 
	
	public void setCyborgEnergyLevel(int th) {
		setEnergyLevel(getCyborgEnergyLevel() + th);
	} //End of setCyborgEnergyLevel(int th)
	
	public void cyborgSlowDown() {
		// Let's slow this cyborg doooown!
		//Nothing moves until tick is clicked

		int speed = getSpeed() - 2;
		setExactSpeed(speed);
		
		if (getSpeed() <= 0)
		{ //Prevents negative speed
			System.out.println("Your Cyborg slows to a dead stop!");
			setExactSpeed(0);
		} //End of if (getSpeed() <= 0)
		else
		{ //If your cyborg does slow down
		System.out.println("Your Cyborg slows on down!");
		} //End of else
	} //End of cyborgSlowDown() 
	
	public void cyborgSpeedUp() {
		// Your Cyborg will add to speed or not
		//based on this method, though nothing
		//moves until tick is clicked
		
		int speed = getSpeed() + 1;
		setExactSpeed(speed);
		
		if (getSpeed() > getSpeedDamage())
		{ //Resets getSpeed if too high
			setExactSpeed(getSpeedDamage());
			System.out.println("Your Cyborg is at its maximum speed!");
		} //End of if (getSpeed() > getSpeedDamage())
		
		if (getCyborgEnergyLevel() == 0)
		{ //Prevents a Cyborg with no energy from moving
			setExactSpeed(0);
			System.out.println("Your Cyborg has no energy and is stuck in place!");
		} //End of if (getCyborgEnergyLevel() == 0)
		
		if (getSpeed() < getSpeedDamage() && getCyborgEnergyLevel() != 0)
		{ //Prints only if Cyborg speed increases
			System.out.println("Your Cyborg ramps up!");
		} //End of if (getSpeed() < getSpeedDamage()
		
	} //End of cyborgSpeedUp()
	
	@Override
	public void Move(double mapWid, double mapHght) { 
		//Overrides Move() from Game Object to add 
		//additional conditions specific to Cyborg
		if (getCyborgEnergyLevel() > 1)
		{
			super.Move(mapWid, mapHght);
		} //End of if (energyLevel > 1)
		else if (getCyborgEnergyLevel() <= 1)
		{
			setEnergyLevel(0);
		} //End of else if
	} //End of Move()
	
	public void resetCyborg() {
	//If you can no longer move or you take 
	//too much damage, you reset back to beginning.
		setDamageLevel(0); 
		setEnergyLevel(450);
		setLastBaseReached(1);
		setColor('1');
		super.setLocation(70, 70);
		setoneMoreBase(false);
		setSpeedDamage();
		System.out.println("You are restored to your almost former glory! \nDown 1 life, back to the beginning. \nTry harder. !");
	} //End of resetCyborg() 
	
	@Override
	public void turn(char c) {
		//Allows Cyborg to turn left or right
		switch(c)
		{
		case 'l':
			//Turns left
			setHeading(getHeading()-20);
		//	System.out.println("Cyborg moves left");
			break;
		case 'r':
			//Turns right
			setHeading(getHeading()+20);
		//	System.out.println("Cyborg moves right");
			break;
		} //End of switch
	} //End of turnCyborg(char c)
	

	public void setCyborgDamageLevel(int th) {
		setDamageLevel(getDamageLevel() + th);
		setSpeedDamage();

		if (getDamageLevel() < 11)
		{
			setColor('1');
		}
		else if (getDamageLevel() > 10 && getDamageLevel() < 21)
		{
			setColor('2');
		}
		else if (getDamageLevel() > 20 && getDamageLevel() < 31)
		{
			setColor('3');
		}
		else if (getDamageLevel() > 30)
		{
			setColor('4');
		}
	} //End of setCyborgDamageLevel(int th)


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
		
		int r = this.getSize();
		
	//	int[] xPoints = { xLoc, (xLoc - 35), (xLoc + 35), xLoc };
		
	//	int[] yPoints = { (yLoc + 35), (yLoc - 30), (yLoc - 30), (yLoc + 30) };
		
	//	int nPoints = 4;
		
		g.drawRoundRect(xLoc, yLoc, r, r, 0, 360);
		g.fillRoundRect(xLoc, yLoc, r, r, 0, 360);
		
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
		if (other instanceof NonPlayerCyborg || other instanceof Base || other instanceof Drone || other instanceof EnergyStation)
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


}
