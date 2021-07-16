package com.mycompany.a3;
import java.util.Observable;

import com.codename1.ui.Dialog;
import com.mycompany.Interfaces.ICollider;
import com.mycompany.Interfaces.IGameWorld;
import com.mycompany.Interfaces.IIterator;
import com.mycompany.a3.Strategy3;
import com.mycompany.a3.Strategy4;
import com.mycompany.a3.Strategy5;

public class GameWorld extends Observable implements IGameWorld {
	
	private GameCollection2 gameObjects = new GameCollection2();
//	private Vector<GameObject> gameObjects = new Vector<GameObject>();
	private int numberOfCyborgs;
	private int numberOfDrones;
	private int numberOfBases;
	private int numberOfEnergyStations;
	private int timeInGame;
	private int lastBase;
	private int latestBaseReached;
	private int gameLives;
	private int npcStrategy;
	private boolean soundOn;
	private boolean endGame;
	private boolean NPCendGame;
	private boolean playerInGame;
	private boolean disableSound;
	private boolean disableSound2;

	private Sound energyStation = new Sound("energy_station.wav", this);
	private Sound death = new Sound("explosion1.wav", this);
	private Sound passbase = new Sound("pass_base.wav", this);
	private Sound cyborgcollide1 = new Sound("baseball_hit.wav", this);
	//private Sound cyborgcollide2 = new Sound("camera1.wav", this);
	private BGSound bg = new BGSound("bensound-moose.wav");


	private double GWWidth;
	private double GWHeight;
	
	private double elapsedTime;
	private int cyborgCollideTimer;


	public int gameObjectSize()
	{
		return gameObjects.size();
	}
	
	//public String returnForPrint(int i)
	//{
	//	String s = " " + gameObjects.elementAt(i).printCurrentStats() + " ";
		
	//return s;
	//}
	
	
	/////////////////////
	//UNIVERSAL METHODS//
	/////////////////////
	
	public void init()
	{   //Initial set up of Game World 
		
		numberOfCyborgs = 0;
		numberOfDrones = 0;
		numberOfBases = 0;
		numberOfEnergyStations = 0;
		soundOn = true;
		endGame = false;
		NPCendGame = false;
		playerInGame = false;
		disableSound = false;
		
		cyborgCollideTimer = 0;
		
		timeInGame = 0; //For Game Clock
		gameLives = 3; //For player lives
		npcStrategy = 1;
		
		bg.play();
		
		char c = 'c'; //For creating Player Cyborg
		char b = 'b'; //For creating Bases
		char d = 'd'; //For creating Drones
		char e = 'e'; //For creating Energy Stations
		char n = 'n'; //for creating NPC Cyborgs
		
		//Adding Objects to the Game World
		//(object type to add, object type number, how many of that object type)
		// 1 = Cyborge, 2 = Base, 3 = Drone, 4 = Energy Station
		addObj(c, 1, 1, gameObjects);
		addObj(c, 1, 2, gameObjects); //Attempt to create 2nd Player Cyborg
		addObj(c, 1, 2, gameObjects);
		addObj(b, 2, 1, gameObjects);
		addObj(b, 2, 2, gameObjects);
		addObj(b, 2, 3, gameObjects);
		addObj(b, 2, 4, gameObjects);
		addObj(b, 2, 5, gameObjects);
		addObj(b, 2, 6, gameObjects);
		setLastBase(6); //Setting up Last base
		addObj(d, 3, 1, gameObjects);
		addObj(d, 3, 2, gameObjects);
		addObj(e, 4, 1, gameObjects);
		addObj(e, 4, 2, gameObjects);
		addObj(n, 5, 1, gameObjects);
		addObj(n, 5, 2, gameObjects);
		addObj(n, 5, 3, gameObjects);
		//currentGameWorldValues(); //prints out starting Game World Values for player. 
		
	}  //End of init()

