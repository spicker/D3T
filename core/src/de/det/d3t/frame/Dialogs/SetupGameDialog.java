package de.det.d3t.frame.Dialogs;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
import de.det.d3t.controller.LevelController;
import de.det.d3t.frame.GameFrame;
import de.det.d3t.frame.MenuFrame;


	public class SetupGameDialog extends Dialog{
		private Image background;
		
		private Label title;
		
		private TextButton btnContinuePlaying, btnBackToMainMenu;
	    
	    private String titleText;
	    
	    private boolean gameWon;

	    private float width;
	    
	    private float height;
	    
	    private int playingLevel;
	    
	    private Game game;

	    private Music music;

	    public SetupGameDialog(Game game, float width, float height, String titleText, boolean gameWon, int playingLevel, Music music){
	    	this.game = game;
	        this.width = width;
	        this.height = height;
	        this.titleText = titleText;
	        this.gameWon = gameWon;
	        this.playingLevel = playingLevel;
	        this.music = music;
	        
	    }
		
	    @Override
		public void showDialog(){
			Label.LabelStyle style = new Label.LabelStyle();
			if(gameWon){
				style.font= TextureFactory.getFont("emmett", 400, Color.GREEN);
			}
			else{
				style.font= TextureFactory.getFont("emmett", 400, Color.RED);
			}
	        title = new Label(titleText, style);
	        
	        background = new Image(TextureFactory.getTexture("selectedLevelBackground"));
	        //background.setScale(2.3f);
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
	        
			btnContinuePlaying  = new TextButton("Bleibe in Level-Auswahl", textButtonStyle);
			btnBackToMainMenu= new TextButton("Zurück zum Hauptmenü und Spiel schließen", textButtonStyle);

			btnContinuePlaying.setBounds(background.getX() + background.getWidth()/2 - 3000 , background.getY() + background.getWidth()/2 -2500 - 1000, 6000, 700);
			btnBackToMainMenu.setBounds(background.getX() + background.getWidth()/2 - 3000 , background.getY() + background.getWidth()/2 -2500 , 6000, 700);

			btnContinuePlaying.addListener(this);
	        btnBackToMainMenu.addListener(this);

	        addActor(background);
	        addActor(title);

	        addActor(btnContinuePlaying);
	        addActor(btnBackToMainMenu);
		}
		
		
	    @Override
	    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	        if(event.getListenerActor() == btnBackToMainMenu){
	        	return true;
	        }

	        if(event.getListenerActor() == btnContinuePlaying){
	            return true;
	        }
			return false;
	    }

	    @Override
	    public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
	        if(event.getListenerActor() == btnContinuePlaying)
	        {     	
	        	Settings.setShowGameFinishedDialog(false);
	        	closeDialog();
	        }

	        if(event.getListenerActor() == btnBackToMainMenu){
	        	
	        	
	        	Settings.setLevelConquered(new boolean[]{false,false,false,false,false,false,false});
	        	Settings.setLevelUnlocked(new boolean[]{true,false,false,false,false,false,false});
	        	Settings.setShowGameFinishedDialog(false);
	        	music.stop();
	        	
	        	game.setScreen(new MenuFrame(game));
	        	closeDialog();   	
	        }
	    }
	}
