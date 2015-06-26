package de.det.d3t.controller;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.det.d3t.TextureFactory;
import de.det.d3t.model.AntiGravityTower;
import de.det.d3t.model.AoeTower;
import de.det.d3t.model.SingleShotTower;
import de.det.d3t.model.Tower;

public class BuildingController {

	private ArrayList<TowerDescription> towerDescList = new ArrayList<>();
	private HashMap<Class<? extends Tower>, TowerDescription> towerToDescMap = new HashMap<>();

	public BuildingController() {

		TowerDescription current;

		current = new TowerDescription("Anti Gravity", "Will pull stuff",
				TextureFactory.getTexture("labe_tower2"));
		towerDescList.add(current);
		towerToDescMap.put(AntiGravityTower.class, current);

		
		current = new TowerDescription("AOE", "Will push more stuff",
				TextureFactory.getTexture("labe_tower2"));
		towerDescList.add(current);
		towerToDescMap.put(AoeTower.class, current);

		current = new TowerDescription("Single Shot", "Will push 1 stuff",
				TextureFactory.getTexture("labe_tower2"));
		towerDescList.add(current);
		towerToDescMap.put(SingleShotTower.class, current);

	}

	public ArrayList<TowerDescription> getTowerDescList() {
		return towerDescList;
	}

	public class TowerDescription implements InputProcessor {

		public TowerDescription(String name, String tooltip, Texture texture) {
			super();
			this.name = name;
			this.tooltip = tooltip;
			this.image = new Image(texture);
		}

		public String name;
		public String tooltip;
		public Image image;
		
		private boolean isHoveredOver = false;

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
			return false;
		}

		@Override
		public boolean touchDragged(int screenX, int screenY, int pointer) {
			return false;
		}

		@Override
		public boolean mouseMoved(int screenX, int screenY) {
			Vector2 stageCoords = image.getStage().screenToStageCoordinates(
					new Vector2(screenX, screenY));
			float stagex = stageCoords.x;
			float stagey = stageCoords.y;
			
			if (stagex > image.getX()
					&& stagex < (image.getX() + image.getWidth() * image.getScaleX())
					&& stagey > image.getY()
					&& stagey < (image.getY() + image.getHeight() * image.getScaleY())) {
				
				if(isHoveredOver == false){
					System.out.println("Du bist mit der Maus auf dem Symbol für " + name);
					isHoveredOver = true;
				}
				
				return true;
			}
			
			isHoveredOver = false;
			return false;
		}

		@Override
		public boolean scrolled(int amount) {
			return false;
		}

	}

}
