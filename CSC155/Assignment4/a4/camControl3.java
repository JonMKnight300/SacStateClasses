package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class camControl3 extends AbstractAction{

	private static camControl3 instance = new camControl3();
	private Camera3 camera;
	private Camera3 camera2;
	
	public camControl3 () {
		super("Change Camera to 2");
	}
	
	public static camControl3 getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 c, Camera3 d) {
		this.camera = c;
		this.camera2 = d;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.changeCamera(3);
		camera2.changeCamera(3);
	}

}