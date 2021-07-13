package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class MoveCamDown extends AbstractAction{

	private static MoveCamDown instance = new MoveCamDown();
	private Camera3 camera;
	
	public MoveCamDown () {
		super("Move Camera Down");
	}
	
	public static MoveCamDown getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveDown();
	}

}