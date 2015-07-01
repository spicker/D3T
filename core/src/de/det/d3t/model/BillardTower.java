package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public class BillardTower extends Tower {

	private float shootTime = 1;
	private float time = shootTime;
	private float missileVel = 2000;
	private float missileSize = 200;
	private float knockStrength = 1000;

	public BillardTower(float x, float y, float scale) {
		super(x, y, scale);
	}

	@Override
	public void act(float delta) {
		time -= delta;
		if (time < 0) {
			time = shootTime;
			shoot();
		}

		super.act(delta);
	}

	public void shoot() {
		Missile m = new BillardBall(
				TextureFactory.getTexture("singleShotMissle"), this,
				getNearest(Enemy.getAllEnemys()), missileVel, missileSize);
		// BillardBall b = new
		// BillardBall(getCenterX(),getCenterY(),2,getNearest(Enemy.getAllEnemys()));
		getStage().addActor(m);
		m.setAction((Enemy e) -> {

			if (e != null) {
				float targetX = e.getCenterX() - getCenterX();
				float targetY = e.getCenterY() - getCenterY();
				float length = (float) Math.sqrt(targetX * targetX + targetY
						* targetY);
				targetX /= length;
				targetY /= length;
				e.addForce(targetX * knockStrength, targetY * knockStrength);
			}

		});
	}

	@Override
	public boolean remove() {
		// TODO Auto-generated method stub
		return super.remove();
	}
}
