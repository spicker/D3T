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

public class Tower extends Circle{
	private Group components;
	private float scale;
	protected float maxHp = 100;
	private float hp = 100;
	private Image hpBarBack;
	private Image hpBarFront;
	private RadialSprite hpBarFrontSprite;
	private boolean active = true;
	
	public Tower(float x, float y, float scale) {
		super(TextureFactory.getTexture("towerBackground"), (TextureFactory.getTexture("towerBackground").getHeight() / 4) * scale);
		setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale, TextureFactory.getTexture("enemy").getHeight() * scale);
		this.scale = scale;
		this.components = new Group();
		hpBarBack = new Image(TextureFactory.getTexture("hpbarbackTower"));
		hpBarBack.setBounds(x - TextureFactory.getTexture("towerBackground").getWidth()/2*0.5f, y - TextureFactory.getTexture("towerBackground").getHeight()/2*0.5f, TextureFactory.getTexture("towerBackground").getWidth()/2 * scale*1.5f, TextureFactory.getTexture("towerBackground").getHeight()/2 * scale*1.5f);
		components.addActor(hpBarBack);
		hpBarFrontSprite = new RadialSprite(new TextureRegion(TextureFactory.getTexture("hpbarTower")));
		hpBarFront = new Image(hpBarFrontSprite);
		hpBarFrontSprite.setColor(Color.valueOf("00FF00"));
		hpBarFront.setBounds(x - TextureFactory.getTexture("towerBackground").getWidth()/2*0.5f, y - TextureFactory.getTexture("towerBackground").getHeight()/2*0.5f, TextureFactory.getTexture("towerBackground").getWidth()/2 * scale*1.5f , TextureFactory.getTexture("towerBackground").getHeight()/2 * scale*1.5f);
		components.addActor(hpBarFront);
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

	public void addComponent(Actor a){
		components.addActor(a);
	}
	
	public void removeComponent(Actor a){
		a.remove();
	}
	
	@Override
	protected void setStage(Stage stage) {
		if(stage != null){
			stage.addActor(components);
		}
		super.setStage(stage);
	}
	
	public void removeHPbar(){
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
		if(active){
			hp-=0.1f;
			hpBarFrontSprite.setAngle(360 * (1 -(hp / maxHp)));
		}
	}

}
