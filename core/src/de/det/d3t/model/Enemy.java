package de.det.d3t.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;

public class Enemy extends Circle{
	private float scale;
	private float acceleration = 1000.f;
	private float maxHp = 100;
	private float hp = 100;
	private float glideFactor = 0.995f;
	private float mass = 1f;
	private float velocityX = 0;
	private float velocityY = -1000; 
	private HpBar hpBar;
	private boolean ingame;
	
	private static final Vector2 hpBarOffset = new Vector2(0, 140);
	private static final Vector2 hpBarSize = new Vector2(250, 40);
	
	public Enemy(float x, float y, float scale, boolean ingame) {
		super(TextureFactory.getTexture("enemy"), (TextureFactory.getTexture("enemy").getHeight() / 2) * scale);
		setBounds(x, y, TextureFactory.getTexture("enemy").getWidth() * scale, TextureFactory.getTexture("enemy").getHeight() * scale);
		this.scale = scale;
		this.ingame = ingame;
		hpBar = new HpBar();
	}
	
	@Override
	protected void setStage(Stage stage) {
//		stage.addActor(hpBar);
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
		length = 1f / length;
		targetX = targetX * length;
		targetY = targetY * length;
		targetX *= acceleration * delta;
		targetY *= acceleration * delta;
		velocityX += targetX;
		velocityY += targetY;
		velocityX *= glideFactor;
		velocityY *= glideFactor;
		rotateBy((float) (Math.sqrt(velocityX*velocityX + velocityY*velocityY) / 3 * delta));
		setPosition(getX() + velocityX * delta, getY()+ velocityY * delta);
	}
	
	public class HpBar extends Entity{
		

		protected HpBar() {
			super(TextureFactory.getTexture("basic"));
			setZIndex(10);
			setColor(new Color(0, 1, 0, 1));
		}
		
		

		@Override
		public void act(float delta) {
//			hp -= 0.1f;
//			Vector2 tempSize = hpBarSize.cpy().scl(scale);
//			tempSize.x *= hp / maxHp;
//			Vector2 vec = getCenter().sub(tempSize.scl(0.5f)).add(hpBarOffset.cpy().scl(scale));
//			setBounds(vec.x, vec.y, tempSize.x, tempSize.y);
		}
		
	}
}
