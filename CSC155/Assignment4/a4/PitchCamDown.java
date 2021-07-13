package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class PitchCamDown extends AbstractAction{

	private static PitchCamDown instance = new PitchCamDown();
	private Camera3 camera;
	
	public PitchCamDown () {
		super("Pitch the Camera Down");
	}
	
	public static PitchCamDown getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 cam) {
		this.camera = cam;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.pitchDown();
	}

}