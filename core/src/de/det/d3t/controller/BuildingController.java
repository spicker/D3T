package de.det.d3t.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import de.det.d3t.CollisionFactory;
import de.det.d3t.TextureFactory;
import de.det.d3t.model.AntiGravityTower;
import de.det.d3t.model.AoeTower;
import de.det.d3t.model.BillardTower;
import de.det.d3t.model.Enemy;
import de.det.d3t.model.Level;
import de.det.d3t.model.MagnetTower;
import de.det.d3t.model.SingleShotTower;
import de.det.d3t.model.SlowTower;
import de.det.d3t.model.TeleportTower;
import de.det.d3t.model.Tower;

public class BuildingController {

	private ArrayList<TowerDescription> towerDescList = new ArrayList<>();
	private HashMap<TowerDescription, Class<? extends Tower>> descToTowerMap = new HashMap<>();
	private LabelStyle labelstyle;
	private boolean buildingSelected = false;
	private Tower buildTower = null;
	private TowerDescription buildDesc = null;
	private Stage gameStage, uiStage;
	private LevelController levelController;

	public BuildingController(Stage gameStage, Stage uiStage,
			LevelController levelController) {

		this.levelController = levelController;
		this.gameStage = gameStage;
		this.uiStage = uiStage;

		labelstyle = new LabelStyle();
		labelstyle.font = TextureFactory.getFont("emmett", 200, Color.YELLOW);

		TowerDescription current;

		current = new TowerDescription("Anti Gravity", "Stößt Gegner ab", 5,
				TextureFactory.getTexture("antiGravityIcon"));
		current.setImageBounds(35, 35);
		towerDescList.add(current);
		descToTowerMap.put(current, AntiGravityTower.class);

		current = new TowerDescription("AOE", "Greift mehrere Gegner an", 5,
				TextureFactory.getTexture("singleShotIcon"));
		current.setImageBounds(35, 35);
		towerDescList.add(current);
		descToTowerMap.put(current, AoeTower.class);

		current = new TowerDescription("Single Shot",
				"Greif einen einzelnen Gegner an", 5,
				TextureFactory.getTexture("singleShotIcon"));
		current.setImageBounds(35, 35);
		towerDescList.add(current);
		descToTowerMap.put(current, SingleShotTower.class);

		current = new TowerDescription("Billard",
				"Verschießt zurückstoßende Kugel", 5,
				TextureFactory.getTexture("singleShotIcon"));
		current.setImageBounds(35, 35);
		towerDescList.add(current);
		descToTowerMap.put(current, BillardTower.class);

		current = new TowerDescription("Teleport", "Teleportiert Gegner", 5,
				TextureFactory.getTexture("singleShotIcon"));
		current.setImageBounds(35, 35);
		towerDescList.add(current);
		descToTowerMap.put(current, TeleportTower.class);

		current = new TowerDescription("Magnet", "Zieht Gegner an", 5,
				TextureFactory.getTexture("singleShotIcon"));
		current.setImageBounds(35, 35);
		towerDescList.add(current);
		descToTowerMap.put(current, MagnetTower.class);

		current = new TowerDescription("Slow", "Verlangsamt Gegner", 5,
				TextureFactory.getTexture("singleShotIcon"));
		current.setImageBounds(35, 35);
		towerDescList.add(current);
		descToTowerMap.put(current, SlowTower.class);

	}

	public ArrayList<TowerDescription> getTowerDescList() {
		return towerDescList;
	}

	public class TowerDescription implements InputProcessor {

		public TowerDescription(String name, String tooltip, int cost,
				Texture texture) {
			super();
			this.name = name;
			this.tooltip = tooltip;
			this.image = new Image(texture);
			this.texture = texture;
			this.cost = cost;
			label = new Label(name + "\n" + "Cost: " + cost + "\n" + tooltip, labelstyle);
		}

		public String name;
		public String tooltip;
		public Label label;
		public Texture texture;
		public Image image;
		public int cost;

		private boolean isHoveredOver = false;
		private Image hoverImage = null;
		private Label hoverLabel = null;

		@Override
		public boolean keyDown(int keycode) {
			return false;
		}

		@Override
		public boolean keyUp(int keycode) {
			return false;
		}

		@Override
		public boolean keyTyped(char character) {
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer,
				int button) {
			return false;
		}

