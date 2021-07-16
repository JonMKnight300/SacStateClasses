package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;

public class ScoreView extends Container implements Observer  {

private GameWorld gw;
	
	private Label time = new Label("Time: ");
	private Label livesLeft = new Label("Lives: ");
	private Label latestBase = new Label("Highest Base Reached: ");
	private Label energyRemaining = new Label("Energy Remaining: ");
	private Label damageLevel = new Label("Damage: ");
	private Label sound = new Label("Sound: ");
	
	public ScoreView(GameWorld gw) {
		this.gw = gw;
		
        this.setLayout(new BoxLayout(BoxLayout.X_AXIS));
        this.getAllStyles().setBorder(Border.createLineBorder(2, ColorUtil.WHITE));
		
        time.getAllStyles().setFgColor(ColorUtil.BLUE);
        livesLeft.getAllStyles().setFgColor(ColorUtil.BLUE);
        latestBase.getAllStyles().setFgColor(ColorUtil.BLUE);
        energyRemaining.getAllStyles().setFgColor(ColorUtil.BLUE);
        damageLevel.getAllStyles().setFgColor(ColorUtil.BLUE);
        sound.getAllStyles().setFgColor(ColorUtil.BLUE);
        
        
        this.add(time);
        this.add(livesLeft);
        this.add(latestBase);
        this.add(energyRemaining);
        this.add(damageLevel);
        this.add(sound);
	}
	
	public boolean updateLabels() {
		if(gw == null) return false;
		
		gw.findPlayerCyborg().setNumberOfLives(gw.getGameLives());
		time.setText("Time: " + Double.toString(gw.getTimeInGame()));
		livesLeft.setText("Lives Left: " + Integer.toString(gw.getGameLives()));
		latestBase.setText("Latest Base #: " + Integer.toString(gw.findPlayerCyborg().getLastBaseReached()));
		energyRemaining.setText("Energy Remaining:" + Integer.toString(gw.findPlayerCyborg().getCyborgEnergyLevel()));
		damageLevel.setText("Damage Level: " + Double.toString(gw.findPlayerCyborg().getDamageLevel()));
		if (gw.getSoundOn())
			sound.setText("Sound: ON");
		else
			sound.setText("Sound: OFF");
		
		return true;
	} //End of updateLabels()
	
	public void update (Observable o, Object arg) {			
		this.updateLabels();
		this.setVisible(true);
	} //End of update

} //End of ScoreView

