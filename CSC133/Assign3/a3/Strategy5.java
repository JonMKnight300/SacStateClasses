package com.mycompany.a3;

import com.codename1.util.MathUtil;
import java.util.*;

//This Strategy is an attempt for the NPC to run to the 3rd Base and run around it
import com.mycompany.Interfaces.IIterator;
import com.mycompany.Interfaces.IStrategy;
import com.mycompany.a3.Base;
import com.mycompany.a3.GameCollection2;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.NonPlayerCyborg;

public class Strategy5  implements IStrategy {
	private NonPlayerCyborg npc;
	private GameCollection2 gameObjects;
	private int timeTick;
	private int currentBase;
	
	public Strategy5 (GameCollection2 gameObjects2) {
	this.gameObjects = gameObjects2;
	timeTick = 0;
	currentBase = 5;
	} //End of Constructor

	public void apply(GameObject inputCyborg) {
		npc = (NonPlayerCyborg) inputCyborg;

		 
		Base nextBase = getBase(currentBase);
		
		double x1 = npc.getLocation().getX();
		double x2 = nextBase.getLocation().getX();
		double y1 = npc.getLocation().getY();
		double y2 = nextBase.getLocation().getY();
		
		double a = x2 - x1;
		double b = y2 - y1;
				
		double headingmod = (.90 - MathUtil.atan2(b, a))*100;
		int heading = (int) headingmod;
		npc.setHeading(heading);
		npc.setExactSpeed(npc.getCyborgMaximumSpeed());
		timeTick++;
		if (timeTick > 100)
		{
			currentBase--;
			timeTick = 0;
			if (currentBase == 1)
				currentBase = 5;
		}
		
		
		
	} //End of apply(GameObject inputCyborg) 
	
	public Base getBase(int currentBase) {
		IIterator i = gameObjects.getIterator();
		while (i.hasNext()) {
			GameObject o = i.getNext();
			if (o instanceof Base) {
				int baseNum = ((Base) o).getSegment();
			
				if (baseNum == currentBase) {
					return (Base) o;
				} //End of if
			} //End of if
		} //End of while
		return null;
	} //End of Base getBase(int currentBase)

} //End of Strategy5
