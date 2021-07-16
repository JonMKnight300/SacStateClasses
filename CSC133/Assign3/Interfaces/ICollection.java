package com.mycompany.Interfaces;

import com.mycompany.a3.GameObject;

public interface ICollection {

	public abstract void addElement(GameObject newObject);
	public abstract IIterator getIterator();
	public abstract GameObject elementAt(int location);
	public abstract int size();
    public abstract void remove(GameObject newObj);

}
