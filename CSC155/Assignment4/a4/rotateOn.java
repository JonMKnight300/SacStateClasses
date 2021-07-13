package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class rotateOn extends AbstractAction{
	
	private Starter s;
	
	public rotateOn (Starter s) {
		super("Turn Rotate Light on or Off");
		this.s = s;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		s.rotateLight();
	}
}
