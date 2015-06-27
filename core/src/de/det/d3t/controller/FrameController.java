package de.det.d3t.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;

import de.det.d3t.TextureFactory;
import de.det.d3t.frame.MenuFrame;
import de.det.d3t.util.Screenshooter;

public class FrameController extends Game implements ApplicationListener {

	private Screen currentScreen;
	private boolean released =true;
	
	@Override
	public void create() {
		System.out.println("Screen set to MenuFrame by default");
        setScreen(new MenuFrame(this));		
        currentScreen = (Screen) getScreen();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
	}

	@Override
	public void render() {
		super.render();
		//f12 --> Do Screenshot
		if(released == true && Gdx.input.isKeyPressed(Keys.F12)){
			//TODO: think about the display of "Screenshot has been saved to <insertName>" for a few seconds over the game
			Screenshooter.saveScreenshot();
			released = false;
		}
		if(released == false){
			if(!Gdx.input.isKeyPressed(Keys.F12)){
				released = true;
			}
		}
		
		
		if(currentScreen==null){

		}
	    else{
	    	if(!currentScreen.getClass().toString().equals(getScreen().getClass().toString())){
	    		System.out.println("Screen set to" + currentScreen.getClass().getSimpleName() + " by user");

	    	}
	    }
		
	}

	@Override
	public void pause() {
		super.pause();
		
	}

	@Override
	public void resume() {
		super.resume();
		
	}

	@Override
	public void dispose() {
		super.dispose();
		
	}

	@Override
	public void setScreen(Screen screen) {
	      if (currentScreen != null) {
	    	  this.screen.hide();
	    	  this.screen.dispose();
	    	  currentScreen.hide();
	    	  currentScreen.dispose();
	        }
	      currentScreen = screen;
	      currentScreen.show();
	      this.screen = currentScreen;
	      
	}
	
	
	

}
