package a4;


import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera {
	private float xLoc;
	private float yLoc;
	private float zLoc;
	float pitch = 0;
	float pan = 0;
	private Matrix4f viewMat;
	
	
	public Camera(float x, float y, float z) {
		this.xLoc = x;
		this.yLoc = y;
		this.zLoc = z;
		
		viewMat = new Matrix4f();
		computeView();
	}
	
	public void computeView() {
		
	//My original camera implementation had some issues, so I reduced it to a Starting View Setter for the scene. 
		
		Vector3f newLoc = new Vector3f(xLoc, yLoc, zLoc);
		float cosPitch = (float)Math.cos(Math.toRadians(pitch));
		float sinPitch = (float)Math.sin(Math.toRadians(pitch));
		float cosYaw = (float)Math.cos(Math.toRadians(pan));
		float sinYaw = (float)Math.sin(Math.toRadians(pan));
		
		Vector3f xAxis = new Vector3f(cosYaw, 0, -sinYaw);
		Vector3f yAxis = new Vector3f(sinYaw * sinPitch, cosPitch, cosYaw * sinPitch);
		Vector3f zAxis = new Vector3f(sinYaw * cosPitch, -sinPitch, cosPitch * cosYaw);
		float[] matArray = new float[] {
				xAxis.x(), yAxis.x(), zAxis.x(), 0,
				xAxis.y(), yAxis.y(), zAxis.y(), 0, 
				xAxis.z(), yAxis.z(), zAxis.z(), 0, 
				-(xAxis.dot(newLoc)), -(yAxis.dot(newLoc)), -(zAxis.dot(newLoc)), 1};
		viewMat.set(matArray);

	}
	
	public void setXYZ (float x, float y, float z)
	{
		this.xLoc = x; 
		this.yLoc = y;
		this.zLoc = z;
		computeView();
	}
	
	public float getX() {
		return (float) xLoc;
	}
	
	public float getY() {
		return (float) yLoc;
	}
	
	public float getZ() {
		return (float) zLoc;
	}
	

	

}