package com.mycompany.cmd;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class ExitCommand extends Command {

	
	public ExitCommand() 
	{
		super("Exit Game (x)");
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (Dialog.show("Exit Game", "Do you want to exit the game?", "Yes", "No"))
		{
			System.exit(0);
		}
		
	}
	
	
}
