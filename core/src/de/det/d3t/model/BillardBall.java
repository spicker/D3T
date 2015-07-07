package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

public class BillardBall extends Circle {

	private float velocityX, velocityY, length;

	private float radius;
	private float velocity = 10000;
	private float mass = 5;

	public BillardBall(Texture texture, Tower start, Enemy target, float scale) {
		super(texture, (texture.getWidth() / 2) * scale);
		this.radius = (texture.getWidth() / 2) * scale;
		setBounds(start.getCenterX() - radius, start.getCenterY() - radius,
				texture.getWidth() * scale, texture.getHeight() * scale);

		if (target != null) {
			velocityX = target.getCenterX() - getCenterX();
			velocityY = target.getCenterY() - getCenterY();
			length = (float) Math.sqrt(velocityX * velocityX + velocityY
					* velocityY);
//			System.out.println("BillardBall: " + ", difX: " + velocityX
//					+ ", difY: " + velocityY + ", length: " + length);
			velocityX /= length;
			velocityY /= length;
//			System.out.println("BillardBall: " + " difX: " + velocityX
//					+ ", difY: " + velocityY);
		} else {
			remove();
		}
	}

	@Override
	public void act(float delta) {

		if (Enemy.getAllEnemys().isEmpty()) {
			remove();

		} else {
			// System.out.println("BillardBall: "+"move");
			setPosition(getX() + velocityX * velocity * delta, getY()
					+ velocityY * velocity * delta);

		}
	}

	public float getVelocity() {
		return velocity;
	}

	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getVelocityX() {
		return velocityX;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
	}

}
