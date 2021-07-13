package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class DisplayTheAxis extends AbstractAction{
	
	private Starter s;
	
	public DisplayTheAxis (Starter s) {
		super("Display the Axis");
		this.s = s;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		s.displayAxis();
	}
}
