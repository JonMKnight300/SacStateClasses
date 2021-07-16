package com.mycompany.cmd;

import com.codename1.ui.CheckBox; 
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class SoundCommand extends Command {
	
	private GameWorld gw;
	
	public SoundCommand(GameWorld gw, CheckBox soundCB)
	{
		super("Sound ON/OFF");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		
		gw.setSoundOn();
		System.out.println("Sound is now set to " + gw.getSoundOn());
	}
}