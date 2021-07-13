package a4;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera3 {
private Vector3f U;
private Vector3f V;
private Vector3f N;
private Vector3f cameraFixedAt;
private Vector3f cameraGoingTo;
private Matrix4f lookAtMatrix;
private Matrix4f viewMatrix;

private int cameraControl;

public Camera3() {
	cameraFixedAt = new Vector3f(0.0f, 0.0f, 10.0f);
	cameraGoingTo = new Vector3f(0.0f, 0.0f, 0.0f);
	viewMatrix = new Matrix4f();
	
	N = new Vector3f(0.0f, 0.0f, 0.0f);
	cameraFixedAt.sub(cameraGoingTo, N);
	N.normalize();
	
	U = new Vector3f(0.0f, 0.0f, 0.0f);
	new Vector3f(0.0f, 1.0f, 0.0f).cross(N, U);
	U.normalize();
	
	V = new Vector3f(0.0f, 0.0f, 0.0f);
	U.cross(N, V);
	V.normalize();
	lookAtMatrix = new Matrix4f().identity();
	lookAtMatrix.lookAt(cameraFixedAt, cameraGoingTo, new Vector3f(0f, 1f, 0f));
}

	public Matrix4f getViewMatrix() {
		viewMatrix.identity();
		lookAtMatrix.set(
				U.x, -V.x, N.x, 0,
				U.y, -V.y, N.y, 0,
				U.z, -V.z, N.z, 0,
				0, 0, 0, 1);
				
				Matrix4f translationMatrix = new Matrix4f().identity();
				translationMatrix.translation(-cameraFixedAt.x, -cameraFixedAt.y, -cameraFixedAt.z);
		
				viewMatrix.mul(lookAtMatrix);
				viewMatrix.mul(translationMatrix);
		
		return viewMatrix;
}
	
	public void moveForward( ) {
		if (cameraControl == 1)
		{
		cameraFixedAt.sub(N);
		closedBox();
		}
		else if (cameraControl == 3)
		{
		cameraFixedAt.sub(N);
		}

	}
	
	public void moveBackward( ) {
		if (cameraControl == 1)
		{
		cameraFixedAt.add(N);
		closedBox();
		}
		else if (cameraControl == 3)
		{
		cameraFixedAt.add(N);
		}

	}
	
	public void moveLeft() {
		if (cameraControl == 1)
		{
		cameraFixedAt.sub(U);
		closedBox();
		}
		else if (cameraControl == 3)
		{
		cameraFixedAt.sub(U);	
		}
	}
	
	public void moveRight() {
		if (cameraControl == 1)
		{
		cameraFixedAt.add(U);
		closedBox();
		}
		else if (cameraControl == 3)
		{
		cameraFixedAt.add(U);
		}
	}
	
	public void moveUp() {
		if (cameraControl == 1)
		{
		cameraFixedAt.sub(V);
		closedBox();
		}
		else if (cameraControl == 3)
		{
		cameraFixedAt.sub(V);			
		}
	}
	
	public void moveDown() {
		if (cameraControl == 1)
		{
		cameraFixedAt.add(V);
		closedBox();
		}
		else if (cameraControl == 3)
		{
		cameraFixedAt.add(V);	
		}
	}
	
	public void changeCamera(int change)
	{
	cameraControl = change;	
	}
	
	public int getCamera() 
	{
	return cameraControl;	
	}
	
	public void panLeft() {
		N.rotateAxis((float) Math.toRadians(1), 0, 1, 0);
		U.rotateAxis((float) Math.toRadians(1), 0, 1, 0);
		V.rotateAxis((float) Math.toRadians(1), 0, 1, 0);

	}
	
	public void panRight() {
		N.rotateAxis((float) Math.toRadians(-1), 0, 1, 0);
		U.rotateAxis((float) Math.toRadians(-1), 0, 1, 0);
		V.rotateAxis((float) Math.toRadians(-1), 0, 1, 0);

	}
	
	public void pitchUp() {
		N.rotateAxis((float) Math.toRadians(1), U.x, U.y, U.z);
		V.rotateAxis((float) Math.toRadians(1), U.x, U.y, U.z);
	}
	
	public void pitchDown() {
		N.rotateAxis((float) Math.toRadians(-1), U.x, U.y, U.z);
		V.rotateAxis((float) Math.toRadians(-1), U.x, U.y, U.z);
	}
	
	public void closedBox() {
		if (cameraFixedAt.x < -6)
			cameraFixedAt.x = -5.5f;
		if (cameraFixedAt.x > 6)
			cameraFixedAt.x = 5.5f;
		if (cameraFixedAt.y < -6)
			cameraFixedAt.y = -5.5f;
		if (cameraFixedAt.y > 3)
			cameraFixedAt.y = 2.9f;
		if (cameraFixedAt.z < -4)
			cameraFixedAt.z = -3.9f;
		if (cameraFixedAt.z > 20)
			cameraFixedAt.z = 19.6f;
		Matrix4f translationMatrix = new Matrix4f().identity();
		translationMatrix.translation(-cameraFixedAt.x, -cameraFixedAt.y, -cameraFixedAt.z);
	}
	
	public float getCameraY()
	{
		return cameraFixedAt.y;
	}
	

}
