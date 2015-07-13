package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.Settings;

public class BillardBall extends Circle {

	private float velocityX, velocityY, length;

	private float radius;
	private float velocity = 1500;
	private float mass = 5;
	private float despawnTime = 15;

	public BillardBall(Texture texture, Tower start, Enemy target, float scale) {
		super(texture, (texture.getWidth()  /2) * scale/Settings.scaleConst);
		scale /= Settings.scaleConst;
		this.radius = (texture.getWidth() /2) * scale ;
		setBounds(start.getCenterX() - radius, start.getCenterY() - radius,
				radius*2, radius*2);

		if (target != null) {
			velocityX = target.getX() - getX();
			velocityY = target.getY() - getY();
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

		if (Enemy.getAllEnemys().isEmpty() || despawnTime < 0) {
			remove();

		} else {
			despawnTime -= delta;
			
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
