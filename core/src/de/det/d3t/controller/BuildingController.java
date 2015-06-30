package de.det.d3t.controller;

import java.util.ArrayList;
import java.util.HashMap;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

import de.det.d3t.TextureFactory;
import de.det.d3t.model.AntiGravityTower;
import de.det.d3t.model.AoeTower;
import de.det.d3t.model.SingleShotTower;
import de.det.d3t.model.Tower;

public class BuildingController {

	private ArrayList<TowerDescription> towerDescList = new ArrayList<>();
	private HashMap<Class<? extends Tower>, TowerDescription> towerToDescMap = new HashMap<>();
	private LabelStyle labelstyle;

	public BuildingController() {

		labelstyle = new LabelStyle();
		labelstyle.font = TextureFactory.getFont("emmett", 200, Color.YELLOW);

		TowerDescription current;

		current = new TowerDescription("Anti Gravity", "Will pull stuff",
				TextureFactory.getTexture("antiGravityIcon"));
		current.setImageBounds(35, 35);
		towerDescList.add(current);
		towerToDescMap.put(AntiGravityTower.class, current);

		current = new TowerDescription("AOE", "Will push more stuff",
				TextureFactory.getTexture("singleShotIcon"));
		current.setImageBounds(35, 35);
		towerDescList.add(current);
		towerToDescMap.put(AoeTower.class, current);

		current = new TowerDescription("Single Shot", "Will push 1 stuff",
				TextureFactory.getTexture("singleShotIcon"));
		current.setImageBounds(35, 35);
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
			label = new Label(name + "\n" + tooltip, labelstyle);
		}

		public String name;
		public String tooltip;
		public Label label;
		public Image image;

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
					&& stagex < (image.getX() + image.getWidth()
							* image.getScaleX())
					&& stagey > image.getY()
					&& stagey < (image.getY() + image.getHeight()
							* image.getScaleY())) {

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
							+ (hoverImage.getWidth() * hoverImage.getScaleX()) > image
							.getStage().getWidth()) {
						hoverImage.setX(hoverImage.getX()
								- (hoverImage.getWidth() * hoverImage
										.getScaleX()));
					}
					
					hoverLabel = label;
					hoverLabel.setX(hoverImage.getX() + 100);
					hoverLabel.setY(hoverImage.getY()
							+ (hoverImage.getHeight() * hoverImage.getScaleY())
							- hoverLabel.getHeight() - 50);

					image.getStage().addActor(hoverImage);
					image.getStage().addActor(hoverLabel);

				}

				return true;
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

	}

}
