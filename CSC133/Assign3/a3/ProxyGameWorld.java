package com.mycompany.a3;

import java.util.Observable;

import com.mycompany.Interfaces.IGameWorld;

public class ProxyGameWorld extends Observable implements IGameWorld{

	private GameWorld gameProxy;
	
	public ProxyGameWorld(GameWorld gameWorld)
	{
		gameProxy = gameWorld;
	} //End of ProxyGameWorld(GameWorld gameWorld)
	
	@Override
	public int getLastBase() {
		return gameProxy.getLastBase();
	} //End of getLastBase()
	
	@Override
	public int getLatestBaseReached(){
		return gameProxy.getLastBase();
	} //End of getLatestBaseReached()

	@Override
	public int getTimeInGame() {
		return gameProxy.getTimeInGame();
	} //End of getTimeInGame()

	@Override
	public int getGameLives() {
		return gameProxy.getGameLives();
	} //End of getGameLives()

	@Override
	public boolean getSoundOn() {
		return gameProxy.getSoundOn();
	} //End of getSoundOn()

	public int getLastBaseReached() {
		// TODO Auto-generated method stub
		return 0;
	} //End of getLastBaseReached() 
	
	public GameCollection2 getCollection2() {

	  	return gameProxy.getCollection2();
		} //End of getCollection2()
	
	
} //End of ProxyGameWorld