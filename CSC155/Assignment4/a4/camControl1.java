package a4;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

@SuppressWarnings("serial")
public class camControl1 extends AbstractAction{

	private static camControl1 instance = new camControl1();
	private Camera3 camera;
	private Camera3 camera2;
	
	public camControl1 () {
		super("Change Camera to 1");
	}
	
	public static camControl1 getInstance() {
		return instance;
	}
	
	public void setCamera(Camera3 c, Camera3 d) {
		this.camera = c;
		this.camera2 = d;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		camera.changeCamera(1);
		camera2.changeCamera(1);
	}

}