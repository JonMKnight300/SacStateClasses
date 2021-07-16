package com.mycompany.Interfaces;

import com.mycompany.a3.GameObject;

public interface IIterator {

	public abstract boolean hasNext();
	public abstract GameObject getNext();
	public abstract GameObject getCurrent();
	public abstract void remove(GameObject gameObj);
    public abstract GameObject  get(int location);
    public abstract int getIndex();
	
}
