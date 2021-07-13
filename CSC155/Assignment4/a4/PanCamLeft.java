package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class PanCamLeft extends AbstractAction{

	private static PanCamLeft instance = new PanCamLeft();
	private Camera3 camera;
	
	public PanCamLeft () {
		super("Pan the Camera Left");
	}
	
	public static PanCamLeft getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.panLeft();
	}

}