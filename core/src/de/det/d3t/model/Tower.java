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
	private float maxHp = 100;
	private float hp = 100;
	private Image hpBarBack;
	private Image hpBarFront;
	private RadialSprite hpBarFrontSprite;
	
	public Tower(float x, float y, float scale) {
		super(TextureFactory.getTexture("enemy"), (TextureFactory.getTexture("enemy").getHeight() / 2) * scale);
		setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale, TextureFactory.getTexture("enemy").getHeight() * scale);
		this.scale = scale;
		this.components = new Group();
		hpBarBack = new Image(TextureFactory.getTexture("hpbarback"));
		hpBarBack.setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale, TextureFactory.getTexture("enemy").getHeight() * scale);
		components.addActor(hpBarBack);
		hpBarFrontSprite = new RadialSprite(new TextureRegion(TextureFactory.getTexture("hpbar")));
		hpBarFront = new Image(hpBarFrontSprite);
		hpBarFrontSprite.setColor(Color.valueOf("00FF00"));
		hpBarFront.setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale , TextureFactory.getTexture("enemy").getHeight() * scale);
		components.addActor(hpBarFront);
	}
	
	public void addComponent(Actor a){
		components.addActor(a);
	}
	
	public void removeComponent(Actor a){
		a.remove();
	}
	
	
	@Override
	protected void setStage(Stage stage) {
		stage.addActor(components);
		super.setStage(stage);
	}
	
	@Override
	public void act(float delta) {
		hp-=0.1f;
		hpBarFrontSprite.setAngle(360 * (1 -(hp / maxHp)));
	}

}
