package a4;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;


@SuppressWarnings("serial")
public class MoveCamForward extends AbstractAction {
	
	private static MoveCamForward instance = new MoveCamForward();
	private Camera3 camera;
	
	public MoveCamForward () {
		super("Move Camera Forward");
	}
	
	public static MoveCamForward getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 cam) {
		this.camera = cam;
		cam.moveForward();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveForward();
	}
}