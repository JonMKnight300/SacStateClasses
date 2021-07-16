package com.mycompany.a3;

import com.mycompany.Interfaces.ISteerable;

public abstract class Cyborg extends MoveableGameObject implements ISteerable {
	private int maximumSpeed;
	private int energyLevel; 
	private int energyConsumptionRate;
	private int damageLevel;
	private int lastBaseReached; 
	private int numberOfLives;
	private int speedDamage;
	private boolean oneMoreBase;
	
	public Cyborg()
	{
		oneMoreBase = false;
	} //End of Constructor
	
	
	////////////////////
	//ABSTRACT METHODS//
	////////////////////
	
	public abstract void setCyborgDamageLevel(int th);
	
	public abstract void setCyborgEnergyConsumptionRate(); 
	
	public abstract void setCyborgEnergyLevel(int th); 
	
	public abstract void cyborgSlowDown(); 
	
	public abstract void cyborgSpeedUp(); 
	
	public abstract void resetCyborg();
	
	
	
	///////////////
	//GET METHODS//
	///////////////
	
	public boolean getoneMoreBase() {
		return oneMoreBase;
	} //End of getoneMoreBase()
	
	public int getCyborgDamageLevel() {
		return getDamageLevel();
	} //End of getCyborgDamageLevel() 
	
	public int getCyborgEnergyConsumptionRate() {
	//Important for how much energy is
	//being consumed for each tick
		return energyConsumptionRate;
	} //End of getCyborgEnergyConsumptionRate()
	
	public int getCyborgEnergyLevel() {
		return energyLevel;
	} //End of getCyborgEnergyLevel()
	
	public int getCyborgMaximumSpeed() {
		return maximumSpeed;
	} //End of getCyborgMaximumSpeed() 
	
	public int getNumberOfLives() {
	//Used for printing stat purposes only
		return numberOfLives;
	} //End of getNumberOfLives()
	
	public int getSpeedDamage() {
		return speedDamage;
	} //End of getSpeedDamage()
	
	public int getDamageLevel() {
		return damageLevel;
	} //End of getDamageLevel()
	
	public int getLastBaseReached() {
		return lastBaseReached;
	} //End of getLastBaseReached
	
	
	
	///////////////
	//SET METHODS//
	///////////////
	
	public void setNumberOfLives(int th) {
	//Used for printing stat purposes only
		numberOfLives = th;
	} //End of setNumberOfLives(int th)
	
	public void setoneMoreBase(boolean val) {
		oneMoreBase = val;
	} //End of setoneMoreBase(boolean val)
	
	public void setSpeedDamage() {
	//Sets the speedDamage value
	//Important for limiting movement 
	//Seeing if Cyborg life needs to end
		speedDamage = getCyborgMaximumSpeed() - getDamageLevel();
	} //End of setSpeedDamage()
	
	public void setDamageLevel(int damageLevel) {
		this.damageLevel = damageLevel;
	} //End of setDamageLevel(int damageLevel)

	public void setLastBaseReached(int lastBaseReached) {
		this.lastBaseReached = lastBaseReached;
	} //End of setLastBaseReached(int lastBaseReached)

	public void setEnergyConsumptionRate(int energyConsumptionRate) {
		this.energyConsumptionRate = energyConsumptionRate;
	} //End of setEnergyConsumptionRate(int energyConsumptionRate)

	public void setEnergyLevel(int energyLevel) {
		this.energyLevel = energyLevel;
	} //End of setEnergyLevel(int energyLevel)

	public void setMaximumSpeed(int maximumSpeed) {
		this.maximumSpeed = maximumSpeed;
	} //End of setMaximumSpeed(int maximumSpeed) 

} //End of Cyborg
