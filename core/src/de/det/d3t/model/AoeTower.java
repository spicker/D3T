package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;

import de.det.d3t.TextureFactory;

public class AoeTower extends Tower {
	private float pingSize = 2000;
	private float pingDuration = 0.5f;
	private float pingTime = 1;
	private float knockStrength = 500;
	private float time = pingTime;
	private float pingCurSize = pingSize;
	private float size;
	private PingImage deco;

	public AoeTower(float x, float y, float scale) {
		super(x, y, scale);
		this.size = TextureFactory
				.getTexture("enemy").getWidth() * scale;
		deco = new PingImage(TextureFactory.getTexture("ping"), size, pingSize, pingTime,pingDuration, this);
		addComponent(deco);
		// deco.setBounds(0, 0, TextureFactory.getTexture("enemy").getWidth()
		// * scale, TextureFactory.getTexture("enemy").getWidth() * scale);

	}

	@Override
	public void act(float delta) {

		time -= delta;
		if (time < 0) {
			time = pingTime;
		} else {
			if (time < pingTime - pingDuration) {

				pingCurSize = size;
			} else {
				if (time < pingTime) {

					pingCurSize += (delta / pingDuration) * pingSize;

					aoeKnockback(pingCurSize);
				}
			}
		}

		super.act(delta);

	}

	private void aoeKnockback(float curDist) {

		ArrayList<Enemy> list = getAllInRange(Enemy.getAllEnemys(), curDist/2);
		for (Enemy e : list) {

			if (e != null) {
				float targetX = e.getCenterX() - getCenterX();
				float targetY = e.getCenterY() - getCenterY();
				float length = (float) Math.sqrt(targetX * targetX + targetY
						* targetY);

				targetX /= length;
				targetY /= length;
				System.out.println(targetX);
				Gdx.app.debug("AoeTower", "" + targetX);
				e.addForce(targetX * knockStrength, targetY * knockStrength);
			}

		}
	}

	public float getPingCurSize() {
		return pingCurSize;
	}

}
