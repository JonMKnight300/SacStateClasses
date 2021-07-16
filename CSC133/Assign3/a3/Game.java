package com.mycompany.a3;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form; 
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.Label; 
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Point;

import java.lang.String;

import com.mycompany.Interfaces.IIterator;
import com.mycompany.Interfaces.ISelectable;
import com.mycompany.cmd.*;

public class Game extends Form implements Runnable {
	private GameWorld gw;  
	private MapView mv;
	private ScoreView sv; 
	private Container rightContainer = new Container(new BoxLayout(Component.RIGHT));
	private static String Title = "Cyborg Game"; 
	
	private boolean paused = false;
	
	private final double TIMETOTICK = 50;
	private int timeElapsed = 0;
	
	private AccelerateCommand accelerateCommand;
	private aButtonObject accelButton;
	private BrakeCommand brakCommand;
	private aButtonObject brakeButton;

	private LeftTurnCommand lefCommand;
	private aButtonObject leftButton;
	
	private RightTurnCommand righCommand;
	private aButtonObject rightButton;
	
	private StrategyCommand stratCommand;
	private aButtonObject stratButton;
	
	private PositionCommand positionCmd;
	private aButtonObject positionButton;
	
	private aButtonObject gamePauseButton;
	private PauseCommand pauseCmd;
	
	private UITimer timer;

	
	public Game() {   
		  gw = new GameWorld();       
		  mv = new MapView(gw);     
		  sv = new ScoreView(gw);  
		  
		  gw.addObserver(mv);  // register the map observer   
		  gw.addObserver(sv);  // register the score observer 
	
		    setLayout(new BorderLayout()); 
		    settingUpGameMenu();
		  
			this.addComponent(BorderLayout.CENTER, mv);
			this.addComponent(BorderLayout.NORTH, sv);

			gw.init();  
			sv.updateLabels();
		  
		 this.show(); 
		 
		 positionButton.setEnabled(false);
			gw.setGWHeight(mv.getMapHeight());
			gw.setGWWidth(mv.getMapWidth());
			
			timer  =  new UITimer(this);
			timer.schedule((int)TIMETOTICK,true, this);
		
		} //End of Game() 
	
	
	void settingUpGameMenu()
	{
		  Toolbar myToolbar = new Toolbar(); 
		  setToolbar(myToolbar);
		  
		  myToolbar.setTitle(Title);

		  Toolbar.setOnTopSideMenu(true); 
		  
		  //SIDE MENU
		  accelerateCommand = new AccelerateCommand(gw);
		  myToolbar.addCommandToSideMenu(accelerateCommand);
		  
		  CheckBox soundCheckBox = new CheckBox("Sound");
		  SoundCommand souCommand = new SoundCommand(gw, soundCheckBox);
		  soundCheckBox.setCommand(souCommand);
		  myToolbar.addCommandToSideMenu(souCommand);
		  
		  HelpCommand helCommand = new HelpCommand(); 
		  addKeyListener('h', helCommand);
		  myToolbar.addCommandToRightBar(helCommand);
		  
		  AboutCommand abouCommand = new AboutCommand(); 
		  addKeyListener('u', abouCommand);
		  myToolbar.addCommandToSideMenu(abouCommand);
		  
		  ExitCommand exiCommand = new ExitCommand(); 
		  addKeyListener('x', exiCommand);
		  myToolbar.addCommandToSideMenu(exiCommand);
		  
		  
		  //SIDE BUTTONS
		  Container mainContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
			mainContainer.setScrollableY(false);
			Label commandLabel = new Label("Commands");
			mainContainer.add(commandLabel);
			
			accelButton = new aButtonObject(accelerateCommand);
			//addKeyListener('a', accelerateCommand);
			addKeyListener('w', accelerateCommand);
			mainContainer.add(accelButton);
			
			brakCommand = new BrakeCommand(gw);
			brakeButton = new aButtonObject(brakCommand);
//			addKeyListener('b', brakCommand);
			addKeyListener('s', brakCommand);
			mainContainer.add(brakeButton);
			
			lefCommand = new LeftTurnCommand(gw);
			leftButton = new aButtonObject(lefCommand);
			addKeyListener('a', lefCommand);
			//addKeyListener('l', lefCommand);
			mainContainer.add(leftButton);
			
			righCommand = new RightTurnCommand(gw);
			rightButton = new aButtonObject(righCommand);
			//addKeyListener('r', righCommand);
			addKeyListener('d', righCommand);
			mainContainer.add(rightButton);
			
			stratCommand = new StrategyCommand(gw);
			stratButton = new aButtonObject(stratCommand);
			//addKeyListener('s', stratCommand);
			addKeyListener('e', stratCommand);
			mainContainer.add(stratButton);
			
			
			pauseCmd = new PauseCommand(this);
			gamePauseButton = new aButtonObject(pauseCmd);
			addKeyListener('p', pauseCmd);
			mainContainer.add(gamePauseButton);
			
			this.addComponent(BorderLayout.WEST, mainContainer);
		
			
			 //BOTTOM BUTTONS
				mainContainer.setScrollableY(false);
				
				
				Label pauseLabel = new Label("Pause Features:");
				rightContainer.add(pauseLabel);
				positionCmd = new PositionCommand("Position", mv);
				positionButton = new aButtonObject(positionCmd);
				rightContainer.add(positionButton);
				
				this.addComponent(BorderLayout.SOUTH, rightContainer);

	} //End of settingUpGameMenu()


	@Override
	public void run() {
	
		
		  if (!paused) {
				gw.tickGame();
				timeElapsed += TIMETOTICK;
		  }
				  mv.repaint();
		
	}
	
	public void pauseGame()
	{
		this.paused = !paused;
		
		accelButton.setEnabled(!accelButton.isEnabled());
		brakeButton.setEnabled(!brakeButton.isEnabled());
		leftButton.setEnabled(!leftButton.isEnabled());
		rightButton.setEnabled(!rightButton.isEnabled());
		
		
		if(paused)
		{
		//	gw.setDisableSound(true);
			positionButton.setEnabled(true);
			timer.cancel();
			gamePauseButton.setText("Resume");
			
			if (gw.getSoundOn())
			gw.setSoundOn();
			
			mv.setIsPaused(true);

			removeKeyListener('w', accelerateCommand);
	 		
           removeKeyListener('s', brakCommand);

			removeKeyListener('a', lefCommand);

			removeKeyListener('d', righCommand);	 
		 
 			removeKeyListener('p', pauseCmd);
			
 			gw.setDisableSound2(true);
		}
		else
		{
			mv.setIsPaused(false);
		//	gw.setDisableSound(false);
			IIterator iterator = gw.getCollection2().getIterator();
			while (iterator.hasNext())
			{
				GameObject curObj = iterator.getNext();
				if (curObj instanceof ISelectable)
				{
					ISelectable selectObj = (ISelectable)curObj;
	
						selectObj.setSelected(false);

				}
			}
			positionButton.setEnabled(false);
			timer.schedule((int)TIMETOTICK, true, this);
			
			gamePauseButton.setText("Pause");
			
			gw.setDisableSound2(false);
			
			if(!gw.getDisableSound())
			gw.setSoundOn();

			addKeyListener('w', accelerateCommand);
	 		

			addKeyListener('s', brakCommand);

			addKeyListener('a', lefCommand);

			addKeyListener('d', righCommand);
			

		 
 			addKeyListener('p', pauseCmd);
 			
		}

	}
	
} //End of Game

			 





