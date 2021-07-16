package com.mycompany.cmd;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCommand  extends Command 
{

	public HelpCommand() 
	{
		super("Help (h)");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String info = "Keyboard Commands\nMOVEMENT\nAccelerate (a) : Brake (b)\nLeft Turn (l) : Right Turn (r)\n\n"
				+ "GAME WORLD\nTick World (t) : Change NPC Strategy (s)\nHelp (h) : Exit Game (x)\n\n"
				+ "COLLISIONS\nBase (c) : Drone (g) : Energy Station (e) : NPC (n)";
		Dialog.show("Help", info, "Ok", null);
	}
}
