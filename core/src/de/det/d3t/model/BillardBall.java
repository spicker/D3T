package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public class BillardBall extends Missile {

	private float scale = 1;
	private float difX, difY, length;

	public BillardBall(Texture texture, Tower start, Enemy target, float velocity, float radius) {
		super(texture,start,target,velocity,radius);
		setBounds(start.getCenterX() - radius / 2, start.getCenterY() - radius
				/ 2, TextureFactory.getTexture("enemy").getWidth() * scale,
				TextureFactory.getTexture("enemy").getHeight() * scale);

		if (target != null) {
			difX = target.getCenterX() - getX() + getWidth() / 2;
			difY = target.getCenterY() - getY() + getWidth() / 2;
			length = (float) Math.sqrt(difX * difX + difY * difY);
			difX /= length;
			difY /= length;
		}else{
			remove();
		}
	}

	@Override
	public void act(float delta) {

		if (target == null || target.getStage() == null) {
			target = start.getNearest(Enemy.getAllEnemys());
		} else {

			setPosition(getX() + difX * velocity * delta, getY() + difY
					* velocity * delta);
		}
	}
}
