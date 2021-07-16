package com.mycompany.a3;

import com.codename1.util.MathUtil;
import com.mycompany.Interfaces.IIterator;
import com.mycompany.Interfaces.IStrategy;
import com.mycompany.a3.GameCollection2;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.NonPlayerCyborg;
import com.mycompany.a3.PlayerCyborg;

//This Strategy makes the NPC attempt to chase the player

public class Strategy4 implements IStrategy {
	private NonPlayerCyborg npc;
	private GameCollection2 gameObjects;
	
	public Strategy4 (GameCollection2 gameObjects2) {
	this.gameObjects = gameObjects2;
	} //End of Constructor

	public void apply(GameObject inputCyborg) {
		npc = (NonPlayerCyborg) inputCyborg;
		
		double x1 = npc.getLocation().getX();
		double x2 = findPlayerCyborg().getLocation().getX();
		double y1 = npc.getLocation().getY();
		double y2 = findPlayerCyborg().getLocation().getY();
				
		double a = x2 - x1; 
		double b = y2 - y1;
				
	
		double headingmod = (.90 - MathUtil.atan2(b, a))*100;
		int heading = (int) headingmod;
		npc.setHeading(heading);
		
		if (npc.getSpeed() > 20)
		{
		npc.setRandSpeed();
		} //End of if
		
		if (npc.getSpeed() < 6)
		{
			int j = npc.getSpeed() + 3;
			npc.setExactSpeed(j);
		} //End of if
	
	} //End of apply(GameObject inputCyborg)

	public PlayerCyborg findPlayerCyborg() {
		IIterator i = gameObjects.getIterator();
		while (i.hasNext()) {
			GameObject obj = i.getNext();
			if (obj instanceof PlayerCyborg) {
					return (PlayerCyborg) obj;
			} //End of if
		} //End of while
		return null;
	} //End of PlayerCyborg findPlayerCyborg()

} //End of Strategy4

