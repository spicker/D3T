package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

public class AoeTower extends Tower {
	private float pingSize = 500;
	private float pingDuration = 1.5f;
	private float pingTime = 3;
	private float knockStrength = 100;
	private float time = pingTime;
	private float time_knockback = pingDuration;
	private ArrayList<Enemy> hasBeenHit = new ArrayList<Enemy>();

	public AoeTower(float x, float y, float scale) {
		super(x, y, scale);

		
	}

	@Override
	public void act(float delta) {
		

		time -= delta;
		if (time < 0) {
			time = pingTime;
		} else {
			if (time < pingTime - pingDuration) {
				time_knockback = pingDuration;

			} else {
				if (time < pingTime) {
					time_knockback -= delta;
					float curDist = (time_knockback / pingDuration) * pingSize;
					aoeKnockback(curDist);
				}
			}
		}
		
		super.act(delta);

	}

	private void aoeKnockback(float curDist) {
		hasBeenHit.clear();
		for (int i = 0; i < 20; i++) {
			Enemy e = getNearest(Enemy.getAllEnemys());

			if (e != null && !hasBeenHit.contains(e)) {
				float targetX = e.getCenterX() - getCenterX();
				float targetY = e.getCenterY() - getCenterY();
				float length = (float) Math.sqrt(targetX * targetX + targetY
						* targetY);
				if (length < curDist) {
					hasBeenHit.add(e);
					targetX /= length;
					targetY /= length;
					System.out.println(targetX);
					Gdx.app.debug("AoeTower", "" + targetX);
					e.addForce(targetX * knockStrength, targetY * knockStrength);
				}
			}
		}
	}

}
