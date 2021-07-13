package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class MoveCamRight extends AbstractAction{

	private static MoveCamRight instance = new MoveCamRight();
	private Camera3 camera;
	
	public MoveCamRight () {
		super("Move Camera Right");
	}
	
	public static MoveCamRight getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveRight();
	}
}