package com.mycompany.cmd;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class TickCommand extends Command {

	private GameWorld gw;
	
	public TickCommand(GameWorld gw)
	{
		super("Tick World (t)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		 gw.tickGame();
		System.out.println("Ticking Game World...");
	}
	
}