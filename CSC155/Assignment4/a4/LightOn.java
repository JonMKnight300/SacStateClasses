package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


public class LightOn extends AbstractAction{
	
	private Starter s;
	
	public LightOn (Starter s) {
		super("Turn Light on or Off");
		this.s = s;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		s.changeLight();
	}
}
