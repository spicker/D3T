package de.det.d3t.frame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
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
import de.det.d3t.TileMapIntersectionDetector;
import de.det.d3t.controller.BuildingController;
import de.det.d3t.controller.CameraInputController;
import de.det.d3t.controller.LevelController;
import de.det.d3t.controller.UIController;
import de.det.d3t.controller.BuildingController.TowerDescription;
import de.det.d3t.frame.Dialogs.IngameDialog;
import de.det.d3t.model.AntiGravityTower;
import de.det.d3t.model.AoeTower;
import de.det.d3t.model.BillardTower;
import de.det.d3t.model.Connection;
import de.det.d3t.model.DummyTower;
import de.det.d3t.model.Enemy;
import de.det.d3t.model.Entity;
import de.det.d3t.model.LineSegment;
import de.det.d3t.model.MagnetTower;
import de.det.d3t.model.SingleShotTower;

public class GameFrame extends InputListener implements Screen {
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
	private BuildingController buildingController;

	private boolean escMenuShowing = false;
	private boolean escReleased = true;

	private float width;
	private float height;

	private Image escMenu;
	private Image uiback;
	private Image uiShadow;
	private BitmapFont font;
	private TextButtonStyle textButtonStyle;
	private Button ingameButtonMenu;
	private Button ingameButtonRestart;
	private Button ingameButtonOptions;
	private Button ingameButtonHelp;
	private Image ingameGold;
	private Image ingameTime;
	private Image ingameLife;
	private Label ingameLifeLabel;
	private Label ingameGoldLabel;
	private Label ingameTimeLabel;

	private Button escButtonCloseGame;
	private Button escButtonMainMenu;
	private Button escButtonLevelSelect;
	private Button escButtonSaveGame;
	private Button escButtonLoadGame;

	private Label buildTower;
	private LabelStyle ls;

	private Music bgmMusic;
	private Sound buttonClickSound;
	private int selectedLevel;

	private TimeKeeper timekeeper;