	public void addObj(char loc, int pta, int ptb, GameCollection2 gameObjects) {
		// Adds Objects to Game World based on type
		switch(loc)
		{
		case 'c': 
			if (playerInGame == false)
			{
			PlayerCyborg c = new PlayerCyborg(); //Instantiates Cyborg
			
			//Sets type of object and what number object
			c.setIDPartA(pta);
			c.setIDPartB(ptb);
			
			//Sets starting position
			int cyborgX = 55;
			int cyborgY = 55;
			c.setLocation(cyborgX, cyborgY);
			
			gameObjects.addElement(c); //Adds object to  Game Collection. 
			playerInGame = true;
			
			numberOfCyborgs = numberOfCyborgs + 1; //Sets number of Cyborges
			
			System.out.println("A Player Cyborg has been created and added to game world!");
			}
			else
			{
			System.out.println("There is already a Player Cyborg in the Game World!");	
			}
			break;
		case 'n': 
			NonPlayerCyborg n = new NonPlayerCyborg(); //Instantiates NPC Cyborg
			
			//Sets up NPC Strategies
			if (getNPCStrategy() == 1)
			{
				n.setStrategy(new Strategy3(gameObjects));
				n.setStratSelect(3);
				n.invokeStrategy(new Strategy3(gameObjects), n);
				setNPCStrategy();
			}
			else if (getNPCStrategy() == 2)
			{
				n.setStrategy(new Strategy4(gameObjects));
				n.setStratSelect(4);
				n.invokeStrategy(new Strategy4(gameObjects), n);
				setNPCStrategy();
			}
			else if (getNPCStrategy() == 3)
			{
				n.setStrategy(new Strategy5(gameObjects));
				n.setStratSelect(5);
				setNPCStrategy();
			}
			
			//Sets type of object and what number object
			n.setIDPartA(pta);
			n.setIDPartB(ptb);
			
			//Sets starting position
			int cyborgX1 = 300 + ptb;
			int cyborgY1 = 100 + ptb;
			n.setLocation(cyborgX1, cyborgY1);
			
			gameObjects.addElement(n); //Adds object to Game Collection. 
			
			numberOfCyborgs = numberOfCyborgs + 1; //Sets number of Cyborgs
			
			System.out.println("An NPC Cyborg has been created and added to game world!");
			break;
		case 'b':
			Base b = new Base();  //Instantiates Base
			
			//Sets type of object and what number object
			b.setIDPartA(pta);
			b.setIDPartB(ptb);
			
			b.setSegment(); //Sets what number for the track the base is
			
			//Sets starting position for bases initialized. 
			if (b.getSegment() == 1)
			{
				int firstBaseX = 55;
				int firstBaseY = 55;
				b.setLocation(firstBaseX, firstBaseY);
			}
			if (b.getSegment() == 2)
			{
				int firstBaseX = 335;
				int firstBaseY = 315;
				b.setLocation(firstBaseX, firstBaseY);
			}
			if (b.getSegment() == 3)
			{
				int firstBaseX = 300;
				int firstBaseY = 600;
				b.setLocation(firstBaseX, firstBaseY);
			}
			if (b.getSegment() == 4)
			{
				int firstBaseX = 500;
				int firstBaseY = 950;
				b.setLocation(firstBaseX, firstBaseY);
			}
			if (b.getSegment() == 5)
			{
				int firstBaseX = 900;
				int firstBaseY = 1050;
				b.setLocation(firstBaseX, firstBaseY);
			}
			if (b.getSegment() == 6)
			{
				int firstBaseX = 1000;
				int firstBaseY = 600;
				b.setLocation(firstBaseX, firstBaseY);
			}
			
			if (b.getSegment() == 7)
			{
				int firstBaseX = 895;
				int firstBaseY = 850;
				b.setLocation(firstBaseX, firstBaseY);
			}
			if (b.getSegment() == 8)
			{
				int firstBaseX = 900;
				int firstBaseY = 900;
				b.setLocation(firstBaseX, firstBaseY);
			}
			if (b.getSegment() == 9)
			{
				int firstBaseX = 999;
				int firstBaseY = 999;
				b.setLocation(firstBaseX, firstBaseY);
			}
			
			gameObjects.addElement(b); //Adds object to  Game Collection.
			
			//Sets number of Bases
			numberOfBases = numberOfBases + 1; //Sets number of Bases
			
			System.out.println("A Base has been created and added to game world!");
			break;
		case 'e':
			EnergyStation e = new EnergyStation();  //Instantiates Energy Station
			
			//Sets type of object and what number object
			e.setIDPartA(pta);
			e.setIDPartB(ptb);
			
			gameObjects.addElement(e); //Adds object to Game Collection.
			
			numberOfEnergyStations = numberOfEnergyStations + 1; //Sets number of Energy Stations
			
			System.out.println("An Energy Station has been created and added to game world!");
			break;
		case 'd':
			Drone d = new Drone(); //Instantiates Drone
			
			//Sets type of object and what number object
			d.setIDPartA(pta);
			d.setIDPartB(ptb);
			
			gameObjects.addElement(d); //Adds object to  Game Collection.
			
			numberOfDrones = numberOfDrones + 1; //Sets number of Drones
			
			System.out.println("A Drone has been created and added to game world!");
			break;
		} //End of switch
		} //End of addObj(char loc, int pta, int ptb)