		@Override
		public boolean touchUp(int screenX, int screenY, int pointer, int button) {
			if (buildingSelected && button != Buttons.LEFT) {
				buildingSelected = false;
				buildTower.remove();
				buildTower = null;
				buildDesc = null;

				return true;
			}

			if (button == Buttons.LEFT && buildingSelected
					&& !hasCollisionWithGameObjects(buildTower)) {

				Vector2 target = gameStage
						.screenToStageCoordinates(new Vector2(screenX, screenY));
				
				if(levelController.getCurrentLevel().getGold() < cost){
					
					NotEnoughGoldLabel label = new NotEnoughGoldLabel();
					gameStage.addActor(label);
					label.setX(target.x);
					label.setY(target.y);
					
					return true;
				}
				
				buildingSelected = false;
				buildTower.remove();
				buildTower = null;

				Class<? extends Tower> towerClass = descToTowerMap
						.get(buildDesc);
				try {
					Tower newTower = towerClass.getDeclaredConstructor(
							float.class, float.class, float.class).newInstance(
							target.x, target.y, 2);
					newTower.setX(target.x - (newTower.getWidth() / 2));
					newTower.setY(target.y - (newTower.getHeight() / 2));
					newTower.renewPositions(newTower.getX(), newTower.getY());
					gameStage.addActor(newTower);
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}

				buildDesc = null;
				
				Level currentLevel = levelController.getCurrentLevel();
				currentLevel.setGold(currentLevel.getGold() - cost);
				
				return true;
			}

			if (button == Buttons.LEFT && isMouseOverMe(screenX, screenY)) {

				Vector2 target = new Vector2(screenX, screenY);

				buildingSelected = true;
				buildDesc = this;

				Class<? extends Tower> towerClass = descToTowerMap
						.get(buildDesc);
				try {
					buildTower = towerClass.getDeclaredConstructor(float.class,
							float.class, float.class).newInstance(target.x,
							target.y, 2);
				} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					System.err.println(e.getMessage());
					e.printStackTrace();
				}

				buildTower.getColor().a = 0.5f;
				buildTower.setActive(false);
				buildTower.removeHPbar();
				gameStage.addActor(buildTower);

				return true;
			}

			buildingSelected = false;
			buildDesc = null;
			if (buildTower != null) {
				buildTower.remove();
				buildTower = null;
			}
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		private boolean hasCollisionWithGameObjects(Tower tower) {
			for (Actor curActor : gameStage.getActors()) {

				if (curActor != buildTower) {
					if (curActor instanceof Enemy) {
						Enemy curEnemy = (Enemy) curActor;
						if (CollisionFactory.checkCollision(buildTower,
								curEnemy)) {
							return true;
						}
					} else if (curActor instanceof Tower) {
						Tower curTower = (Tower) curActor;
						if (CollisionFactory.checkCollision(buildTower,
								curTower)) {
							return true;
						}
					}
				}

			}

			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			if (buildingSelected) {

				Vector2 target = gameStage
						.screenToStageCoordinates(new Vector2(screenX, screenY));

				buildTower.setX(target.x - (buildTower.getWidth() / 2));
				buildTower.setY(target.y - (buildTower.getHeight() / 2));

				boolean coll = hasCollisionWithGameObjects(buildTower);

				if (coll) {
					buildTower.getColor().r = 1f;
					buildTower.getColor().g = 0.1f;
					buildTower.getColor().b = 0.1f;
				} else {
					buildTower.getColor().r = 0.1f;
					buildTower.getColor().g = 1f;
					buildTower.getColor().b = 0.1f;
				}

				return true;
			}

			if (isMouseOverMe(screenX, screenY)) {

				if (isHoveredOver == false) {
					isHoveredOver = true;

					hoverImage = new Image(
							TextureFactory.getTexture("ingameButton1"));
					hoverImage.setX(image.getX()
							+ (image.getWidth() * image.getScaleX() / 2));
					hoverImage.setY(image.getY()
							+ (image.getHeight() * image.getScaleY() / 2));
					hoverImage.setScale(7);

					if (hoverImage.getX()
							+ (hoverImage.getWidth() * hoverImage.getScaleX()) > uiStage
								.getWidth()) {
						hoverImage.setX(hoverImage.getX()
								- (hoverImage.getWidth() * hoverImage
										.getScaleX()));
					}

					hoverLabel = label;
					hoverLabel.setX(hoverImage.getX() + 100);
					hoverLabel.setY(hoverImage.getY()
							+ (hoverImage.getHeight() * hoverImage.getScaleY())
							- hoverLabel.getHeight() - 50);

					uiStage.addActor(hoverImage);
					uiStage.addActor(hoverLabel);

				}

				return false;
			}

			isHoveredOver = false;
			if (hoverImage != null) {
				hoverImage.remove();
				hoverImage = null;
				hoverLabel.remove();
				hoverLabel = null;
			}
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}

		public void setImageBounds(int width, int height) {
			this.image.setWidth(width);
			this.image.setHeight(height);
		}

		private boolean isMouseOverMe(float screenX, float screenY) {
			Vector2 stageCoords = uiStage.screenToStageCoordinates(new Vector2(
					screenX, screenY));
			float stagex = stageCoords.x;
			float stagey = stageCoords.y;

			if (stagex > image.getX()
					&& stagex < (image.getX() + image.getWidth()
							* image.getScaleX())
					&& stagey > image.getY()
					&& stagey < (image.getY() + image.getHeight()
							* image.getScaleY()))
				return true;

			return false;
		}

	}
	
	private class NotEnoughGoldLabel extends Label{
		
		private static final float TIMELIMIT = 3;
		private float lived = 0;
		
		public NotEnoughGoldLabel(){
			super("Nicht genügend Gold", labelstyle);
		}
		
		@Override
		public void act(float delta) {
			lived += delta;
			
			if(lived > TIMELIMIT){
				remove();
				return;
			}
			
			setY(getY()+5);
			super.act(delta);
		}
	}
	
}
