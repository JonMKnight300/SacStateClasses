package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil; 
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.plaf.Border;

public class aButtonObject extends Button{
	
	public aButtonObject(Command cmd)
	{
		this.getAllStyles().setBgTransparency(150);
		this.getAllStyles().setBorder(Border.createBevelRaised());
		this.getAllStyles().setBorder(Border.createBevelLowered());
		this.getAllStyles().setBorder(Border.createDoubleBorder(2,ColorUtil.BLACK));
		this.getAllStyles().setMargin(TOP,1);
		this.getAllStyles().setMargin(BOTTOM,1);
		this.getUnselectedStyle().setBgColor(ColorUtil.rgb(158, 219, 174));
		this.getUnselectedStyle().setFgColor(ColorUtil.BLUE);
		this.getPressedStyle().setBgTransparency(125);
		this.getPressedStyle().setBgColor(ColorUtil.rgb(158, 219, 174));
		this.getPressedStyle().setFgColor(ColorUtil.BLUE);
		this.getDisabledStyle().setBgTransparency(255);
		this.getDisabledStyle().setBgColor(ColorUtil.BLACK);
		this.getDisabledStyle().setFgColor(ColorUtil.BLUE);
		this.getDisabledStyle().setStrikeThru(true);
		this.setFocusable(false);
		this.setCommand(cmd);
	} //End of aButtonObject(Command cmd)
	
} //End of aButtonObject extends Button{