	private void localNotifyObserver() {
		
		ProxyGameWorld prox = new ProxyGameWorld(this);
		this.setChanged();
		this.notifyObservers(prox);
		
		
		if(endGame)
		{
			
			String endText = "You Won The Game, yay!!!";
			if(Dialog.show("Game Over!", endText,"Quitting Game!", null))
				finishLine();
		} //End of if
		else if (NPCendGame)
		{
			String endText = "The Enemy Cyborg Won the Game!!! Which means... you... lose!";
			if(Dialog.show("Game Over!", endText,"Quitting Game!", null))
				NPCwin();
		}
} //End of  localNotifyObserver() 
	
	public void tickGame() {
		//Ticks the game world 

		
		while (elapsedTime < 20 )
			elapsedTime++;
		
		if (elapsedTime == 20)
		{
		timeInGame++; //Game world clock tick
		//System.out.println("The Game has Ticked!");
		localNotifyObserver(); //Updates Views
		
		findPlayerCyborg().setCyborgEnergyConsumptionRate(); //Cyborg consumes energy
		
		for(int i=0; i < gameObjects.size(); i++)
		{
			if(gameObjects.elementAt(i) instanceof MoveableGameObject)
			{
				if(gameObjects.elementAt(i) instanceof NonPlayerCyborg)
					{
					if(((NonPlayerCyborg) gameObjects.elementAt(i)).getLastBaseReached() != getLastBase())
					{
					activateStrategy(i);
					}
					}
				((MoveableGameObject) gameObjects.elementAt(i)).Move(GWWidth,GWHeight); //finding each moveable object and moving it
				
			} //End of if(gameObjects.get(i) instanceof MoveableGameObject)
		} //End of for(int i=0; i < gameObjects.size(); i++)
		
		if (findPlayerCyborg().getCyborgEnergyLevel() == 0)
		{ //Bad stuff happens if Energy Level reaches 0
			setGameLives(); //Game lives go down one
			localNotifyObserver();
			if (getGameLives() == 0)
			{ //If Game Lives reach 0, then it's all over for the human
				gameOver();
			} //End of if (getGameLives() == 0)
			
			findPlayerCyborg().resetCyborg(); //If a life is lost, resets Cyborg back to base lives and starting location
			localNotifyObserver(); //Updates game world
			
		} //End of if (findPlayerCyborg().getCyborgEnergyLevel() == 0)
		}

		elapsedTime = 0;
		CheckCollisions();

	} //End of tickGame() 
	
