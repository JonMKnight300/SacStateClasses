package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class MoveCamLeft extends AbstractAction{

	private static MoveCamLeft instance = new MoveCamLeft();
	private Camera3 camera;
	
	public MoveCamLeft () {
		super("Move Camera Left");
	}
	
	public static MoveCamLeft getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.moveLeft();
	}

}