	public GameFrame(Game game, int selectedLevel) {
		this.game = game;
		this.selectedLevel = selectedLevel;
		TextureFactory.loadAllGameRessources();
		setupStage();
		setupUI();
		setupEscMenuStage();
		setupLevels();
		setupTilemap();
		manageInputs();
		fpsLogger = new FPSLogger();
		timekeeper = new TimeKeeper();



		width = stageViewport.getWorldWidth();
		height = stageViewport.getWorldHeight();

		bgmMusic = TextureFactory.getMusic("dubstepBgm");
		bgmMusic.setLooping(true);
		bgmMusic.setVolume(Settings.getBgm());
		bgmMusic.play();
		buttonClickSound = TextureFactory.getSound("buttonClick");

		// teststuff
		/*
		 * Texture texture = new Texture("badlogic.jpg"); Image i = new
		 * Image(texture); i.setBounds(0, 0, 2000, 2000); ui.addActor(i); i =
		 * new Image(texture); i.setBounds(0, 0, 2000, 2000); i.rotateBy(180);
		 * ui.addActor(i);
		 */

		// UI
		uiback = new Image(TextureFactory.getTexture("uiNew"));
		uiback.setBounds(0, 0, width, height);
		// uiShadow = new Image(TextureFactory.getTexture("uiskin2Shadow"));
		// uiShadow.setBounds(0,0,width, height);

		font = TextureFactory.getFont("emmett", 200, Color.valueOf("DDDCE0"));
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(
				TextureFactory.getTexture("blueButtonNew")));
		textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(
				TextureFactory.getTexture("blueButtonNew_down")));
		textButtonStyle.font = font;
		textButtonStyle.over = new TextureRegionDrawable(new TextureRegion(
				TextureFactory.getTexture("blueButtonNew_over")));

		ingameButtonMenu = new TextButton("Menü [ESC]", textButtonStyle);
		ingameButtonMenu.setBounds(width / 2 - (1900 / 2), height / 2 + height
				/ 3 + height / 10, 1900, 500);
		ingameButtonMenu.addListener(this);

		ingameButtonOptions = new TextButton("Optionen [F11]", textButtonStyle);
		ingameButtonOptions.setBounds(width / 2 - (1900 / 2) - 2000, height / 2
				+ height / 3 + height / 10, 1900, 500);
		ingameButtonOptions.addListener(this);

		ingameButtonRestart = new TextButton("Neu Starten [F10]",
				textButtonStyle);
		ingameButtonRestart.setBounds(width / 2 - (1900 / 2) - 4000, height / 2
				+ height / 3 + height / 10, 1900, 500);
		ingameButtonRestart.addListener(this);

		ingameButtonHelp = new TextButton("Hilfe [F9]", textButtonStyle);
		ingameButtonHelp.setBounds(width / 2 - (1900 / 2) - 6000, height / 2
				+ height / 3 + height / 10, 1900, 500);
		ingameButtonHelp.addListener(this);

		ingameGold = new Image(TextureFactory.getTexture("gold"));
		ingameGold.setBounds(width / 2 - (1900 / 2) + 3000, height / 2 + height
				/ 3 + height / 10 + height / 120, 300, 300);

		ingameTime = new Image(TextureFactory.getTexture("time"));
		ingameTime.setBounds(width / 2 - (1900 / 2) + 4000, height / 2 + height
				/ 3 + height / 10 + height / 120, 300, 300);
		
		ingameLife = new Image(TextureFactory.getTexture("heart"));
		ingameLife.setBounds(width / 2 - (1900 / 2) + 2000, height / 2 + height
				/ 3 + height / 10 + height / 120, 300, 300);

		ls = new LabelStyle();
		ls.font = TextureFactory.getFont("emmett", 200, Color.YELLOW);

		ingameLifeLabel = new Label("30", ls);
		ingameLifeLabel.setBounds(width / 2 - (1900 / 2) + 2400, height / 2
				+ height / 3 + height / 10, 1000, 400);
		
		ingameGoldLabel = new Label("143", ls);
		ingameGoldLabel.setBounds(width / 2 - (1900 / 2) + 3400, height / 2
				+ height / 3 + height / 10, 1000, 400);

		ls.font = TextureFactory
				.getFont("emmett", 200, Color.valueOf("DDDCE0"));

		ingameTimeLabel = new Label("00:00:00", ls);
		ingameTimeLabel.setBounds(width / 2 - (1900 / 2) + 4400, height / 2
				+ height / 3 + height / 10, 1000, 400);

		ui.addActor(uiback);
		// ui.addActor(uiShadow);
		ui.addActor(ingameButtonMenu);
		ui.addActor(ingameButtonOptions);
		ui.addActor(ingameButtonRestart);
		ui.addActor(ingameButtonHelp);
		ui.addActor(ingameGold);
		ui.addActor(ingameTime);
		ui.addActor(ingameLife);
		ui.addActor(ingameGoldLabel);
		ui.addActor(ingameTimeLabel);
		ui.addActor(ingameLifeLabel);

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				Image img = new Image(
						TextureFactory.getTexture("iconBackground"));
				img.setBounds(width / 2 + width / 4 + width / 12 + width / 30
						- width / 450 + (j * 700), height / 10 + height / 180
						+ (i * 700), 550, 550);
				ui.addActor(img);
			}
		}

		setupBuilding();

		ls.font = TextureFactory
				.getFont("emmett", 320, Color.valueOf("DDDCE0"));

		buildTower = new Label("Turm bauen", ls);
		buildTower.setBounds(width / 2 + width / 4 + width / 8 - width / 150,
				height / 2 - height / 20, 1200, 500);

		ui.addActor(buildTower);

		// escMenuStage
		escMenu = new Image(TextureFactory.getTexture("escMenuNew"));
		escMenu.setBounds(width / 2 - (4000 / 2), height / 2 - (7000 / 2),
				5000, 7000);

		textButtonStyle.font = TextureFactory.getFont("emmett", 270,
				Color.valueOf("DDDCE0"));

		escButtonCloseGame = new TextButton("Spiel Beenden", textButtonStyle);
		escButtonCloseGame.setBounds(escMenu.getX() + escMenu.getWidth() / 2
				- (2500 / 2), escMenu.getY() + height / 9, 2500, 650);
		escButtonCloseGame.addListener(this);

		escButtonSaveGame = new TextButton("Spiel Speichern", textButtonStyle);
		escButtonSaveGame.setBounds(escMenu.getX() + escMenu.getWidth() / 2
				- (2500 / 2), escMenu.getY() + height / 9 + height / 8, 2500,
				650);
		escButtonSaveGame.addListener(this);

		escButtonLoadGame = new TextButton("Spiel Laden", textButtonStyle);
		escButtonLoadGame.setBounds(escMenu.getX() + escMenu.getWidth() / 2
				- (2500 / 2), escMenu.getY() + height / 9 + height / 8 + height
				/ 8, 2500, 650);
		escButtonLoadGame.addListener(this);

		escButtonLevelSelect = new TextButton("Level Auswahl", textButtonStyle);
		escButtonLevelSelect.setBounds(escMenu.getX() + escMenu.getWidth() / 2
				- (2500 / 2), escMenu.getY() + height / 9 + height / 8 + height
				/ 8 + height / 8, 2500, 650);
		escButtonLevelSelect.addListener(this);

		escButtonMainMenu = new TextButton("Hauptmenü", textButtonStyle);
		escButtonMainMenu.setBounds(escMenu.getX() + escMenu.getWidth() / 2
				- (2500 / 2), escMenu.getY() + height / 9 + height / 8 + height
				/ 8 + height / 8 + height / 8, 2500, 650);
		escButtonMainMenu.addListener(this);

		escMenuStage.addActor(escMenu);
		escMenuStage.addActor(escButtonCloseGame);
		escMenuStage.addActor(escButtonSaveGame);
		escMenuStage.addActor(escButtonLoadGame);
		escMenuStage.addActor(escButtonLevelSelect);
		escMenuStage.addActor(escButtonMainMenu);

		// stage.addActor(new BillardTower(26000, 25000, 2));
		// stage.addActor(new AntiGravityTower(2500,4500,2));
		// stage.addActor(new AoeTower(3500,4500,2));

		// stage.addActor(new DummyTower(3000,4500,2));
		// stage.addActor(new AntiGravityTower(2500,4500,2));
		// stage.addActor(new MagnetTower(6000,6000,2));
		/*
		 * for(int j = 1; j <= 100000; j++){ float x = (float) (Math.random() *
		 * Settings.viewportWidth); float y = (float) (Math.random() *
		 * Settings.viewportHeight); x = (float) (Math.random() *
		 * Settings.viewportWidth); y = (float) (Math.random() *
		 * Settings.viewportHeight); if(lavaDetector.hasIntersectAt(x, y)){
		 * Image i = new Image(TextureFactory.getTexture("basic"));
		 * i.setBounds(x, y, 10, 10); stage.addActor(i); } }
		 */
		// new RadialSprite(new
		// TextureRegion(TextureFactory.getTexture("basic")));

		levelController.startGame(stage);

	}

	private void setupLevels() {
		levelController = new LevelController(this);
		levelController.loadLevelsFromFile();
		switch (selectedLevel) {
		case 1:
			levelController.setCurrentLevel(0);
			break;
		case 2:
			levelController.setCurrentLevel(1);
			break;
		case 3:
			levelController.setCurrentLevel(2);
			break;
		case 4:
			levelController.setCurrentLevel(3);
			break;
		case 5:
			levelController.setCurrentLevel(4);
			break;
		case 6:
			levelController.setCurrentLevel(5);
			break;
		case 7:
			levelController.setCurrentLevel(6);
			break;
		}
	}

	public void setupStage() {
		stageCamera = new OrthographicCamera();
		stageCamera.zoom = 0.3f;
		stageViewport = new StretchViewport(Settings.viewportWidth,
				Settings.viewportHeight, stageCamera);
		stage = new Stage(stageViewport);
	}

	public void setupUI() {
		uiCamera = new OrthographicCamera();
		uiCamera.zoom = 1f;
		uiViewport = new StretchViewport(Settings.viewportWidth,
				Settings.viewportHeight, uiCamera);
		ui = new Stage(uiViewport);
	}

	public void setupEscMenuStage() {
		escCamera = new OrthographicCamera();
		escCamera.zoom = 1f;
		escViewport = new StretchViewport(Settings.viewportWidth,
				Settings.viewportHeight, escCamera);
		escMenuStage = new Stage(escViewport);
	}

	public void setupTilemap() {
		TiledMap map = levelController.getCurrentLevel().getTiledMap();
		TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
		lavaDetector = new TileMapIntersectionDetector(layer);
		tileMapRenderer = new OrthogonalTiledMapRenderer(map,
				Settings.viewportHeight
						/ (layer.getHeight() * layer.getTileHeight()));
	}

	public void setupBuilding() {
		buildingController = new BuildingController(stage, ui, levelController);
		int i = 0;
		int j = 0;

		for (BuildingController.TowerDescription towerDesc : buildingController
				.getTowerDescList()) {

			towerDesc.image.setX(width / 2 + width / 4 + width / 12 + width
					/ 28 + (i * 700));
			towerDesc.image.setY(height / 10 + height / 60 + (j * 700));
			towerDesc.image.scaleBy(10);

			inputMultiplexer.addProcessor(towerDesc);
			ui.addActor(towerDesc.image);

			i++;
			if (i == 3) {
				i = 0;
				j++;
			}

		}
	}

	public void manageInputs() {
		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new UIController());
		inputMultiplexer.addProcessor(ui);
		inputMultiplexer.addProcessor(new CameraInputController(stageCamera)); // TODO
																				// Add
																				// some
																				// real
																				// controller
																				// here
		inputMultiplexer.addProcessor(stage);

		if (buildingController != null) {
			for (TowerDescription towerDesc : buildingController
					.getTowerDescList()) {
				inputMultiplexer.addProcessor(towerDesc);
			}
		}

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	@Override
	public void dispose() {
	}

	@Override
	public void resize(int width, int height) {
		stageViewport.update(width, height);
		uiViewport.update(width, height);
		escViewport.update(width, height);

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

		if (escReleased == true && Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			escMenuShowing = !escMenuShowing;
			System.out.println("EscMenuShowing: " + escMenuShowing);
			escReleased = false;

			if (escMenuShowing) {
				inputMultiplexer = new InputMultiplexer();
				inputMultiplexer.addProcessor(escMenuStage);
				Gdx.input.setInputProcessor(inputMultiplexer);
				buildingController.resetBuilding();
			} else {
				manageInputs();
			}
		}
		if (escReleased == false) {
			if (!Gdx.input.isKeyPressed(Keys.ESCAPE)) {
				escReleased = true;
			}
		}
		/**
		 * if(Math.random() < 0.01f){ stage.addActor(new Enemy(0, 4500, 1,
		 * true)); }
		 */
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stageCamera.update();
		tileMapRenderer.setView(stageCamera);
		tileMapRenderer.render();
		if (!escMenuShowing) {
			stage.act(Gdx.graphics.getDeltaTime());
			Entity.checkCollisions();
			Enemy.checkForIntersection(lavaDetector,
					Gdx.graphics.getDeltaTime());
			ui.act(Gdx.graphics.getDeltaTime());
			;
			levelController.update(delta);
			timekeeper.update(delta);
			ingameTimeLabel.setText(timekeeper.timeAsString());
		}

		ingameGoldLabel.setText(levelController.getGold() + "");

		stage.draw();
		ui.draw();
		/*
		 * ui.getBatch().begin(); Image i = new
		 * Image(TextureFactory.getTexture("hpbarback")); i.setBounds(1000,
		 * 1000, 4000, 4000); ui.addActor(i); RadialSprite sp = new
		 * RadialSprite(new TextureRegion(TextureFactory.getTexture("hpbar")));
		 * sp.setColor(new Color(0, 1, 0, 1)); sp.draw(ui.getBatch(), 1000,
		 * 1000, 4000, 4000, 80f); ui.getBatch().end();
		 */
		if (escMenuShowing) {
			escMenuStage.act(Gdx.graphics.getDeltaTime());
			escMenuStage.draw();
		} else {
			if (inputMultiplexer.getProcessors().size == 1) {
				manageInputs();
			}
		}
		// fpsLogger.log();

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean touchDown(InputEvent event, float x, float y, int pointer,
			int button) {

		if (event.getListenerActor().equals(ingameButtonMenu)) {
			buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if (event.getListenerActor().equals(ingameButtonHelp)) {
			buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if (event.getListenerActor().equals(ingameButtonOptions)) {
			buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if (event.getListenerActor().equals(ingameButtonRestart)) {
			buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if (event.getListenerActor().equals(escButtonCloseGame)) {
			if (escMenuShowing)
				buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if (event.getListenerActor().equals(escButtonMainMenu)) {
			if (escMenuShowing)
				buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if (event.getListenerActor().equals(escButtonLoadGame)) {
			if (escMenuShowing)
				buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if (event.getListenerActor().equals(escButtonSaveGame)) {
			if (escMenuShowing)
				buttonClickSound.play(Settings.getSfx());
			return true;
		}
		if (event.getListenerActor().equals(escButtonLevelSelect)) {
			if (escMenuShowing)
				buttonClickSound.play(Settings.getSfx());
			return true;
		}

		return false;

	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer,
			int button) {
		if (event.getListenerActor().equals(ingameButtonMenu)) {
			escMenuShowing = !escMenuShowing;
			if (escMenuShowing) {
				inputMultiplexer = new InputMultiplexer();
				inputMultiplexer.addProcessor(escMenuStage);
				Gdx.input.setInputProcessor(inputMultiplexer);
				buildingController.resetBuilding();
			} else {
				manageInputs();
			}
			buildingController.resetBuilding();
		}
		if (event.getListenerActor().equals(ingameButtonHelp)) {
			buildingController.resetBuilding();
		}
		if (event.getListenerActor().equals(ingameButtonOptions)) {
			buildingController.resetBuilding();
		}
		if (event.getListenerActor().equals(ingameButtonRestart)) {
			buildingController.resetBuilding();
			levelController.resetLevel();
			timekeeper.seconds = 0f;
		}

		// ESC MENU
		if (event.getListenerActor().equals(escButtonCloseGame)) {
			// TODO: show dialog
			// "Unsaved progress won't be saved! Do you want to save your game before closing? "
			if (escMenuShowing)
				Gdx.app.exit();
		}

		if (event.getListenerActor().equals(escButtonMainMenu)) {
			// TODO: add dialog:
			// "You are going back to the main menu. Unsaved progress will be lost! Do you want to save before leaving the game to the Main Menu?"
			if (escMenuShowing) {
				game.setScreen(new MenuFrame(game));
				bgmMusic.stop();
			}

		}
		if (event.getListenerActor().equals(escButtonLoadGame)) {
			if (escMenuShowing) {

			}
		}
		if (event.getListenerActor().equals(escButtonSaveGame)) {
			if (escMenuShowing) {

			}

		}
		if (event.getListenerActor().equals(escButtonLevelSelect)) {
			if (escMenuShowing) {
				// TODO: add dialog:
				// "You are going back to the level Selection. Unsaved progress of the running level will be lost! Do you want to save before leaving the running level to the level selection?"
				game.setScreen(new SetupGameFrame(game,false));
				bgmMusic.stop();
			}
		}

	}

	@Override
	public void touchDragged(InputEvent event, float x, float y, int pointer) {
		/*
		 * Pixmap ui = new Pixmap(new
		 * FileHandle("textures/ui/ingame/uiNew.png")); float fx = ui.getWidth()
		 * / Gdx.graphics.getWidth(); float fy = ui.getHeight() /
		 * Gdx.graphics.getHeight();
		 * 
		 * Color newColor = new Color(ui.getPixel((int)(x/fx),(int)(y/fy)));
		 * System.out.println(newColor);
		 */
		super.touchDragged(event, x, y, pointer);
	}

	@Override
	public boolean mouseMoved(InputEvent event, float x, float y) {
		// System.out.println("X: " + x + " Y: " + y);
		return super.mouseMoved(event, x, y);
	}

	@Override
	public void enter(InputEvent event, float x, float y, int pointer,
			Actor fromActor) {
		// TODO Auto-generated method stub
		super.enter(event, x, y, pointer, fromActor);
	}

	@Override
	public void exit(InputEvent event, float x, float y, int pointer,
			Actor toActor) {
		// TODO Auto-generated method stub
		super.exit(event, x, y, pointer, toActor);
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

	private class TimeKeeper {

		float seconds = 0;

		public void update(float delta) {
			seconds += delta;
		}

		public String timeAsString() {
			String minutesString = String.valueOf((int) java.lang.Math
					.floor(seconds / 60));
			String secondsString;
			if (levelController.isBuildingPhase()) {
				secondsString = String.valueOf((int) java.lang.Math
						.floor(levelController.getCurrentLevel()
								.getInitialDelay() + 1 - seconds % 60));
			} else {
				secondsString = String.valueOf((int) java.lang.Math
						.floor(seconds
								% 60
								- levelController.getCurrentLevel()
										.getInitialDelay()));
			}
			if (minutesString.length() == 1) {
				minutesString = "0" + minutesString;
			}
			if (secondsString.length() == 1) {
				secondsString = "0" + secondsString;
			}

			if (levelController.isBuildingPhase() == false) {
				return minutesString + ":" + secondsString;
			} else {
				return "- " + minutesString + ":" + secondsString;
			}
		}
	}

	public void levelFinished() {
		bgmMusic.stop();
		IngameDialog idia = new IngameDialog(game, width, height, "Level Abgeschlossen",true,selectedLevel);
		ui.addActor(idia.getGroup());
		idia.showDialog();
	}
	
	public void levelLost(){
		bgmMusic.stop();
		IngameDialog idia = new IngameDialog(game, width, height, "Level verloren",false,selectedLevel);
		ui.addActor(idia.getGroup());
		idia.showDialog();
		
	
	}
	
	
}
