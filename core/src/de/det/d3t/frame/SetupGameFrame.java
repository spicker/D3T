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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
	private TextButtonStyle levelStyle;
	private TextButtonStyle levelStyleDisabled;
	
	private Button startLevelButton;
	private Button mainMenuButton;
	private Button saveGameButton;
	private Button loadGameButton;
	private Button closeGameButton;
	
	private Button level1Button;
	private Button level2Button;
	private Button level3Button;
	private Button level4Button;
	private Button level5Button;
	private Button level6Button;
	private Button level7Button;
	
	private Image conquered1;
	private Image conquered2;
	private Image conquered3;
	private Image conquered4;
	private Image conquered5;
	private Image conquered6;
	private Image conquered7;
	
	
	
	private Image uiBackground;
	private Image uiMiddleLevel;
	private Image stageBackground;
	private Label levelLabel;
	private Label levelTagLabel;
	private Label levelInfo;
	
	private String selectedLevel = "1";
	private LabelStyle ls;
	private LabelStyle ls2;
	
	

	private Game game;
	
	public SetupGameFrame(Game game){
		TextureFactory.loadAllSetupGameRessources();
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
		
		uiMiddleLevel = new Image(TextureFactory.getTexture("uiLevelSelect"));
		uiMiddleLevel.setBounds(width/2 - (5500/2), height/2 + height/5, 6000, 5500);
		

		
		ls = new LabelStyle();
		ls.font = TextureFactory.getFont("emmett",330, Color.valueOf("DDDCE0"));
		
		levelLabel = new Label("Level "  + selectedLevel, ls);
		levelLabel.setBounds(width/2 - (700/2),7600 , 700, 800);
		
		
		
		ui.addActor(uiMiddleLevel);
		ui.addActor(uiBackground);
		ui.addActor(levelLabel);
		
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
		
		
		
		stageBackground = new Image(TextureFactory.getTexture("setupGameBackground2")); //setupGameBackground2"));
		stageBackground.setBounds(0, 0, width, height);
		stageBackground.addListener(this);
		
		font = TextureFactory.getFont("emmett",290, Color.valueOf("484848"));
		levelStyle = new TextButtonStyle();
		levelStyle.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("level_green")));
		levelStyle.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("level_green_down")));
		levelStyle.font = font;
		levelStyle.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("level_green_over")));		
		
		font = TextureFactory.getFont("emmett",290, Color.valueOf("484848"));
		levelStyleDisabled = new TextButtonStyle();
		levelStyleDisabled.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("level_red")));
		levelStyleDisabled.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("level_red")));
		levelStyleDisabled.font = font;
		levelStyleDisabled.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("level_red")));	
		
		level1Button = new TextButton("1",levelStyle);
		level1Button.setBounds(width/2 - width/4 + width/40,height/2 + height/8 - height/80,500,500);
		level1Button.addListener(this);
		
		level2Button = new TextButton("2",levelStyleDisabled);
		level2Button.setBounds(width/2 - width/4 -width/20,height/2 - height/3 + 100,500,500);
		level2Button.addListener(this);
		
		level3Button = new TextButton("3",levelStyleDisabled);
		level3Button.setBounds(6800,4130,500,500);
		level3Button.addListener(this);
		
		level4Button = new TextButton("4",levelStyleDisabled);
		level4Button.setBounds(5450,2950,500,500);
		level4Button.addListener(this);
		
		level5Button = new TextButton("5",levelStyleDisabled);
		level5Button.setBounds(8700,2430,500,500);
		level5Button.addListener(this);
		
		level6Button = new TextButton("6",levelStyleDisabled);
		level6Button.setBounds(11500,6300,500,500);
		level6Button.addListener(this);
		
		level7Button = new TextButton("7",levelStyleDisabled);
		level7Button.setBounds(12200,3400,500,500);
		level7Button.addListener(this);
		
		
		conquered1=new Image(TextureFactory.getTexture("conquered1"));
		conquered1.setBounds(0, 0, width, height);
		conquered1.setVisible(false);
		
		conquered2=new Image(TextureFactory.getTexture("conquered2"));
		conquered2.setBounds(0, 0, width, height);
		conquered2.setVisible(false);

		conquered3=new Image(TextureFactory.getTexture("conquered3"));
		conquered3.setBounds(0, 0, width, height);
		conquered3.setVisible(false);
		
		conquered4=new Image(TextureFactory.getTexture("conquered4"));
		conquered4.setBounds(0, 0, width, height);
		conquered4.setVisible(false);
		
		conquered5=new Image(TextureFactory.getTexture("conquered5"));
		conquered5.setBounds(0, 0, width, height);
		conquered5.setVisible(false);
		
		conquered6=new Image(TextureFactory.getTexture("conquered6"));
		conquered6.setBounds(0, 0, width, height);
		conquered6.setVisible(false);
		
		conquered7=new Image(TextureFactory.getTexture("conquered7"));
		conquered7.setBounds(0, 0, width, height);
		conquered7.setVisible(false);
		
		
		
		stage.addActor(stageBackground);
		
		
		
		stage.addActor(conquered1);
		stage.addActor(conquered2);
		stage.addActor(conquered3);
		stage.addActor(conquered4);
		stage.addActor(conquered5);
		stage.addActor(conquered6);
		stage.addActor(conquered7);
		
		
		stage.addActor(level1Button);
		stage.addActor(level2Button);
		stage.addActor(level3Button);
		stage.addActor(level4Button);
		stage.addActor(level5Button);
		stage.addActor(level6Button);
		stage.addActor(level7Button);
		
		
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
		
		System.out.println("X: " + x + " Y: " + y);
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