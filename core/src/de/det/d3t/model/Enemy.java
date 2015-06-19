package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.sun.xml.internal.messaging.saaj.soap.impl.DetailImpl;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.TileMapIntersectionDetector;
import de.det.d3t.util.RadialSprite;

public class Enemy extends Circle{
	private static ArrayList<Enemy> allEnemys = new ArrayList<Enemy>();
	private float scale;
	private float acceleration = 1000.f;
	private float accelerationGrow = 100f;
	private float maxHp = 100;
	private float hp = 100; 
	private float glideFactor = 0.90f;
	private float mass = 1f;
	private float velocityX = 0;
	private float velocityY = -1000; 
	private Image hpBarBack;
	private Image hpBarFront;
	private boolean ingame;
	private RadialSprite hpBarFrontSprite;
	
	public Enemy(float x, float y, float scale, boolean ingame) {
		super(TextureFactory.getTexture("enemy"), (TextureFactory.getTexture("enemy").getHeight() / 2) * scale);
		if(ingame){
			allEnemys.add(this);
		}
		setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale, TextureFactory.getTexture("enemy").getHeight() * scale);
		this.scale = scale;
		this.ingame = ingame;
		hpBarBack = new Image(TextureFactory.getTexture("hpbarback"));
		hpBarBack.setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale, TextureFactory.getTexture("enemy").getHeight() * scale);
		hpBarFrontSprite = new RadialSprite(new TextureRegion(TextureFactory.getTexture("hpbar")));
		hpBarFront = new Image(hpBarFrontSprite);
		hpBarFrontSprite.setColor(Color.valueOf("00FF00"));
		hpBarFront.setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale , TextureFactory.getTexture("enemy").getHeight() * scale);
	}
	
	public static ArrayList<Enemy> getAllEnemys() {
		return allEnemys;
	}
	
	@Override
	protected void setStage(Stage stage) {
		if(stage != null){
			stage.addActor(hpBarBack);
			stage.addActor(hpBarFront);
		}
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
	
	public void addForce(float x, float y){
		velocityX += x;
		velocityY += y;
	}
	
	@Override
	public void setPosition(float x, float y) {
		hpBarBack.setPosition(x, y);
		hpBarFront.setPosition(x, y);
		super.setPosition(x, y);
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
		acceleration += accelerationGrow * delta;
		targetX *= acceleration * delta;
		targetY *= acceleration * delta;
		velocityX += targetX;
		velocityY += targetY;
		velocityX *= Math.pow(glideFactor, delta);
		velocityY *= Math.pow(glideFactor, delta);
		rotateBy((float) (Math.sqrt(velocityX*velocityX + velocityY*velocityY) / 3 * delta));
		setPosition(getX() + velocityX * delta, getY()+ velocityY * delta);
		
		


		
	}
	
	@Override
	public boolean remove() {
		allEnemys.remove(this);
		hpBarBack.remove();
		hpBarFront.remove();
		return super.remove();
	}
	
	public static void checkForIntersection(TileMapIntersectionDetector detector, float delta){
		ArrayList<Enemy> toRemove = new ArrayList<>();
		for(Enemy e : allEnemys){
			if(!detector.hasIntersectAt(e.getCenterX(), e.getCenterY())){
				e.hp -= 30 * delta;
				e.hpBarFrontSprite.setAngle(360 * (1 -(e.hp / e.maxHp)));
				if(e.hp < 0){
					toRemove.add(e);
				}
			}
		}
		for(Enemy e : toRemove){
			e.remove();
		}
	}

}
