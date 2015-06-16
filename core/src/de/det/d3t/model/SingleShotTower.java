package de.det.d3t.model;

import de.det.d3t.Settings;

public class SingleShotTower extends Tower {
	public float knockStrength = 20000;
	private float cd = 0.01f;

	public SingleShotTower(float x, float y, float scale) {
		super(x, y, scale);
	}
	
	@Override
	public void act(float delta) {
		cd -= delta;
		if(cd < 0){
			cd = 0.01f;
			shoot();
		}
		super.act(delta);
	}
	
	public void shoot(){
		Enemy e = getNearest(Enemy.getAllEnemys());
		if(e != null){
			float targetX = e.getCenterX() - getCenterX();
			float targetY = e.getCenterY() - getCenterY();
			float length = (float) Math.sqrt(targetX * targetX + targetY * targetY);
			targetX /= length;
			targetY /= length;
			System.out.println(targetX);
			e.addForce(targetX * knockStrength, targetY * knockStrength);
		}
	}

}
