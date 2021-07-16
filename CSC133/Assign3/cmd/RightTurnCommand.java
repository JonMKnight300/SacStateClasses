package com.mycompany.cmd;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class RightTurnCommand extends Command {

	private GameWorld gw;
	
	public RightTurnCommand(GameWorld gw)
	{
		super("Turn Right (d)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		 char ri = 'r';
		 gw.moveCyborg(ri);
		System.out.println("Turning Right...");
	}
	
}