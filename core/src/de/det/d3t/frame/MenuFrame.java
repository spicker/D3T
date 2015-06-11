package de.det.d3t.frame;

import com.badlogic.gdx.ApplicationListener;
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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.TileMapIntersectionDetector;
import de.det.d3t.controller.CameraInputController;
import de.det.d3t.model.Enemy;

public class MenuFrame implements Screen {
	private Stage stage;
	private Stage ui;
	private StretchViewport stageViewport;
	private StretchViewport uiViewport;
	private OrthographicCamera stageCamera;
	private OrthographicCamera uiCamera;
	private OrthogonalTiledMapRenderer tileMapRenderer;
	private InputMultiplexer inputMultiplexer;
	private FPSLogger fpsLogger;
	
	
	private TextureAtlas buttonAtlas;
	private Skin skin;
	private TextButtonStyle textButtonStyle;
	private Button button;
	private BitmapFont font;
	
	
	public MenuFrame(){
		TextureFactory.loadAllGameTextures();
		setupStage();
		setupUI();
		setupTilemap();
		manageInputs();
		fpsLogger = new FPSLogger();
		//////////UI/////////
        font = new BitmapFont();
        skin = new Skin();
		buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/button.pack"));
	    skin.addRegions(buttonAtlas);
	   // skin = new Skin(Gdx.files.internal("uiskin.json"));
	    textButtonStyle = new TextButtonStyle();
	    textButtonStyle.font = font;
	    textButtonStyle.up = skin.getDrawable("buttonnormal");
	    textButtonStyle.down = skin.getDrawable("buttonpressed");
	    //textButtonStyle.checked = skin.getDrawable("checked-button");
	    button = new TextButton("Spiel starten", textButtonStyle);
	    button.setBounds(500, 500, 500, 500);
		ui.addActor(button);
		//////////UI/////////
		
		/////////Stage//////////
		stage.addActor(new Enemy(7000, 3180, 2));
		
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
	
	public void setupTilemap(){
		/*
		TiledMap map = new TmxMapLoader().load("tilemap/map.tmx");
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);
		tileMapRenderer = new OrthogonalTiledMapRenderer(map, Settings.viewportHeight / (layer.getHeight() * layer.getTileHeight()));
		*/
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
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stageCamera.update();
		//tileMapRenderer.setView(stageCamera);
		//tileMapRenderer.render();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		ui.act(Gdx.graphics.getDeltaTime());;
		ui.draw();
		fpsLogger.log();
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}