package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Point;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;
import com.mycompany.Interfaces.IDrawable;
import com.mycompany.Interfaces.IIterator;
import com.mycompany.Interfaces.ISelectable;
import com.mycompany.a3.GameWorld;

public class MapView extends Container implements Observer  {

private TextArea gameText;

private int px;
private int py;
private boolean isPaused;
private boolean moveObject;

private GameWorld gw;
	
	public MapView(GameWorld gw)
	{
		this.gw = gw;
		this.getAllStyles().setBorder(Border.createLineBorder(6,ColorUtil.argb(2, 200, 20, 20)));
		this.setLayout(new BorderLayout());
		
		gameText = new TextArea();
		gameText.setEditable(false);
		gameText.getAllStyles().setBgTransparency(0);
		isPaused = false;
		moveObject = false;
		
	} // End of MapView(GameWorld gw)
	
	public void setIsPaused(boolean isP)
	{
		isPaused = isP;
	}
	
	
	@Override
	public void update(Observable observable, Object data) {

		this.repaint();
		
	} //End of update
	
	public double getMapWidth()
	{
		double retval = (double) this.getWidth();
		
		return retval;
	}
	
	public double getMapHeight()
	{
		double retval = (double) this.getHeight();
	
		return retval;

	}
	
 @Override
	public void pointerPressed(int x, int y) 
	{ 
	 
		if (isPaused)
		{
			System.out.println("Inside PointerPresseds: (" + x + ", " + y + ") - Bef");
		x = x - getParent().getAbsoluteX(); 
		y = y - getParent().getAbsoluteY(); 
		System.out.println("Inside PointerPresseds: (" + x + ", " + y + ") - Aft");
		Point pPtrRelPrnt = new Point(x, y); 
		Point pCmpRelPrnt = new Point(getX(), getY()); 
		System.out.println("Inside PointerPresseds: pPTRRelPrnt (" + x + ", " + y + ") - Aft");
		System.out.println("Inside PointerPresseds: pCmpRelPrnt (" + x + ", " + y + ") - Aft");
		
		IIterator iterator = gw.getCollection2().getIterator();
		
		while (iterator.hasNext())
		{
			GameObject curObj = iterator.getNext();
			if (curObj instanceof ISelectable)
			{
				ISelectable selectObj = (ISelectable)curObj;
				

				
				if (selectObj.contains(pPtrRelPrnt, pCmpRelPrnt) && moveObject == false)
				{
					selectObj.setSelected(true);
				}
				else if (!selectObj.contains(pPtrRelPrnt, pCmpRelPrnt) && moveObject == false)
				{
					selectObj.setSelected(false);
				}
				else if (moveObject == true && selectObj.isSelected())
				{
					curObj.setLocation(x - 260, y + 20);
					((ISelectable) curObj).setSelected(false);
					moveObject = false;
					System.out.println("InsideMoveObject else if and selectObj is (" + curObj.getDeltaX() + ", " + curObj.getDeltaY() + ")");
				}
			}
		}
		
		repaint(); 
		}
		}
 
 public void setMoveObject(boolean val)
 {
	 moveObject = val;
 }
	

@Override
public void paint(Graphics graph)
{
	//System.out.println("MAP is " + getMapWidth() + " * " + getMapHeight());
	super.paint(graph);
	
	Point pCmpRelPrnt = new Point(this.getX(), this.getY());
	IIterator iterator = gw.getCollection2().getIterator();
	
	while (iterator.hasNext())
	{
		GameObject curObject = iterator.getNext();
		if (curObject instanceof IDrawable)
		{

			((IDrawable) curObject).draw(graph, pCmpRelPrnt);
		}
	}
}
	
	
} //End of MapView extends Container implements Observer
