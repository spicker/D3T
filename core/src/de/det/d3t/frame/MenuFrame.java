package de.det.d3t.frame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.controller.CameraInputController;
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
	private Button startOptionsButton;
	private Button closeGameButton;
	private Button startCreditsButton;
	private Button helpButton;//TODO: add help button
	
	private Image menuBg;
	
	
	private BitmapFont font;
	
	
	private float width;
	private float height;
	
	private Game game;
	
	
	public MenuFrame(Game game){
		this.game = game;

		TextureFactory.loadAllMenuRessources();
		//TODO: remove later and put the correct buttons in "loadAllMenuRessources"
		TextureFactory.loadAllButtons();
		//TODO: remove later and put the correct fonts in "loadAllMenuRessources"
		TextureFactory.loadAllFonts();
		setupStage();
		setupUI();
		manageInputs();
		width = stageViewport.getWorldWidth();
		height = stageViewport.getWorldHeight();
		fpsLogger = new FPSLogger();
		
		
		
		//////////UI/////////        
       // skin = new Skin();
		//buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/button.pack"));
	    //skin.addRegions(buttonAtlas);
	   // skin = new Skin(Gdx.files.internal("uiskin.json"));
	    
        font = TextureFactory.getFont("emmet");
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("button_metal")));
		textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("button_metal_down")));
		textButtonStyle.font = font;
		textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("button_metal_over")));
		
	    startGameButton = new TextButton("Spiel starten", textButtonStyle);
	    startGameButton.setBounds(width/2 - 1650, height/2 +700, 3000, 800);
	    startGameButton.addListener(this);
	    
	    startOptionsButton = new TextButton("Optionen", textButtonStyle);
	    startOptionsButton.setBounds(width/2 - 1650, height/2 -300, 3000, 800);
	    startOptionsButton.addListener(this);
	    
	    startCreditsButton = new TextButton("Mitwirkende", textButtonStyle);
	    startCreditsButton.setBounds(width/2 - 1650, height/2 -1300, 3000, 800);
	    startCreditsButton.addListener(this);
	    
	    closeGameButton = new TextButton("Spiel Beenden", textButtonStyle);
	    closeGameButton.setBounds(width/2 - 1650, height/2 -2300, 3000, 800);
	    closeGameButton.addListener(this);
	    
		menuBg = new Image(TextureFactory.getTexture("menuBackground"));
		menuBg.setBounds(0, 0, width, height);
		
		ui.addActor(menuBg);
		
	    ui.addActor(startGameButton);
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
		//inputMultiplexer.addProcessor(new CameraInputController(stageCamera)); //TODO Add some real controller here
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void resize(int width, int height) {
		stageViewport.update(width, height);
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
	
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
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
	
	
	
	
	
	
}