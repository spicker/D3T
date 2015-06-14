package de.det.d3t.frame;


import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.model.Enemy;

public class MenuFrame extends InputListener implements Screen {
	private Stage stage;
	private Stage ui;
	private StretchViewport stageViewport;
	private StretchViewport uiViewport;
	private OrthographicCamera stageCamera;
	private OrthographicCamera uiCamera;
	private InputMultiplexer inputMultiplexer;
	private FPSLogger fpsLogger;
	
	
	//private TextureAtlas buttonAtlas;
	//private Skin skin;
	private TextButtonStyle textButtonStyle;
	private Button startGameButton;
	private Button loadGameButton;
	private Button startOptionsButton;
	private Button closeGameButton;
	private Button startCreditsButton;
	private Button helpButton;//TODO: add help button
	
	private Image menuBg;
	private Image menuTitle;
	
	
	private BitmapFont font;
	private SpriteBatch  batch;
	
	
	private float width;
	private float height;
	
	private long timeOld = 0;
	private long timeNew = 0;
	
	private ParticleEffect particleEffect;
	private ParticleEffectPool pool;
	private Array<PooledEffect> effects;
	
	private Game game;
	
	
	public MenuFrame(Game game){
		this.game = game;

		TextureFactory.loadAllMenuRessources();
		//TODO: remove later and put the correct buttons in "loadAllMenuRessources"
		TextureFactory.loadAllButtons();
		setupStage();
		setupUI();
		manageInputs();
		fpsLogger = new FPSLogger();
		width = stageViewport.getWorldWidth();
		height = stageViewport.getWorldHeight();

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
		

		
		//////////UI/////////        
       // skin = new Skin();
		//buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/button.pack"));
	    //skin.addRegions(buttonAtlas);
	   // skin = new Skin(Gdx.files.internal("uiskin.json"));
	    
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
		
		ui.addActor(menuBg);
		Texture texture = new Texture("badlogic.jpg");
		Image i = new Image(texture);
		ui.addActor(new Enemy(500, 610, 0.7f,false));
		i = new Image(texture);
		i.setBounds(0, 0, 500, 500);
		i.rotateBy(180);
		ui.addActor(i);
		ui.addActor(menuTitle);
	    ui.addActor(startGameButton);
	    ui.addActor(loadGameButton);
	    ui.addActor(startOptionsButton);
	    ui.addActor(startCreditsButton);
	    ui.addActor(closeGameButton);
	    		
		
		//////////UI/////////
		
		/////////Stage//////////

		
		/////////Stage//////////
		
	}
	
	
	public void setupStage(){
		 stageCamera = new OrthographicCamera();
		 stageCamera.zoom = 1f;
		 stageViewport = new StretchViewport(2560, 1600, stageCamera);
		 stage = new Stage(stageViewport);
	}
	
	public void setupUI(){
		uiCamera = new OrthographicCamera();
		uiCamera.zoom = 1f;
		uiViewport = new StretchViewport(2560, 1600, uiCamera);
		ui = new Stage(uiViewport);
	}
	

	
	public void manageInputs(){
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(ui);
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void resize(int width, int height) {
		stageViewport.update(width, height);
		uiViewport.update(width, height);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stageCamera.update();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		ui.act(Gdx.graphics.getDeltaTime());;
		ui.draw();
		fpsLogger.log();	
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
			Settings.basePositionMenuX = randInt(0,(int) ((int)width));
			Settings.basePositionMenuY = randInt(0,(int) ((int)height));
			timeOld = System.currentTimeMillis();
		}
		
		
		
		
		//width = Gdx.graphics.getWidth();
		//height = Gdx.graphics.getHeight();
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
		if(event.getListenerActor() == startGameButton){
			return true;
		}	
		if(event.getListenerActor() == startOptionsButton){
			return true;
		}		
		if(event.getListenerActor() == startCreditsButton){
			return true;
		}		
		if(event.getListenerActor() == helpButton){
			return true;
		}		
		if(event.getListenerActor() == closeGameButton){
			return true;
		}		
		return false;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		if(event.getListenerActor() == startGameButton){
            game.setScreen(new GameFrame(game));
		}
		if(event.getListenerActor() == closeGameButton){
			Gdx.app.exit();
		}	

	}
	
	
	public static int randInt(int min, int max) {

	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	
}