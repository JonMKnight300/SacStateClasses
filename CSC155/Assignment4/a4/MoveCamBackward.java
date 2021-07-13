package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class MoveCamBackward extends AbstractAction{

	private static MoveCamBackward instance = new MoveCamBackward();
	private Camera3 camera;
	
	public MoveCamBackward () {
		super("Move Camera Back");
	}
	
	public static MoveCamBackward getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 cam) {
		this.camera = cam;
		cam.moveBackward();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveBackward();
	}
}
