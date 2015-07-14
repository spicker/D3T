package de.det.d3t.model;

import java.util.ArrayList;

import org.omg.CORBA.Bounds;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.TileMapIntersectionDetector;
import de.det.d3t.util.RadialSprite;

public class Enemy extends Circle{
	private static ArrayList<Enemy> allEnemys = new ArrayList<Enemy>();
	private static final float SPAWN_EFFECT_TIME = 0.7f;
	
	private float scale;
	private float acceleration = 0f;
	private float accelerationGrow = 100f;
	private float maxHp = 100;
	private float hp = 100; 
	private float glideFactor = 0.90f;
	private float mass = 1f;
	private float velocityX = 0;
	private float velocityY = 0; 
	private Image hpBarBack;
	private Image hpBarFront;
	private RadialSprite hpBarFrontSprite;
	private Image spawnEffect;
	public boolean hit = false;
	private float timer = 0;
	
	public Enemy(float x, float y) {
		this(x, y, EnemyType.KEVIN);
	}
	
	public Enemy(float x, float y, EnemyType type){
		super(type.getTexture(), (type.getTexture().getHeight() / 2) * type.getScale()/Settings.scaleConst);
		scale = type.getScale();
		maxHp = hp = type.getHp();
		accelerationGrow = type.getAccelerationGrow();
		mass = type.getMass();
		scale /= Settings.scaleConst;
		allEnemys.add(this);
		Rectangle bounds = new Rectangle(x, y, type.getTexture().getWidth() * scale, type.getTexture().getHeight() * scale);
		setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		hpBarBack = new Image(TextureFactory.getTexture("hpbarback"));
		hpBarBack.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
		hpBarFrontSprite = new RadialSprite(new TextureRegion(TextureFactory.getTexture("hpbar")));
		hpBarFront = new Image(hpBarFrontSprite);
		hpBarFrontSprite.setColor(Color.valueOf("00FF00"));
		hpBarFront.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
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
		targetX = Settings.basePositionX - getX();
		targetY = Settings.basePositionY - getY();
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
		rotateBy((float) (Math.sqrt(velocityX*velocityX + velocityY*velocityY) / 5 / scale * delta));
		setPosition(getX() + velocityX * delta, getY()+ velocityY * delta);
		
		if(spawnEffect != null){
			timer+=delta;
			if(timer >= SPAWN_EFFECT_TIME){
				spawnEffect.remove();
				spawnEffect = null;
				return;
			}
			
			float oldWidth = spawnEffect.getWidth()*spawnEffect.getScaleX();
			spawnEffect.setScale((float) (spawnEffect.getScaleX() + (2 * delta)));
			float offset = (spawnEffect.getWidth()*spawnEffect.getScaleX() - oldWidth) / 2;
			spawnEffect.setX(spawnEffect.getX() - offset);
			spawnEffect.setY(spawnEffect.getY() - offset);
		}

		
	}
	
	@Override
	public boolean remove() {
		allEnemys.remove(this);
		hpBarBack.remove();
		hpBarFront.remove();
		if(spawnEffect != null){
			spawnEffect.remove();
			spawnEffect = null;
		}
		return super.remove();
	}
	
	public static void checkForIntersection(TileMapIntersectionDetector detector, float delta){
		ArrayList<Enemy> toRemove = new ArrayList<>();
		for(Enemy e : allEnemys){
			if(!detector.hasIntersectAt(e.getCenterX(), e.getCenterY())){
				e.hp -= 5 * delta;
				e.hpBarFrontSprite.setAngle(360 * (1 -(e.hp / e.maxHp)));
				if(e.hp < 0){
					toRemove.add(e);
				}
			}
		}
		for(Enemy e : toRemove){
			e.remove();
			TextureFactory.getSound("goldget").play();
		}
	}
	
	public void addSpawnEffect(){

		spawnEffect = new Image(TextureFactory.getTexture("wave_0"));
		spawnEffect.setBounds(getX(), getY(), hpBarFront.getWidth(), hpBarFront.getHeight());
		spawnEffect.setColor(Color.BLUE);
		getStage().addActor(spawnEffect);
	}

	public float getScale() {
		return scale;
	}

	public float getAcceleration() {
		return acceleration;
	}

	public float getAccelerationGrow() {
		return accelerationGrow;
	}

	public float getMaxHp() {
		return maxHp;
	}

	public float getHp() {
		return hp;
	}

	public float getGlideFactor() {
		return glideFactor;
	}

	public Image getHpBarBack() {
		return hpBarBack;
	}

	public Image getHpBarFront() {
		return hpBarFront;
	}

	public RadialSprite getHpBarFrontSprite() {
		return hpBarFrontSprite;
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

	public static void setAllEnemys(ArrayList<Enemy> allEnemys) {
		Enemy.allEnemys = allEnemys;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public void setAcceleration(float acceleration) {
		this.acceleration = acceleration;
	}

	public void setAccelerationGrow(float accelerationGrow) {
		this.accelerationGrow = accelerationGrow;
	}

	public void setMaxHp(float maxHp) {
		this.maxHp = maxHp;
	}

	public void setHp(float hp) {
		this.hp = hp;
	}

	public void setGlideFactor(float glideFactor) {
		this.glideFactor = glideFactor;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public void setHpBarBack(Image hpBarBack) {
		this.hpBarBack = hpBarBack;
	}

	public void setHpBarFront(Image hpBarFront) {
		this.hpBarFront = hpBarFront;
	}

	public void setHpBarFrontSprite(RadialSprite hpBarFrontSprite) {
		this.hpBarFrontSprite = hpBarFrontSprite;
	}
}
