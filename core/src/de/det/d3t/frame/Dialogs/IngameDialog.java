package de.det.d3t.frame.Dialogs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.frame.GameFrame;
import de.det.d3t.frame.SetupGameFrame;


	public class IngameDialog extends Dialog{
		private Game game;
		
		private Image background;
		
		private Label title;
		
		private TextButton btnSetupGameFrame, btnRestart;
	    
	    private String titleText;
	    
	    private boolean gameWon;

	    private float width;
	    
	    private float height;
	    
	    private int playingLevel;


	    public IngameDialog(Game game, float width, float height, String titleText, boolean gameWon, int playingLevel){
	    	this.game = game;
	        this.width = width;
	        this.height = height;
	        this.titleText = titleText;
	        this.gameWon = gameWon;
	        this.playingLevel = playingLevel;
	        
	    }
		
	    @Override
		public void showDialog(){
			Label.LabelStyle style = new Label.LabelStyle();
	        style.font= TextureFactory.getFont("emmett", 400, Color.valueOf("DDDCE0"));
	        title = new Label(titleText, style);
	        
	        background = new Image(TextureFactory.getTexture("selectedLevelBackground"));
			background.setBounds(width/2 - (8000/2), height/2 - (4000/2) + 1800, 8000, 4000);
	        title.setBounds(background.getX() + background.getWidth()/2 - (3600/2) , background.getY() + background.getWidth()/2 -1000 , 4000, 700);
	        
	        BitmapFont fontTitle = TextureFactory.getFont("emmett", 350, Color.valueOf("DDDCE0"));
	   
			TextButtonStyle textButtonStyleTitle = new TextButtonStyle();
			textButtonStyleTitle.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew")));
			textButtonStyleTitle.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_down")));
			textButtonStyleTitle.font = fontTitle;
			textButtonStyleTitle.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_over")));
	        
			BitmapFont fontContent= TextureFactory.getFont("emmett", 280, Color.valueOf("DDDCE0"));
			
			TextButtonStyle textButtonStyle = new TextButtonStyle();
			textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew")));
			textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_down")));
			textButtonStyle.font = fontContent;
			textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_over")));
	        
			btnSetupGameFrame = new TextButton("Zurück zur Level-Auswahl", textButtonStyle);
			btnRestart= new TextButton("Level neu starten", textButtonStyle);

			btnRestart.setBounds(background.getX() + background.getWidth()/2 - 3000 , background.getY() + background.getWidth()/2 -2500 - 1000, 6000, 700);
	        btnSetupGameFrame.setBounds(background.getX() + background.getWidth()/2 - 3000 , background.getY() + background.getWidth()/2 -2500 , 6000, 700);

	        btnRestart.addListener(this);
	        btnSetupGameFrame.addListener(this);

	        addActor(background);
	        addActor(title);

	        addActor(btnRestart);
	        addActor(btnSetupGameFrame);
		}
		
		
	    @Override
	    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	        if(event.getListenerActor() == btnRestart){
	        	return true;
	        }

	        if(event.getListenerActor() == btnSetupGameFrame){
	            return true;
	        }
			return false;
	    }

	    @Override
	    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        if(event.getListenerActor() == btnRestart)
	        {
	        	//TODO: clear all towers, enemies and Projectiles from the stage
	        	//TODO: reset values (gold, time wave etc)
	        	System.out.println("set game frame restart level");
	        	game.setScreen(new GameFrame(game,playingLevel));
	        	closeDialog();
	        }

	        if(event.getListenerActor() == btnSetupGameFrame){
	        	if(gameWon){
	        		boolean[] lc = Settings.getLevelConquered();
	        		boolean[] lu = Settings.getLevelUnlocked();	        			        		
	 
	        		lu[playingLevel-1] = true;
	        		lc[playingLevel-1] = true;
	        		
	        		switch(playingLevel){
	        		case 1:lu[1] = true;break;
	        		case 2:lu[2] = true;lu[3] = true;break;
	        		case 3:if(lc[3] && lc[2]){ lu[4] = true;} break;
	        		case 4:if(lc[3] && lc[2]){ lu[4] = true;} break;
	        		case 5:if(lc[2] && lc[3] && lc[4]){lu[5] = true;}break;
	        		case 6:lu[6] = true; break;
	        		case 7: break;
	        		}
	        		
	        		Settings.setLevelConquered(lc);
	        		Settings.setLevelUnlocked(lu);
	        		if(lc[6]){
	        			game.setScreen(new SetupGameFrame(game,true));
	        		}
	        		else{
	        			game.setScreen(new SetupGameFrame(game,false));
	        		}
	        	}
	        	else{
	        		game.setScreen(new SetupGameFrame(game,false));	
	        	}
	        	closeDialog();   	
	        }
	    }
	

	}

