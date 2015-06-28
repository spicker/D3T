package de.det.d3t.frame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;

public class SetupGameFrame extends InputListener implements Screen {
	private Stage stage;
	private Stage ui;
	private StretchViewport stageViewport;
	private StretchViewport uiViewport;
	private OrthographicCamera stageCamera;
	private OrthographicCamera uiCamera;
	private InputMultiplexer inputMultiplexer;
	private FPSLogger fpsLogger;
	
	private float width;
	private float height;
	
	private Music bgmMusic;
	private Sound buttonClickSound;
	
	private BitmapFont font;
	private TextButtonStyle textButtonStyle;
	
	private Button startLevelButton;
	private Button mainMenuButton;
	private Button saveGameButton;
	private Button loadGameButton;
	private Button closeGameButton;
	
	private Image uiBackground;
	private Image stageBackground;
	
	

	private Game game;
	
	public SetupGameFrame(Game game){
		TextureFactory.loadALlSetupGameRessources();
		setupStage();
		setupUI();
		manageInputs();
		fpsLogger = new FPSLogger();
		this.game = game;
		
		width = stageViewport.getWorldWidth();
		height = stageViewport.getWorldHeight();
		
		bgmMusic = TextureFactory.getMusic("quietStraightBgm");
		bgmMusic.setLooping(true);
		bgmMusic.setVolume(Settings.getBgm());
		bgmMusic.play();
		buttonClickSound = TextureFactory.getSound("buttonClick");
		
		font = TextureFactory.getFont("emmett",200, Color.valueOf("DDDCE0"));
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew")));
		textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_down")));
		textButtonStyle.font = font;
		textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("blueButtonNew_over")));		
		
		
		uiBackground = new Image(TextureFactory.getTexture("uiNewTop"));
		uiBackground.setBounds(0, 0, width, height);
		ui.addActor(uiBackground);
		
		
		
		
		
		startLevelButton = new TextButton("Level Starten",textButtonStyle);
		startLevelButton.setBounds(width/2 -(1900/2) + 4000, height/2  + height/3 + height/10, 1900, 500);
		startLevelButton.addListener(this);
		//TODO: find a place where this button belongs
		
		
		
		mainMenuButton = new TextButton("Hauptmenü",textButtonStyle);
		mainMenuButton.setBounds(width/2 -(1900/2), height/2  + height/3 + height/10, 1900, 500);
		mainMenuButton.addListener(this);
		
		saveGameButton = new TextButton("Spiel Speichern",textButtonStyle);
		saveGameButton.setBounds(width/2 -(1900/2) - 2000, height/2  + height/3 + height/10, 1900, 500);
		saveGameButton.addListener(this);
		
		loadGameButton = new TextButton("Spiel Laden", textButtonStyle);
		loadGameButton.setBounds(width/2 -(1900/2) - 4000, height/2  + height/3 + height/10, 1900, 500);
		loadGameButton.addListener(this);
		
		closeGameButton = new TextButton("Spiel Beenden",textButtonStyle);
		closeGameButton.setBounds(width/2 -(1900/2) - 6000, height/2  + height/3 + height/10, 1900, 500);
		closeGameButton.addListener(this);
		
		ui.addActor(startLevelButton);
		ui.addActor(mainMenuButton);
		ui.addActor(saveGameButton);
		ui.addActor(loadGameButton);
		ui.addActor(closeGameButton);
		
		
		
		stageBackground = new Image(TextureFactory.getTexture("setupGameBackgroundExample")); //setupGameBackground2"));
		stageBackground.setBounds(0, 0, width, height);
		
		
		stage.addActor(stageBackground);
		
		//TODO: stage --> background image of world and levels a.s.o.
		
	}
	
	
	public void setupStage(){
		 stageCamera = new OrthographicCamera();
		 stageCamera.zoom = 1f;
		 stageViewport = new StretchViewport(Settings.viewportWidth, Settings.viewportHeight, stageCamera);
		 stage = new Stage(stageViewport);
	}
	
	public void setupUI(){
		uiCamera = new OrthographicCamera();
		uiCamera.zoom = 1f;
		uiViewport = new StretchViewport(Settings.viewportWidth, Settings.viewportHeight, uiCamera);
		ui = new Stage(uiViewport);
	}
	
	public void manageInputs(){
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(ui);
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void render(float delta) {
		
		stage.act();
		ui.act();
		
		stage.draw();
		ui.draw();
		
	}	
	
	@Override
	public void resize(int width, int height) {
		stageViewport.update(width, height);
		uiViewport.update(width,height);
		
		this.width = stageViewport.getWorldWidth();
		this.height = stageViewport.getWorldHeight();		
		
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		
		if(event.getListenerActor().equals(startLevelButton)){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if(event.getListenerActor().equals(mainMenuButton)){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if(event.getListenerActor().equals(saveGameButton)){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if(event.getListenerActor().equals(loadGameButton)){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if(event.getListenerActor().equals(closeGameButton)){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}
		
		
		return false;
		
	}


	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		
		if(event.getListenerActor().equals(startLevelButton)){
			game.setScreen(new GameFrame(game));
			bgmMusic.stop();
		}
		if(event.getListenerActor().equals(mainMenuButton)){
			//TODO: show dialog "You are leaving your running game. Unsaved progress will be lost! Save before going back to the MainMenu?"
			game.setScreen(new MenuFrame(game));
			bgmMusic.stop();
		}
		if(event.getListenerActor().equals(saveGameButton)){

		}
		if(event.getListenerActor().equals(loadGameButton)){

		}
		if(event.getListenerActor().equals(closeGameButton)){
			//TODO: show dialog "You are closing the game. Unsaved progress will be lost! Save before going back to the MainMenu?"
			Gdx.app.exit();
		}
		
	
	}


	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		// TODO Auto-generated method stub
		super.touchDragged(event, x, y, pointer);
	}


	@Override
	public boolean mouseMoved(InputEvent event, float x, float y) {
		// TODO Auto-generated method stub
		return super.mouseMoved(event, x, y);
	}


	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		// TODO Auto-generated method stub
		super.enter(event, x, y, pointer, fromActor);
	}


	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		// TODO Auto-generated method stub
		super.exit(event, x, y, pointer, toActor);
	}


	@Override
	public boolean scrolled(InputEvent event, float x, float y, int amount) {
		// TODO Auto-generated method stub
		return super.scrolled(event, x, y, amount);
	}


	@Override
	public boolean keyDown(InputEvent event, int keycode) {
		// TODO Auto-generated method stub
		return super.keyDown(event, keycode);
	}


	@Override
	public boolean keyUp(InputEvent event, int keycode) {
		// TODO Auto-generated method stub
		return super.keyUp(event, keycode);
	}


	@Override
	public boolean keyTyped(InputEvent event, char character) {
		// TODO Auto-generated method stub
		return super.keyTyped(event, character);
	}
	
	
	
}