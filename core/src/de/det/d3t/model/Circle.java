package de.det.d3t.model;

import java.util.ArrayList;

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
	
	public <T extends Circle> T getNearest(ArrayList<T> list){
		float min = Float.MAX_VALUE;
		T minC = null;
		for(T c : list){
			float x = getCenterX() - c.getCenterX();
			float y = getCenterY() - c.getCenterY();
			float dist = (float) Math.sqrt(x*x + y*y);
			if(dist < min){
				min = dist;
				minC = c;
			}
		}
		return minC;
	}

}
