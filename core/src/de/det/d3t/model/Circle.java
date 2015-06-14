package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public abstract class Circle extends Entity {
	private float radius;

	protected Circle(Texture texture, float radius) {
		super(texture);
		this.radius = radius;
		this.setOrigin(radius, radius);
	}
	
	public float getRadius() {
		return radius;
	}
	
	public float getCenterX(){
		return getX() + (getWidth() / 2f);
	}
	
	public float getCenterY(){
		return getY() + (getHeight() / 2f);
	}

}
