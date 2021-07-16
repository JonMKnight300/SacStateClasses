package com.mycompany.cmd;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCommand extends Command 
{
	/**
	 * Creates a command for side menu for info on program.
	 */
	public AboutCommand() 
	{
		super("About");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String info = "Jon M. Knight\nCyborg Race\nCSC 133\nProfessor V. Scott Gordon";
		Dialog.show("About", info, "Ok", null);
	}
}
