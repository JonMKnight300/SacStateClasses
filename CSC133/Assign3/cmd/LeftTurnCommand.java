package com.mycompany.cmd;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class LeftTurnCommand extends Command {

	private GameWorld gw;
	
	public LeftTurnCommand(GameWorld gw)
	{
		super("Turn Left (a)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		 char le = 'l';
		 gw.moveCyborg(le);
		System.out.println("Turning Left...");
	}
	
}