	private void CheckCollisions() {
		IIterator iter = gameObjects.getIterator();
		while(iter.hasNext())
		{
			GameObject thisObj = iter.getNext();
			if(thisObj instanceof ICollider)
			{
				ICollider thisColliderObj = (ICollider) thisObj;
				IIterator otherIter = gameObjects.getIterator();
				while(otherIter.hasNext())
				{
					GameObject otherObj = otherIter.getNext();
					if(otherObj instanceof ICollider && !(thisObj.equals(otherObj)))
					{
						ICollider otherColliderObj = (ICollider) otherObj;
						
						if(thisColliderObj.collidesWith(otherColliderObj))
						{
							thisColliderObj.handleCollision(otherColliderObj);

							if (thisColliderObj instanceof PlayerCyborg && otherColliderObj instanceof EnergyStation)
							energyStationCollision2((PlayerCyborg)thisColliderObj, (EnergyStation)otherColliderObj);
							else if (thisColliderObj instanceof PlayerCyborg && otherColliderObj instanceof Drone)
							droneCollision2((PlayerCyborg)thisColliderObj, (Drone)otherColliderObj);
							else if (thisColliderObj instanceof PlayerCyborg && otherColliderObj instanceof NonPlayerCyborg)
								cyborgCollision2((PlayerCyborg)thisColliderObj, (NonPlayerCyborg)otherColliderObj);
							else if (thisColliderObj instanceof PlayerCyborg && otherColliderObj instanceof Base)
								baseCollision2((PlayerCyborg)thisColliderObj, (Base)otherColliderObj);
							else if (thisColliderObj instanceof NonPlayerCyborg && otherColliderObj instanceof Base)
								baseCollision3((NonPlayerCyborg)thisColliderObj, (Base)otherColliderObj);
						}
					}
				}
			}
		}
		removeCollidedObjects();
		localNotifyObserver();
	}
	
	
	private void removeCollidedObjects()
	{
		IIterator iter = gameObjects.getIterator();
		
		while(iter.hasNext())
		{
			GameObject obj = iter.getNext();
			if (obj instanceof ICollider && ((ICollider)obj).getCollisionFlag())
			{
				iter.remove(obj);
				
				}
			}
		}

	
	
	/////////////////
	//GET METHODS//
	////////////////
	
	public int getGameLives() {
		return gameLives;
	} //End of getGameLives()
	
	public int getTimeInGame() {
		return timeInGame;
	} //End of getGameLives()
	
	public int getLastBase() {  
		//Gets the last base. 
		return lastBase;
	}  //End of getLastBase
	
	public int getLatestBaseReached() 
	{
		return latestBaseReached;
	} //End of getLatestBaseReached()
	
	public int getNumberOfBases()
	{
		return numberOfBases;
	} //End of getNumberOfBases()
	
	public int getNumberOfCyborgs()
	{
		return numberOfCyborgs;
	} //End of getNumberOfCyborgs()
	
	public int getNumberOfDrones()
	{
		return numberOfDrones;
	} //End of getNumberOfBases()
	
	public int getNumberOfEnergyStations()
	{
		return numberOfEnergyStations;
	} //End of getNumberOfCyborgs()
	
	public GameCollection2 getCollection2() {

  	return gameObjects;
	} //End of getCollection2()
	
	public boolean getSoundOn() 
	{
		return soundOn;
	} //End of  getSoundOn() 

	
	
	
	/////////////////
	//SET METHODS//
	////////////////
	
	public void setSoundOn() {
		if (soundOn == true)
		{
			soundOn = false;
			disableSound = true;
		//	bg.pause();
		}
		else if (soundOn == false && disableSound2 == false)
		{
			soundOn = true;
			disableSound = false;
		//	bg.play();
		}
		
		if(soundOn == true && disableSound2 == false)
		{
			bg.play();
		}
		else
			bg.pause();

		localNotifyObserver(); //Updates Game World
	} //End of setSoundOn()
	
	public void setGameLives() {
		--gameLives;
		if(soundOn == true)
		death.play();
	} //End of setGameLives() 
	
	private void setLastBase(int th) {  
		//Sets the highest level base in game as The Last base for the finish line
		lastBase = th;
	}  //End of setLastBase
	
	public void setLatestBaseReached(int latestBaseReached) 
	{
		this.latestBaseReached = latestBaseReached;
	} //End of setLatestBaseReached(int latestBaseReached) 
	
	
	
	///////////////////////
	//GAME END CONDITIONS//
	///////////////////////
	
	protected void exit() {   
		// Exits Game. The final exit called whenever an end game state is reached. 
		System.out.println("Your game... ends now!");
		 System.exit(0);
	}   //End of exit 
	
	public void finishLine() {	
		//When the player reaches the last base!
		//End Game State. 
		System.out.println("You reached the last base, you won the game! You did so in " + timeInGame + " ticks.\nI guess humans aren't so bad, afterall!");
		exit();
	}   //End of finishLine
	
	private void gameOver() {
		// Player has lost the game!
		System.out.println("You just lost the game! I guess you are... only human, afterall."); 
		exit();
	} //End of gameOver()
	
