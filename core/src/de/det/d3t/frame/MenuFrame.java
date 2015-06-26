package de.det.d3t.frame;


import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.model.Enemy;

public class MenuFrame extends InputListener implements Screen {
	
	private Stage settingsStage;
	private Stage uiStage;
	private Stage creditsStage;
	private StretchViewport stageViewport;
	private StretchViewport uiViewport;
	private StretchViewport creditsViewport;
	private OrthographicCamera stageCamera;
	private OrthographicCamera uiCamera;
	private OrthographicCamera creditsCamera;
	private InputMultiplexer inputMultiplexer;
	private FPSLogger fpsLogger;
	
	private boolean inCredits = false;
	private boolean inSettings = false;
	private boolean inMenu = true;
	
	
	private TextButtonStyle textButtonStyle;
	private Button startGameButton;
	private Button loadGameButton;
	private Button startOptionsButton;
	private Button closeGameButton;
	private Button startCreditsButton;
	private Button helpButton;//TODO: add help button
	private Image menuBg;
	private Image menuTitle;
	
	
	private Slider bgmSlider;
	private Slider sfxSlider;
	private LabelStyle ls;
	private Label bgmLabel;
	private Label sfxLabel;
	private Button backButton;
	private Button acceptButton;
	private Image settingsBg;
	
	private float sfxToSet = 100f;
	private float bgmToSet = 100f;
	
	private BitmapFont font;
	private SpriteBatch  batch;
	
	
	private float width;
	private float height;
	
	private long timeOld = 0;
	private long timeNew = 0;
	
	private ParticleEffect particleEffect;
	private ParticleEffectPool pool;
	private Array<PooledEffect> effects;
	
	private Music bgmMusic;
	private Sound buttonClickSound;
	
