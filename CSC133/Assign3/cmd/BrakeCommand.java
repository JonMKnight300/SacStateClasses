package com.mycompany.cmd;

import com.codename1.ui.Command; 
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class BrakeCommand extends Command {

	private GameWorld gw;
	
	public BrakeCommand(GameWorld gw)
	{
		super("Brake (s)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.brakeForCyborg();
		System.out.println("Braking...");
	}
	
}
