package com.mycompany.cmd;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class StrategyCommand extends Command {
	
	private GameWorld gw;

	public StrategyCommand(GameWorld gw) {
		super("Strategy Change (e)");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		gw.changeNPCStrategy();
	}
	
}
