package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.geom.Point2D;
import com.mycompany.Interfaces.ISelectable;

public abstract class FixedObject extends ObjectAttributes implements ISelectable {
	private boolean isSelected;
	public FixedObject() {
		super();
	} //End of Constructor
	
	public void setSelected(boolean yesNo) { isSelected = yesNo; }
	
	public boolean isSelected() { return isSelected; }
	
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {	
		return false; 
		}
	
	public void draw(Graphics g, Point pCmpRelPrnt) {	}
	
	public void setLocation (Point2D location) { 
		this.location =location;
		}
}