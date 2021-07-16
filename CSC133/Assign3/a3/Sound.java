package com.mycompany.a3;

import java.io.InputStream;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;
import com.mycompany.a3.GameWorld;

public class Sound {
	protected Media m;
	protected GameWorld gw;
	public Sound(String fileName, GameWorld gameWorld) {
		this.gw = gameWorld;
		try{
			InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+fileName);
			m = MediaManager.createMedia(is, "audio/wav");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
//	public void sample() {
//		Sound("carcrash.wav");
//	}
	
	public void play() {
		//start playing the sound from time zero (beginning of the sound file)
		m.setTime(0);
		m.play();
	}
	
	public void pause() {
		m.pause();
	}
	
	public void resume() {
		m.play();
	}
}