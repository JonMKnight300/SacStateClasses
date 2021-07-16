package com.mycompany.Interfaces;

import com.mycompany.a3.GameObject;

public interface ICollider {
	public boolean collidesWith(ICollider other);
	
	public void handleCollision(ICollider other);
	
	public void setCollisionFlag();
	public boolean getCollisionFlag();
	public void setCollisionFlagFalse();
}
