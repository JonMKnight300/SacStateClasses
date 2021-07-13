package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class camControl2 extends AbstractAction{

	private static camControl2 instance = new camControl2();
	private Camera3 camera;
	private Camera3 camera2;
	
	public camControl2 () {
		super("Change Camera to 2");
	}
	
	public static camControl2 getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 c, Camera3 d) {
		this.camera = c;
		this.camera2 = d;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.changeCamera(2);
		camera2.changeCamera(2);
	}

}