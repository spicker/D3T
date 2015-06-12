package de.det.d3t.frame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
	
	
	private TextureAtlas buttonAtlas;
	private Skin skin;
	private TextButtonStyle textButtonStyle;
	private Button startGameButton;
	private BitmapFont font;
	
	
	private int width;
	private int height;
	
	private Game game;
	
	
	public MenuFrame(Game game){
		this.game = game;
		TextureFactory.loadAllMenuRessources();
		setupStage();
		setupUI();
		manageInputs();
		fpsLogger = new FPSLogger();
		
		
		
		//////////UI/////////
        font = TextureFactory.getFont("vr");
        
       // skin = new Skin();
		//buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/button.pack"));
	    //skin.addRegions(buttonAtlas);
	   // skin = new Skin(Gdx.files.internal("uiskin.json"));
	    
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("ingameMenuButton")));
		textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("ingameMenuButtonDown")));
		textButtonStyle.font = font;
		textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(TextureFactory.getTexture("ingameMenuButtonOver")));
	    //textButtonStyle.checked = skin.getDrawable("checked-button");
	    startGameButton = new TextButton("Spiel starten", textButtonStyle);
	    startGameButton.setBounds(stageViewport.getWorldWidth()/2 - 1650, stageViewport.getWorldHeight()/2 - 450, 3300, 900);
	    startGameButton.addListener(this);
		ui.addActor(startGameButton);
		//////////UI/////////
		
		/////////Stage//////////
		//stage.addActor(new Enemy(9000, 3180, 2));
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
		inputMultiplexer.addProcessor(new CameraInputController(stageCamera)); //TODO Add some real controller here
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void resize(int width, int height) {
		stageViewport.update(width, height);
		
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
		//tileMapRenderer.setView(stageCamera);
		//tileMapRenderer.render();
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
		return false;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
		if(event.getListenerActor() == startGameButton){
            game.setScreen(new GameFrame(game));
		}

	}
	
	
	
	
	
	
}