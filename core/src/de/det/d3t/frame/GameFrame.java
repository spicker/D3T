package de.det.d3t.frame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.TileMapIntersectionDetector;
import de.det.d3t.controller.CameraInputController;
import de.det.d3t.model.Enemy;



public class GameFrame implements Screen {
	
	
	public GameFrame(){
		TextureFactory.loadAllGameTextures();
		setupStage();
		setupUI();
		setupTilemap();
		manageInputs();
		fpsLogger = new FPSLogger();
		
		//teststuff
		Texture texture = new Texture("badlogic.jpg");
		Image i = new Image(texture);
		i.setBounds(0, 0, 2000, 2000);
		ui.addActor(i);
		stage.addActor(new Enemy(7000, 3180, 2));
		i = new Image(texture);
		i.setBounds(0, 0, 2000, 2000);
		i.rotateBy(180);
		ui.addActor(i);
		
		
		for(int j = 1; j < 1000; j++){
			float x = (float) (Math.random() * Settings.viewportWidth);
			float y = (float) (Math.random() * Settings.viewportHeight);
			stage.addActor(new Enemy(x, y, 1));
			if(lavaDetector.hasIntersectAt(x, y)){
				i = new Image(texture);
				i.setBounds(x, y, 10, 10);
				stage.addActor(i);
			}
		}
	}
	
	
	
	private Stage stage;
	private Stage ui;
	private StretchViewport stageViewport;
	private StretchViewport uiViewport;
	private OrthographicCamera stageCamera;
	private OrthographicCamera uiCamera;
	private OrthogonalTiledMapRenderer tileMapRenderer;
	private InputMultiplexer inputMultiplexer;
	private TileMapIntersectionDetector lavaDetector;
	private FPSLogger fpsLogger;
	
	
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
		TiledMap map = new TmxMapLoader().load("tilemap/map.tmx");
		TiledMapTileLayer layer = (TiledMapTileLayer)map.getLayers().get(1);
		lavaDetector = new TileMapIntersectionDetector(layer);
		tileMapRenderer = new OrthogonalTiledMapRenderer(map, Settings.viewportHeight / (layer.getHeight() * layer.getTileHeight()));
	}
	
	public void manageInputs(){
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(ui);
		inputMultiplexer.addProcessor(new CameraInputController(stageCamera)); //TODO Add some real controller here
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	

	@Override
	public void dispose() {
	}

	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stageCamera.update();
		tileMapRenderer.setView(stageCamera);
		tileMapRenderer.render();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		ui.act(Gdx.graphics.getDeltaTime());;
		ui.draw();
		fpsLogger.log();
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