	private void NPCwin() {
		// Another Cyborg won the race
		System.out.println("You just lost the game! I guess you are... only human, afterall."); 
		exit();
	} //End of gameOver()
	
	
	
	////////////////////////////
	//PLAYER AFFECTING METHODS//
	////////////////////////////
	
	public void accelerate() {
		// Accelerates the Cyborg
		
		if(findPlayerCyborg() != null)
		{
			findPlayerCyborg().cyborgSpeedUp(); //finds player ship, and changes increases speed
		} //End of if(findPlayerCyborg() != null)
		else
			System.out.println("No Cyborg Exists!");
		localNotifyObserver(); //Updates Views
	} // End of accelerate() 
	

	public void brakeForCyborg() {
		// Slows down the Cyborg
		if(findPlayerCyborg() != null)
		{
			findPlayerCyborg().cyborgSlowDown(); //finds player ship, and changes increases speed
			localNotifyObserver(); //Updates Views
		}	//End of if(findPlayerCyborg() != null)
	} //End of brakeForCyborg()

	
	public void cyborgDamaged(int th) {
		//This is what happens when the Cyborg is damaged
		
		findPlayerCyborg().setCyborgDamageLevel(th); //Damages Cyborg
		
	
		
		
		if (findPlayerCyborg().getSpeedDamage() <= 0)
		{ //What happens when Cyborg's movement level is reduced to 0 or below
	
			
			findPlayerCyborg().resetCyborg();
			setGameLives();
		} //End of if (findPlayerCyborg().getSpeedDamage() <= 0)
		
		if (getGameLives() == 0)
		{
			gameOver();
		} //End of if (getGameLives() == 0)
	} //End of cyborgDamaged(int th)
	
	public void moveCyborg(char c) {
		if(findPlayerCyborg() != null)
		{
			findPlayerCyborg().turn(c); //finds player ship, and changes dir
			localNotifyObserver(); //Updates Views
		} //End of 	if(findPlayerCyborg() != null)
	} //End of public void moveCyborg(char c)
	
	
	
	//////////////
	//COLLISIONS//
	//////////////
	
	public void baseCollision2(PlayerCyborg a, Base b) {
		// Collision with Base, either hit the right base or wrong
		
		if (b.getSegment() == (a.getLastBaseReached() + 1))
		{
			//System.out.println("You hit the right BASE! Now onto the next base: " + (b.getSegment() + 1));				
			a.setLastBaseReached(b.getSegment()); //Sets the base as reached
			setLatestBaseReached(b.getSegment());
			if(soundOn == true)
			passbase.play();
			localNotifyObserver(); //Updates Views
			if (b.getSegment() == (getLastBase() - 1))
			{
				a.setoneMoreBase(true);
			} //End of if
		} //End of if (findPlayerCyborg().getCyborgLastBaseReached()
		else if (b.getSegment() > getLastBase())
		{
			System.out.println("No such Base exists. Try again.");	
		} //End of else if
		else if (b.getSegment() == getLastBase() && a.getLastBaseReached() == getLastBase())
		{
			endGame = true;
		}
		else
		{
			System.out.println("You hit the wrong BASE! You want Base # " + (findPlayerCyborg().getLastBaseReached() + 1));	
		} //End of else
		
	} //End of baseCollision(int i) 
	
