package de.det.d3t.frame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.TileMapIntersectionDetector;
import de.det.d3t.controller.CameraInputController;
import de.det.d3t.controller.LevelController;
import de.det.d3t.model.AntiGravityTower;
import de.det.d3t.model.DummyTower;
import de.det.d3t.model.Enemy;
import de.det.d3t.model.Entity;
import de.det.d3t.model.MagnetTower;
import de.det.d3t.model.SingleShotTower;
import de.det.d3t.model.Tower;
import de.det.d3t.util.RadialSprite;
import de.det.d3t.util.Screenshooter;


public class GameFrame implements Screen {
	private Stage stage;
	private Stage ui;
	private Stage escMenuStage;
	private StretchViewport stageViewport;
	private StretchViewport uiViewport;
	private StretchViewport escViewport;
	private OrthographicCamera stageCamera;
	private OrthographicCamera uiCamera;
	private OrthographicCamera escCamera;
	private OrthogonalTiledMapRenderer tileMapRenderer;
	private InputMultiplexer inputMultiplexer;
	private TileMapIntersectionDetector lavaDetector;
	private FPSLogger fpsLogger;
	
	private Game game;
	private LevelController levelController;
	
	private boolean escMenuShowing = false;
	private boolean escReleased = true;
	
	private float width;
	private float height;
	
	
	public GameFrame(Game game){
		this.game = game;
		TextureFactory.loadAllGameRessources();
		setupStage();
		setupUI();
		setupEscMenuStage();
		setupTilemap();
		setupLevels();
		manageInputs();
		fpsLogger = new FPSLogger();
		
		width = stageViewport.getWorldWidth();
		height = stageViewport.getWorldHeight();
		
		//teststuff
		Texture texture = new Texture("badlogic.jpg");
		Image i = new Image(texture);
		i.setBounds(0, 0, 2000, 2000);
		ui.addActor(i);
		i = new Image(texture);
		i.setBounds(0, 0, 2000, 2000);
		i.rotateBy(180);
		ui.addActor(i);
		
		
		
		//UI
		Image uiback = new Image(TextureFactory.getTexture("uiback"));
		//Pixmap map = new Pixmap(new FileHandle("textures/ui/ingame/uiBack.png"));
//		map.getPixel(x, y)
//				uiCamera.unproject(screenCoords)
		
		uiback.setBounds(0,0,width, height);
		ui.addActor(uiback);
		
		
		stage.addActor(new Enemy(0, 4500, 1, true));
		stage.addActor(new SingleShotTower(2000, 4500, 2));
		stage.addActor(new DummyTower(3000,4500,2));
		stage.addActor(new AntiGravityTower(2500,6000,2));
		stage.addActor(new MagnetTower(3000,6000,2));
		for(int j = 1; j <= 100; j++){
			float x = (float) (Math.random() * Settings.viewportWidth);
			float y = (float) (Math.random() * Settings.viewportHeight);
			stage.addActor(new Enemy(x, y, 1, true));
//			x = (float) (Math.random() * Settings.viewportWidth);
//			y = (float) (Math.random() * Settings.viewportHeight);
//			stage.addActor(new Tower(x, y, 2));
//			if(lavaDetector.hasIntersectAt(x, y)){
//				i = new Image(texture);
//				i.setBounds(x, y, 10, 10);
//				stage.addActor(i);
//			}
		}
		//new RadialSprite(new TextureRegion(TextureFactory.getTexture("basic")));
	}
	
	private void setupLevels() {
		levelController = new LevelController();
		levelController.loadLevelsFromFile();
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
	
	public void setupEscMenuStage(){
		escCamera = new OrthographicCamera();
		escCamera.zoom = 1f;
		escViewport = new StretchViewport(Settings.viewportWidth, Settings.viewportHeight, escCamera);
		escMenuStage = new Stage(escViewport);
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
		inputMultiplexer.addProcessor(escMenuStage);
		Gdx.input.setInputProcessor(inputMultiplexer);
	}
	

	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
		stageViewport.update(width, height);
		uiViewport.update(width,height);
		escViewport.update(width,height);
		
		this.width = stageViewport.getWorldWidth();
		this.height = stageViewport.getWorldHeight();
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
		
		if(escReleased == true && Gdx.input.isKeyPressed(Keys.ESCAPE)){
			escMenuShowing = !escMenuShowing;
			System.out.println("EscMenuShowing: " + escMenuShowing);
			escReleased = false;
		}
		if(escReleased == false){
			if(!Gdx.input.isKeyPressed(Keys.ESCAPE)){
				escReleased = true;
			}
		}
		
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stageCamera.update();
		tileMapRenderer.setView(stageCamera);
		tileMapRenderer.render();
		stage.act(Gdx.graphics.getDeltaTime());
		Entity.checkCollisions();
		stage.draw();
		ui.act(Gdx.graphics.getDeltaTime());;
		ui.draw();
		/*ui.getBatch().begin();
		Image i =  new Image(TextureFactory.getTexture("hpbarback"));
		i.setBounds(1000, 1000, 4000, 4000);
		ui.addActor(i);
		RadialSprite sp = new RadialSprite(new TextureRegion(TextureFactory.getTexture("hpbar")));
		sp.setColor(new Color(0, 1, 0, 1));
		sp.draw(ui.getBatch(), 1000, 1000, 4000, 4000, 80f);
		ui.getBatch().end();*/
		if(escMenuShowing){
			escMenuStage.act(Gdx.graphics.getDeltaTime());
			escMenuStage.draw();
			inputMultiplexer = new InputMultiplexer();
			inputMultiplexer.addProcessor(escMenuStage);
			Gdx.input.setInputProcessor(inputMultiplexer);
			//TODO: stop enemies from moving
		}
		else{
			if(inputMultiplexer.getProcessors().size == 1){
				manageInputs();
			}
		}
		//fpsLogger.log();
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
