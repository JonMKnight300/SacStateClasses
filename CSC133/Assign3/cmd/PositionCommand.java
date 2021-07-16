package com.mycompany.cmd;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.MapView;

public class PositionCommand extends Command {
	MapView mv;
	
	public PositionCommand(String command, MapView mv) {
		super(command);
		this.mv = mv;
	}
	
	public void actionPerformed(ActionEvent e) {
		Dialog.show("Position", "You may move the object just selected", "Ok", "");
		
		mv.setMoveObject(true);

	}
}
