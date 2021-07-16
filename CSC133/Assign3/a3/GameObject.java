package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Point2D;
import com.mycompany.Interfaces.ICollider;
import com.mycompany.Interfaces.IDrawable;

public abstract class GameObject implements IDrawable {

	public Random rand = new Random();
	protected Point2D location;
	private int whatColor;
	protected int size;
	
	public GameObject() {
		double x = Math.round((700.0* rand.nextDouble() * 10.0)) / 10.0;
		double y = Math.round((700.0* rand.nextDouble() * 10.0)) / 10.0;
		
		//To prevent going off the edge of screen. 
		if( x > 700)
			x = 700;
		if(y > 700)
			y = 700;
		 
		location = new Point2D(x,y);
		setColor('1');
	} //End of Constructor
	
	
	
	public double getDeltaX() {
		return location.getX();
	} //End of getDeltaX
	
	public double getDeltaY() {
		return location.getY();
	} //End of getDeltaY() 
	
	public String printTheColor() {
		String str = " Color = [" + ColorUtil.red(whatColor) + "," +
				ColorUtil.green(whatColor) +"," +
				ColorUtil.blue(whatColor) + "]";
		
		return str;
	} //End of printTheColor()
	
	public String printTheLocation() {
		String str = "Location = (" + getDeltaX() + ", " + getDeltaY() + ")";
		
		return str;
	} //End of printTheLocation()
	
	public Point2D getLocation() {
		return location;
	} //End of getLocation()
	
	public void setLocation(double deltaX, double deltaY) {
		location.setX((Math.round(deltaX)*10.0)/10.0);
		location.setY((Math.round(deltaY)*10.0)/10.0);
	} //End of setLocation
	
	public void setLocation(Point2D newWhere) {
		location.setX((Math.round(newWhere.getX())*10.0)/10.0);
		location.setY((Math.round(newWhere.getY())*10.0)/10.0);
	} //End of setLocation
	
	public int getColor() {
		return this.whatColor;
	} //End of getColor()
	
	public void setColor(char state) {
	//This will set the color based on 
	//how much damage an object has.
	//This will be overridden depending 
	//on object
		int r, g, b;
		switch(state)
		{
		case '1': 
			r = 100;
			g = 100;
			b = 100;
			setSpecificColor(r, g, b);
			
		//	System.out.println("Object color changed!");
			break;
		case '2':
			r = 100;
			g = 100;
			b = 100;
			setSpecificColor(r, g, b);
			
		//	System.out.println("Object color changed!");
			break;
		case '3':
			r = 100;
			g = 100;
			b = 100;
			setSpecificColor(r, g, b);
			
		//	System.out.println("Object color changed!");
			break;
		} //End of switch
	} //End of setColor(int state)
	
	public void setSpecificColor(int R, int G, int B) {
		 this.whatColor = ColorUtil.rgb(R, G, B);
	} //End of setSpecificColor
	
	public abstract String printCurrentStats(); //This will print differently depending on object
	
	
	public int getSize() {
		return size;
	} //End of  getSize ()
	
	public void setRandSize() {
		size = rand.nextInt(80);
		if (size < 20)
		{
			size = 23;
		}
	} //End of setRandSize ()
	
	public void setSize(int th) {
		size = th;
	} //End of setSize



	public void handleCollision(ICollider other) {
		// TODO Auto-generated method stub
		
	}
	
	
	
} //End of GameObject


