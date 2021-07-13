package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class PitchCamUp extends AbstractAction{

	private static PitchCamUp instance = new PitchCamUp();
	private Camera3 camera;
	
	public PitchCamUp () {
		super("PitchUp");
	}
	
	public static PitchCamUp getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 cam) {
		this.camera = cam;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.pitchUp();
	}

}