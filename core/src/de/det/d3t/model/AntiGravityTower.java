package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.det.d3t.TextureFactory;

public class AntiGravityTower extends Tower {
	public float knockStrength = 2000;
	BlinkImage deco;
	private float towerRange = 4000f;

	public AntiGravityTower(float x, float y, float scale) {
		super(x, y, scale);
		deco = new BlinkImage(TextureFactory.getAnimation("arrows_anim_green"), this, 0,1,250,10);
		addComponent(deco);
		deco.setBounds(0, 0, 200, 200);


	}
	
	
	@Override
	public void act(float delta){
		if(isActive() == false){
			return;
		}
		
		ArrayList<Enemy> inRange = getAllInRange(Enemy.getAllEnemys(), towerRange);
		float cx = getCenterX();
		float cy = getCenterY();
		for(Enemy e : inRange){
			float difX = e.getCenterX() - cx;
			float difY = e.getCenterY() - cy;
			float length = (float) Math.sqrt(difX * difX + difY * difY);
			difX /= length;
			difY /= length;
			e.addForce(difX * knockStrength * delta, difY * knockStrength * delta);
		}
		super.act(delta);
	}

}
