package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public class BillardTower extends Tower {

	private float shootTime = 1;
	private float time = shootTime;
	private float missileSize = 2;

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
		Enemy target = getNearest(Enemy.getAllEnemys());
		if (target != null && target.getStage() != null) {
			BillardBall m = new BillardBall(
					TextureFactory.getTexture("singleShotMissle"), this,
					getNearest(Enemy.getAllEnemys()), missileSize);
			// BillardBall b = new
			// BillardBall(getCenterX(),getCenterY(),2,getNearest(Enemy.getAllEnemys()));
			getStage().addActor(m);
		}
	}

	@Override
	public boolean remove() {
		// TODO Auto-generated method stub
		return super.remove();
	}
}
