package de.det.d3t.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.util.RadialSprite;

public class Tower extends Circle {
	private Group components;
	private float scale;
	protected float maxHp = 100;
	private float hp = 100;
	private Image hpBarBack;
	private Image hpBarFront;
	private RadialSprite hpBarFrontSprite;
	private boolean active = true;

	public Tower(float x, float y, float scale) {
		super(TextureFactory.getTexture("towerBackground"), (TextureFactory
				.getTexture("towerBackground").getHeight() / 2) * scale);
		setBounds(x, y, TextureFactory.getTexture("towerBackground").getWidth() * scale,
				TextureFactory.getTexture("towerBackground").getHeight() * scale);
		this.scale = scale;
		this.components = new Group();
		
		hpBarBack = new Image(TextureFactory.getTexture("hpbarbackTower"));
		hpBarBack.setBounds(x, y, TextureFactory.getTexture("towerBackground").getWidth() * scale, TextureFactory.getTexture("towerBackground").getHeight() * scale);
		hpBarFrontSprite = new RadialSprite(new TextureRegion(TextureFactory.getTexture("hpbarTower")));
		hpBarFront = new Image(hpBarFrontSprite);
		hpBarFrontSprite.setColor(Color.valueOf("00FF00"));
		hpBarFront.setBounds(x, y, TextureFactory.getTexture("towerBackground").getWidth() * scale , TextureFactory.getTexture("towerBackground").getHeight() * scale);
		
		
		components.addActor(hpBarBack);
		components.addActor(hpBarFront);
		
		
	}

	
	public void renewPositions(float x, float y){
		hpBarBack.setBounds(x, y, TextureFactory.getTexture("towerBackground").getWidth() * scale, TextureFactory.getTexture("towerBackground").getHeight() * scale);
		hpBarFront.setBounds(x, y, TextureFactory.getTexture("towerBackground").getWidth() * scale , TextureFactory.getTexture("towerBackground").getHeight() * scale);
	}
	
	
	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(float maxHp) {
		this.maxHp = maxHp;
	}

	public float getHp() {
		return hp;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}

	public void addComponent(Actor a) {
		components.addActor(a);
	}

	public void removeComponent(Actor a) {
		a.remove();
	}
	
	public void removeAllComponents(){
		components.remove();
		components = new Group();
	}

	@Override
	public boolean remove() {
		removeAllComponents();
		return super.remove();
	}
	
	@Override
	protected void setStage(Stage stage) {
		if (stage != null) {
			stage.addActor(components);
		}
		super.setStage(stage);
	}

	public void removeHPbar() {
		components.removeActor(hpBarFront);
		components.removeActor(hpBarBack);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public void act(float delta) {
		if (isActive() == false) {
			return;
		}

		hp -= 0.1f;
		hpBarFrontSprite.setAngle(360 * (1 - (hp / maxHp)));
	}

}
