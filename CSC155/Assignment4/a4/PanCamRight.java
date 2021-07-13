package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class PanCamRight extends AbstractAction{

	private static PanCamRight instance = new PanCamRight();
	private Camera3 camera;
	
	public PanCamRight () {
		super("Pan the Camera Right");
	}
	
	public static PanCamRight getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 c) {
		this.camera = c;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.panRight();
	}

}