	public void baseCollision3(NonPlayerCyborg a, Base b) {
		// Collision with Base, either hit the right base or wrong
		
		if (b.getSegment() == (a.getLastBaseReached() + 1))
		{
		//	System.out.println("NPC hit the right BASE! Now onto the next base: " + (b.getSegment() + 1));				
			a.setLastBaseReached(b.getSegment()); //Sets the base as reached
			setLatestBaseReached(b.getSegment());
			localNotifyObserver(); //Updates Views
			if (b.getSegment() == (getLastBase() - 1))
			{
				a.setoneMoreBase(true);
			} //End of if
		} //End of if (findPlayerCyborg().getCyborgLastBaseReached()
		else if (b.getSegment() > getLastBase())
		{
		//	System.out.println("No such Base exists. Try again.");	
		} //End of else if
		else if (b.getSegment() == getLastBase() && a.getLastBaseReached() == getLastBase())
		{
			NPCendGame = true;
		}
		
	} //End of baseCollision(int i) 

	
	public void cyborgCollision2(PlayerCyborg a, NonPlayerCyborg b) {
		// This is when two cyborgs collide!
		
		if(soundOn == true)
		cyborgcollide1.play();
		if(soundOn == true)
	//	cyborgcollide2.play();
		if(soundOn == true)
		cyborgcollide1.play();
		
		if (cyborgCollideTimer == 0)
		{
	//	System.out.println("Your Cyborg has collided with another Cyborg!");
		a.setCyborgDamageLevel(b.getAtkValue()); //Damages Cyborg
		}
		cyborgCollideTimer++;
	
	if (cyborgCollideTimer > 15)
		cyborgCollideTimer = 0;
		
		
		if (a.getSpeedDamage() <= 0)
		{ //What happens when Cyborg's movement level is reduced to 0 or below
			a.resetCyborg();
			setGameLives();
		} //End of if (findPlayerCyborg().getSpeedDamage() <= 0)
		
		if (getGameLives() == 0)
		{
			gameOver();
		} //End of if (getGameLives() == 0)
		//cyborgDamaged(findNonPlayerCyborg().getAtkValue()); //Cyborg gets damaged
		localNotifyObserver(); //Updates Views
	} //End of cyborgCollision()

	public void energyStationCollision2(Cyborg a, EnergyStation b) {
		// This is what happens when you run across an energy station
		
		
		if (b.getCapacity() > 0)
		{
			if(soundOn == true)
			energyStation.play();

		a.setCyborgEnergyLevel(b.getCapacity()); //Adds energy from station to Cyborg's Energy
		b.stationCollision(); //Sets Capacity to 0, changes color
		
		
		char e = 'e'; //For creating Energy Stations
		addObj(e, 4, numberOfEnergyStations, gameObjects); //Creates a new energy station. 
		//addObj(e, 4, numberOfEnergyStations, gameObjects);
		
		localNotifyObserver(); //Updates Views
		
	//	System.out.println("Your Cyborg has collided with an Energy Station!");
		}
		
	} //End of energyStationCollision(Cyborg a)


	public void droneCollision2(PlayerCyborg a, Drone b) {
		// Cyborg collides with Drone

		if(soundOn == true)
		cyborgcollide1.play();
		if(soundOn == true)
	//	cyborgcollide2.play();
		if(soundOn == true)
		cyborgcollide1.play();

		a.setCyborgDamageLevel(b.getAtkValue()); //Damages Cyborg
		
		if (a.getSpeedDamage() <= 0)
		{ //What happens when Cyborg's movement level is reduced to 0 or below
			a.resetCyborg();
			setGameLives();
		} //End of if (findPlayerCyborg().getSpeedDamage() <= 0)
		
		if (getGameLives() == 0)
		{
			gameOver();
		} //End of if (getGameLives() == 0)
		
	} //End of droneCollision
	

	
	//////////////////////////////////////////
	//FIND A SPECIFIC TYPE OF OBJECT METHODS//
	//////////////////////////////////////////
	public Cyborg findPlayerCyborg()
	{
		for(int i=0; i < gameObjects.size(); i++)
		{
			if(gameObjects.elementAt(i) instanceof PlayerCyborg)
			{
				return (PlayerCyborg) gameObjects.elementAt(i);
			} //End of if(gameObjects.get(i)
		} //End of for(int i=0; i < gameObjects.size(); i++)
		
		System.out.println("Your Cyborg is missing!");
		return null;
	} //End of Cyborg findPlayerCyborg()
	
	
	////////////////////
	//STRATEGY METHODS//
	////////////////////
	
