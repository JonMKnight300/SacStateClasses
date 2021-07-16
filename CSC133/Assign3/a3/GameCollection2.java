package com.mycompany.a3;

import java.util.Vector;

import com.mycompany.Interfaces.ICollection;
import com.mycompany.Interfaces.IIterator;

public class GameCollection2 implements ICollection {
	private Vector<GameObject> aCollection;
	
	public GameCollection2() {
		aCollection = new Vector<GameObject>();
	} //End of GameCollection2()
	
	public int size() {
		return aCollection.size();
	} //End of size()
	
	public void addElement(GameObject newObject) {
		aCollection.addElement(newObject);
	} //End of addElement(GameObject newObject)

	public IIterator getIterator() {
		return new GameObjectIterator();
	} //End of IIterator getIterator()
	
	private class GameObjectIterator implements IIterator { 
		
		private int currElementIndex;
		
		public GameObjectIterator() {
			currElementIndex = -1;
		} //End of GameObjectIterator()
		
		public boolean hasNext() {
			if (aCollection.size ( ) <= 0) return false;
			if (currElementIndex == aCollection.size()-1)	return false;
		
			return true;
		} //End of hasNext()
		
		public GameObject getNext() {
			currElementIndex++;
			return(aCollection.elementAt(currElementIndex));
		} //End of GameObject getNext()
		
		public int getIndex() {
			return currElementIndex;
		} //End of getIndex()
		
		public GameObject get(int location) {
			return null;
		} //End of GameObject get(int location) 

		@Override
		public GameObject getCurrent() {
			return null;
		} //End of GameObject getCurrent()
		
		@Override
		public void remove(GameObject gameObj) {
		} //End of  remove(GameObject gameObj)
		
	} //End of GameObjectIterator

	public GameObject elementAt(int location) {
		return aCollection.elementAt(location);
	} //End of GameObject elementAt(int location)

	public void remove(GameObject newObj) {
		aCollection.remove(newObj);
	} //End of remove(GameObject newObj)

	public Base getBase(int currentBase) {
		IIterator i = ((ICollection) aCollection).getIterator();
		while (i.hasNext()) {
			GameObject o = i.getNext();
			if (o instanceof Base) {
				int pyNum = ((Base) o).getSegment();
			
				if (pyNum == currentBase) {
					return (Base) o;
				} //End of if
			} //End of if
		} //End of while 
		return null;
	} //End of Base getBase(int currentBase)

	public Cyborg getCyborg() {
		IIterator i = ((ICollection) aCollection).getIterator();
		while (i.hasNext()) {
			GameObject o = i.getNext();
			if (o instanceof Cyborg) {
					return (Cyborg) o;
			} //End of if
		} //End of while
		return null;
	} //End of Cyborg getCyborg() 
	
	public PlayerCyborg getPlayerCyborg() {
		IIterator i = ((ICollection) aCollection).getIterator();
		while (i.hasNext()) {
			GameObject o = i.getNext();
			if (o instanceof PlayerCyborg) {
					return (PlayerCyborg) o;
			} //End of if
		} //End of while
		return null;
	} //End of Cyborg getPlayerCyborg()

} //End of GameCollection2
