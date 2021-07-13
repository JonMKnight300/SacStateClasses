package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


@SuppressWarnings("serial")
public class MoveCamUp extends AbstractAction{

	private static MoveCamUp instance = new MoveCamUp();
	private Camera3 camera;
	
	public MoveCamUp () {
		super("Move Camera Up");
	}
	
	public static MoveCamUp getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveUp();
	}

}