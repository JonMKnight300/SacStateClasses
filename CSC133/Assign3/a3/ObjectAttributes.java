package com.mycompany.a3;

public abstract class ObjectAttributes extends GameObject {

	private boolean fixed;
	private boolean movable;
	private boolean steerable;
	private int idPartA; //what type of object
	private int idPartB; //What number the object is of its type
	private int atkValue;
	
	
	
	///////////////
	//GET METHODS//
	///////////////
	
	public int getAtkValue() {
	//gets an Object's Attack value
		return atkValue;
	} //End of getAtkValue()
	
	public boolean getFixed() {
		return fixed;
	} //End of getFixed()
	
	public int getIDPartA() {
		return idPartA;
	} //End of getIDPartA ()
	
	public int getIDPartB() {
		return idPartB;
	} //End of getIDPartB ()
	
	public boolean getMovable() {
		return movable;
	} //End of getMovable()

	
	public boolean getSteerable() {
		return steerable;
	} //End of getSteerable()
	
	
	
	///////////////
	//SET METHODS//
	///////////////
	
	public void setAtkValue(int th) {
		//Sets an Object's Attack value
		atkValue = atkValue + th;
	} //End of setAtkValue(int th)
	
	public void setMovable(boolean th) {
		movable = th;
	} //End of setMovable()
	

	public void setFixed(boolean th) {
		fixed = th;
	} //End of setFixed
	
	public void setIDPartA(int th) {
		idPartA = th;
	} //End of setIDPartA 
	
	public void setIDPartB(int th) {
		idPartB = th;
	} //End of setIDPartB
	
	public void setSteerable(boolean th) {
		steerable = th;
	} //End of setSteerable
	
} //End of ObjectAttributes
