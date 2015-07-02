package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.det.d3t.TextureFactory;

public class MagnetTower extends Tower {
	private float knockStrength = 2000;
	private float towerRange = 2000;
	private BlinkImage deco;
	private SpriteBatch sp;

	public MagnetTower(float x, float y, float scale) {
		super(x, y, scale);
		deco = new BlinkImage(TextureFactory.getAnimation("arrows_anim"), this, 0,-1,250,10);
		addComponent(deco);
		deco.setBounds(0, 0, 150, 150);
		sp = new SpriteBatch();
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
			float difX = cx - e.getCenterX();
			float difY = cy - e.getCenterY();
			float length = (float) Math.sqrt(difX * difX + difY * difY);
			difX /= length;
			difY /= length;
			e.addForce(difX * knockStrength * delta, difY * knockStrength * delta);
		}
		super.act(delta);
	}


}