	private Game game;
	
	
	
	
	public MenuFrame(Game game){
		this.game = game;
		TextureFactory.loadAllMenuRessources();
		//TODO: remove later and put the correct buttons in "loadAllMenuRessources"
		TextureFactory.loadAllButtons();
		setupStage();
		setupUI();
		setupCreditsStage();
		manageInputs();
		fpsLogger = new FPSLogger();
		width = stageViewport.getWorldWidth();
		height = stageViewport.getWorldHeight();
		//bgmMusic = TextureFactory.getMusic("menuBgm");
		bgmMusic = TextureFactory.getMusic("happyBgm");
		bgmMusic.setLooping(true);
		bgmMusic.setVolume(Settings.getBgm());
		bgmMusic.play();
		buttonClickSound = TextureFactory.getSound("buttonClick");
		buttonClickSound.setVolume(0, Settings.getSfx());

		timeOld = System.currentTimeMillis();
		
		batch = new SpriteBatch();
		
		particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("effects/unicornParticles.p"), Gdx.files.internal("particle"));
		particleEffect.setPosition(width / 2, height / 2);
		particleEffect.start();
		pool = new ParticleEffectPool(particleEffect, 0, 70);
		effects = new Array<PooledEffect>();
		
		
		PooledEffect effect = pool.obtain();
		effect.setPosition(310, 690);
		effect.setDuration(2000000000);
		effects.add(effect);
		PooledEffect effect2 = pool.obtain();
		effect2.setPosition(855, 670);
		effect2.setDuration(2000000000);
		effects.add(effect2);
		

		
		//////////////////////////////////////UI-STAGE//////////////////////////////////////    
        font = TextureFactory.getFont("emmett",48, Color.valueOf("484848"));
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("button_metal")));
		textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("button_metal_down")));
		textButtonStyle.font = font;
		textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("button_metal_over")));

		menuTitle = new Image(TextureFactory.getTexture("menuTitle"));
		menuTitle.setBounds(width/2 - 750, height/2 + height/7, 1500, 600);
		
	    startGameButton = new TextButton("Spiel starten", textButtonStyle);
	    startGameButton.setBounds(width/2 - 300, height/2 + 100, 600, 150);
	    startGameButton.addListener(this);
	    
	    loadGameButton = new TextButton("Spiel laden", textButtonStyle);
	    loadGameButton.setBounds(width/2 - 300, height/2 -100, 600, 150);
	    loadGameButton.addListener(this);
	    
	    startOptionsButton = new TextButton("Optionen", textButtonStyle);
	    startOptionsButton.setBounds(width/2 - 300, height/2 -300, 600, 150);
	    startOptionsButton.addListener(this);
	    
	    startCreditsButton = new TextButton("Mitwirkende", textButtonStyle);
	    startCreditsButton.setBounds(width/2 - 300, height/2 -500 , 600, 150);
	    startCreditsButton.addListener(this);
	    
	    closeGameButton = new TextButton("Spiel Beenden", textButtonStyle);
	    closeGameButton.setBounds(width/2 - 300, height/2 -700, 600, 150);
	    closeGameButton.addListener(this);
	    
		menuBg = new Image(TextureFactory.getTexture("menuBackground"));
		menuBg.setBounds(0, 0, width, height);
		
		uiStage.addActor(menuBg);
		Texture texture = new Texture("badlogic.jpg");
		Image i = new Image(texture);
		uiStage.addActor(new Enemy(500, 610, 0.7f,false));
		i = new Image(texture);
		i.setBounds(0, 0, 500, 500);
		i.rotateBy(180);
		uiStage.addActor(i);
		uiStage.addActor(menuTitle);
	    uiStage.addActor(startGameButton);
	    uiStage.addActor(loadGameButton);
	    uiStage.addActor(startOptionsButton);
	    uiStage.addActor(startCreditsButton);
	    uiStage.addActor(closeGameButton);
	    //////////////////////////////////////UI-STAGE//////////////////////////////////////  
		
	    
	    
	    
	    //////////////////////////////////////OPTIONS-STAGE//////////////////////////////////////  
	    //TODO: think about other options and put them here
		SliderStyle sliderStyle = new SliderStyle();
		sliderStyle.background = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("slider_bar1")));
		sliderStyle.background.setMinWidth(600);
		sliderStyle.knob = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("slider_knob1")));
		sliderStyle.background.setMinHeight(40);
		sliderStyle.knob.setMinHeight(100);
		sliderStyle.knob.setMinWidth(100);

		
		ls = new LabelStyle();
		ls.font = TextureFactory.getFont("emmett",48, Color.valueOf("484848"));
	    bgmSlider = new Slider(0f, 100f, 0.1f, false, sliderStyle);
	    bgmSlider.setBounds(width/2 + 400, height/2 - 200, 800, 200);
	    bgmSlider.setValue(Settings.getBgm()*100);
	    bgmSlider.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				bgmLabel.setText("Musik-Lautstärke: " + String.format("%.01f", bgmSlider.getValue()) + " %");
				//Settings.setBgm(bgmSlider.getValue()/100);
				bgmToSet = bgmSlider.getValue()/100;
			}
			
		});
	    bgmLabel = new Label("Musik-Lautstärke: " + String.format("%.01f", bgmSlider.getValue()) + " %", ls);
	    bgmLabel.setBounds(width/2 - 200, height/2 -200, 800, 200);
	    
	    sfxSlider = new Slider(0f, 100f, 0.1f, false, sliderStyle);
	    sfxSlider.setBounds(width/2 + 400, height/2, 800, 200);
	    sfxSlider.setValue(Settings.getSfx()*100);
	    sfxSlider.addListener(new ChangeListener(){

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				sfxLabel.setText("Sound-Lautstärke: " + String.format("%.01f", sfxSlider.getValue()) + " %");
				//Settings.setBgm(sfxSlider.getValue()/100);
				sfxToSet = sfxSlider.getValue()/100;
			}
			
		});
	    sfxLabel = new Label("Sound-Lautstärke: " + String.format("%.01f", sfxSlider.getValue()) + " %", ls);
	    sfxLabel.setBounds(width/2 - 200, height/2, 800, 200);
	    
	    backButton = new TextButton("Zurück", textButtonStyle);
	    backButton.setBounds(width/2 - 1100, height/2 -700, 600, 150);
	    backButton.addListener(this);
	    
	    acceptButton = new TextButton("Bestätigen", textButtonStyle);
	    acceptButton.setBounds(width/2 + 500, height/2 -700, 600, 150);
	    acceptButton.addListener(this);
	    
		settingsBg = new Image(TextureFactory.getTexture("optionsBackground"));
		settingsBg.setBounds(0, 0, width, height);
	    
	        
		settingsStage.addActor(settingsBg);
	    settingsStage.addActor(sfxLabel);
	    settingsStage.addActor(sfxSlider);
	    settingsStage.addActor(bgmLabel);
	    settingsStage.addActor(bgmSlider);
	    settingsStage.addActor(backButton);
	    settingsStage.addActor(acceptButton);
	    
	    //////////////////////////////////////OPTIONS-STAGE//////////////////////////////////////  
	    
	    
	    
	    
	    //////////////////////////////////////CREDITS-STAGE//////////////////////////////////////  
	    //TODO: add credits of the Game (creatively or "normal")
	    //TODO: add possible easter eggs
	    
	    //////////////////////////////////////CREDITS-STAGE//////////////////////////////////////  
		
	}
	
	
	public void setupStage(){
		 stageCamera = new OrthographicCamera();
		 stageCamera.zoom = 1f;
		 stageViewport = new StretchViewport(2560, 1600, stageCamera);
		 settingsStage = new Stage(stageViewport);
	}
	
	public void setupUI(){
		uiCamera = new OrthographicCamera();
		uiCamera.zoom = 1f;
		uiViewport = new StretchViewport(2560, 1600, uiCamera);
		uiStage = new Stage(uiViewport);
	}
	
	public void setupCreditsStage(){
		creditsCamera = new OrthographicCamera();
		creditsCamera.zoom = 1f;
		creditsViewport = new StretchViewport(2560, 1600, creditsCamera);
		creditsStage = new Stage(creditsViewport);
	}
	

	
	public void manageInputs(){
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(uiStage);
		inputMultiplexer.addProcessor(settingsStage);
		inputMultiplexer.addProcessor(creditsStage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void resize(int width, int height) {
		stageViewport.update(width, height);
		uiViewport.update(width, height);
		creditsViewport.update(width,height);
		this.height = stageViewport.getWorldHeight();
		this.width = stageViewport.getWorldWidth();
		
	}
	
	@Override
	public void pause() {
		
		
	}
	@Override
	public void resume() {
		
	}
	@Override
	public void dispose() {
		batch.dispose();
		particleEffect.dispose();
	}

	@Override
	public void show() {
		
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stageCamera.update();
		if(!inCredits && !inMenu){
			settingsStage.act(Gdx.graphics.getDeltaTime());
			settingsStage.draw();
		}
		if(!inSettings && !inCredits){
			uiStage.act(Gdx.graphics.getDeltaTime());;
			uiStage.draw();
			batch.begin();
			for(PooledEffect effect : effects) {
				effect.draw(batch, delta);
				if(effect.isComplete()) {
					effect.start();
					//PooledEffect effe = effect;
					//effects.removeValue(effect, true);
					//effect.free();
					//effects.add(effe);
				}
			}
			batch.end();
			//Settings.basePositionMenuX = Gdx.input.getX();
			//Settings.basePositionMenuY = Gdx.input.getY();
			
			timeNew = System.currentTimeMillis();
			if(timeNew-timeOld > 5000){
				Settings.setBasePositionMenuX(randInt(0,(int) ((int)width)));
				Settings.setBasePositionMenuY(randInt(0,(int) ((int)height)));
				timeOld = System.currentTimeMillis();
			}
		}
		if(!inSettings && !inMenu){
			creditsStage.act(Gdx.graphics.getDeltaTime());;
			creditsStage.draw();
		}
		

		fpsLogger.log();	
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		if(event.getListenerActor() == startGameButton){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}	
		if(event.getListenerActor() == startOptionsButton){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}		
		if(event.getListenerActor() == startCreditsButton){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}		
		if(event.getListenerActor() == helpButton){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}		
		if(event.getListenerActor() == closeGameButton){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}		
		if(event.getListenerActor() == backButton){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}	
		if(event.getListenerActor() == acceptButton){
			buttonClickSound.play(Settings.getSfx());
			return true;
		}	
		
		return false;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		if(event.getListenerActor() == startGameButton){
            game.setScreen(new GameFrame(game));
            bgmMusic.stop();
		}
		
		if(event.getListenerActor() == startOptionsButton){
			inSettings = true;
			inMenu = false;
		}		
		if(event.getListenerActor() == closeGameButton){
			Gdx.app.exit();
		}	
		
		
		
		
		
		
		if(event.getListenerActor() == backButton){
			inSettings = false;
			inMenu = true;
			sfxSlider.setValue(Settings.getSfx()*100);
			bgmSlider.setValue(Settings.getBgm()*100);
			sfxLabel.setText("Sound-Lautstärke: " + String.format("%.01f", sfxSlider.getValue()) + " %");
			bgmLabel.setText("Musik-Lautstärke: " + String.format("%.01f", bgmSlider.getValue()) + " %");
		}
		if(event.getListenerActor() == acceptButton){
			Settings.setBgm(bgmToSet);
			Settings.setSfx(sfxToSet);
			inSettings = false;
			inMenu = true;
			bgmMusic.setVolume(Settings.getBgm());
		}
		

	}
	
	
	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	
}