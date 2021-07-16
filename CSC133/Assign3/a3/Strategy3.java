package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.util.MathUtil;
import com.mycompany.Interfaces.IIterator;
import com.mycompany.Interfaces.IStrategy;
import com.mycompany.a3.Base;
import com.mycompany.a3.GameCollection2;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.MoveableGameObject;
import com.mycompany.a3.NonPlayerCyborg;

//Strategy aiming to run the bases. 

public class Strategy3 extends MoveableGameObject implements IStrategy {
	private NonPlayerCyborg npc;
	private GameCollection2 gameObjects;
	
	public Strategy3 (GameCollection2 gameObjects2) {
	this.gameObjects = gameObjects2;
	} //End of Constructor
	
	public void apply(GameObject inputCyborg) {
		npc = (NonPlayerCyborg) inputCyborg;

		int currentBase = npc.getLastBaseReached();
		Base nextBase = getBase(currentBase + 1);
		
		double x1 = npc.getLocation().getX();
		double x2 = nextBase.getLocation().getX();
		double y1 = npc.getLocation().getY();
		double y2 = nextBase.getLocation().getY();
		
	//	System.out.println("Npc: (" + x1 + ", "+ y1 + ") - Next Base: (" + x2 + ", " + y2 + ")");
		
		double a = x2 - x1;
		double b = y2 - y1;
		
		//System.out.println("a: " + a + " - b: " + b);
		
		//npc.setRandSpeed();
		npc.setExactSpeed(3);
		double headingmod = (.90 - MathUtil.atan2(b, a))*100;
		
		int heading = (int) headingmod;
		npc.setHeading(heading);
		
	//	System.out.println("Npc Heading: " + npc.getHeading());
	//	System.out.println("MathUtil: " + MathUtil.atan2(b, a));
	} //End of apply(GameObject inputCyborg)
	
	public Base getBase(int currentBase) {
		IIterator i = gameObjects.getIterator();
		while (i.hasNext()) {
			GameObject obj = i.getNext();
			if (obj instanceof Base) {
				int baseNum = ((Base) obj).getSegment();
			
				if (baseNum == currentBase) {
					return (Base) obj;
				} //End of if
			} //End of if
		} //End of while
		return null;
	} //End of Base getBase(int currentBase)

	@Override
	public String printCurrentStats() {
		// TODO Auto-generated method stub
		return null;
	}

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
		// TODO Auto-generated method stub
		
	}


} //End of Strategy3

