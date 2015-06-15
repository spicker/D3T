package de.det.d3t.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.frame.RadialSprite;

public class Enemy extends Circle{
	private float scale;
	private float acceleration = 1000.f;
	private float maxHp = 100;
	private float hp = 100;
	private float glideFactor = 0.90f;
	private float mass = 1f;
	private float velocityX = 0;
	private float velocityY = -1000; 
	private Image hpBarBack;
	private Image hpBarFront;
	private boolean ingame;
	
	private static final Vector2 hpBarOffset = new Vector2(0, 140);
	private static final Vector2 hpBarSize = new Vector2(250, 40);
	
	public Enemy(float x, float y, float scale, boolean ingame) {
		super(TextureFactory.getTexture("enemy"), (TextureFactory.getTexture("enemy").getHeight() / 2) * scale);
		setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale, TextureFactory.getTexture("enemy").getHeight() * scale);
		this.scale = scale;
		this.ingame = ingame;
		hpBarBack = new Image(TextureFactory.getTexture("hpbarback"));
		hpBarBack.setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale, TextureFactory.getTexture("enemy").getHeight() * scale);
		hpBarFront = new Image(new RadialSprite(new TextureRegion(TextureFactory.getTexture("hpbar"))));
		hpBarFront.setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale * 1.5f, TextureFactory.getTexture("enemy").getHeight() * scale * 1.5f);
	}
	
	@Override
	protected void setStage(Stage stage) {
		stage.addActor(hpBarBack);
		stage.addActor(hpBarFront);
		super.setStage(stage);
	}
	
	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}
	
	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public float getMass() {
		return mass;
	}
	
	@Override
	public void act(float delta) {
		delta = Math.min(delta, 0.03f);
		float targetX = 0;
		float targetY = 0;
		if(ingame){
			targetX = Settings.basePositionX - getX();
			targetY = Settings.basePositionY - getY();
		}
		else{
			targetX = Settings.getBasePositionMenuX() - getX();
			targetY = Settings.getBasePositionMenuY() - getY();
		}
		float length = (float) Math.sqrt(targetX * targetX + targetY * targetY);
		targetX /= length;
		targetY /= length;
		targetX *= acceleration * delta;
		targetY *= acceleration * delta;
		velocityX += targetX;
		velocityY += targetY;
		velocityX *= Math.pow(glideFactor, delta);
		velocityY *= Math.pow(glideFactor, delta);
		rotateBy((float) (Math.sqrt(velocityX*velocityX + velocityY*velocityY) / 3 * delta));
		setPosition(getX() + velocityX * delta, getY()+ velocityY * delta);
		hpBarBack.setPosition(getX(), getY());
		hpBarFront.setPosition(getX(), getY());
	}

}