	public void changeNPCStrategy() {
		//Allows NPCs to switch between the strategies.

		for(int i=0; i < gameObjects.size(); i++)
		{
			if(gameObjects.elementAt(i) instanceof Cyborg)
			{
				//System.out.println("Attempting change, NPC Strat = " + npcStrategy);
				if (getNPCStrategy() == 1)
				{
					((MoveableGameObject) gameObjects.elementAt(i)).setStrategy(new Strategy3(gameObjects));
					((MoveableGameObject) gameObjects.elementAt(i)).setStratSelect(3);
					setNPCStrategy();
				//	System.out.println("Attempting change, NPC Strat = " + npcStrategy);
				}
				else if (getNPCStrategy() == 2)
				{
					((MoveableGameObject) gameObjects.elementAt(i)).setStrategy(new Strategy4(gameObjects));
					((MoveableGameObject) gameObjects.elementAt(i)).setStratSelect(4);
					setNPCStrategy();
				//	System.out.println("Attempting change, NPC Strat = " + npcStrategy);
				}
				else if (getNPCStrategy() == 3)
				{
					((MoveableGameObject) gameObjects.elementAt(i)).setStrategy(new Strategy5(gameObjects));
					((MoveableGameObject) gameObjects.elementAt(i)).setStratSelect(5);
					setNPCStrategy();
				//	System.out.println("Attempting change, NPC Strat = " + npcStrategy);
				}
				
				activateStrategy(i); //To initiate whatever strategies get added to an NPC

			} //End of if(gameObjects.elementAt(i) instanceof Cyborg)
		} //End of for(int i=0; i < gameObjects.size(); i++)
		
		localNotifyObserver(); //Updates Views
	} //End of changeNPCStrategy()
	
	public void activateStrategy(int i)
	{
		//Once a strategy is added, initiates that strategy
		if(gameObjects.elementAt(i) instanceof NonPlayerCyborg)
		{
			if (((NonPlayerCyborg) gameObjects.elementAt(i)).getStratSelect() == 3)
			{
			//	System.out.println("IS THIS HAPPENING...This is a NonPlayerCyborg StratSelect = " + ((NonPlayerCyborg) gameObjects.elementAt(i)).getStratSelect());
			((NonPlayerCyborg) gameObjects.elementAt(i)).invokeStrategy(new Strategy3(gameObjects), gameObjects.elementAt(i));
			}
			else if (((NonPlayerCyborg) gameObjects.elementAt(i)).getStratSelect() == 4)
			{
			//	System.out.println("IS THIS HAPPENING...This is a NonPlayerCyborg StratSelect = " + ((NonPlayerCyborg) gameObjects.elementAt(i)).getStratSelect());
				((NonPlayerCyborg) gameObjects.elementAt(i)).invokeStrategy(new Strategy4(gameObjects), gameObjects.elementAt(i));
			}
			else if (((NonPlayerCyborg) gameObjects.elementAt(i)).getStratSelect() == 5)
			{
			//	System.out.println("IS THIS HAPPENING...This is a NonPlayerCyborg StratSelect = " + ((NonPlayerCyborg) gameObjects.elementAt(i)).getStratSelect());
				((NonPlayerCyborg) gameObjects.elementAt(i)).invokeStrategy(new Strategy5(gameObjects), gameObjects.elementAt(i));
			}
		} //End of if(gameObjects.elementAt(i) instanceof NonPlayerCyborg)
	} //End of activateStrategy(int i)
	
	public int getNPCStrategy()
	{
		//Value that NPCs based their initial strategy on
		return npcStrategy; 
	} //End of getNPCStrategy()
	
	public void setNPCStrategy()
	{ 
		//Makes it easier to stagger strategies between newly added NPCs
		 if (npcStrategy == 3)
			 npcStrategy = 1;
		 else
			 npcStrategy++;
	} //End of setNPCStrategy()
	
	
	
	public void setDisableSound(boolean val) {
		disableSound = val;
}

public boolean getDisableSound() {
	// TODO Auto-generated method stub
	return disableSound;
}

public void setDisableSound2(boolean val) {
	disableSound2 = val;
}

public boolean getDisableSound2() {
// TODO Auto-generated method stub
return disableSound2;
}

public void setGWHeight(double mapHeight) {
	GWHeight = mapHeight;
}

public double getGWHeight() {
// TODO Auto-generated method stub
return GWHeight;
}



public void setGWWidth(double mapWidth) {
	// TODO Auto-generated method stub
	GWWidth = mapWidth;
}

public double getGWWidth() {
	// TODO Auto-generated method stub
	return this.GWWidth;
}


	

} //End of GameWorld
