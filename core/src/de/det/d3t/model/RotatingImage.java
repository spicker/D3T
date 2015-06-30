package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class RotatingImage extends Image {
	private float axisRoationSpeed = 180f;
	private float roationSpeed = 360;
	private float rotation = 0f;
	private float offset = 500;
	private Circle bind;
		
	public RotatingImage(Texture texture, Circle bind, float offset) {
		super(texture);
		this.offset = offset;
		this.bind = bind;
	}
	
	public void setAxisRoationSpeed(float axisRoationSpeed) {
		this.axisRoationSpeed = axisRoationSpeed;
	}
	
	public void setRoationSpeed(float roationSpeed) {
		this.roationSpeed = roationSpeed;
	}
	
	public void setOffset(float offset) {
		this.offset = offset;
	}
	
	@Override
	public void setRotation(float degrees) {
		rotation = degrees;
	}
	
	@Override
	public void act(float delta) {
		setOrigin(getWidth() / 2, getHeight() / 2);
		rotateBy(axisRoationSpeed * delta);
		rotation -= roationSpeed * delta;
		setPosition(bind.getCenterX() + ((float) Math.cos(Math.toRadians(rotation)) * offset) - getWidth() / 2, 
				bind.getCenterY() + ((float) Math.sin(Math.toRadians(rotation)) * offset) - getHeight() / 2);
	}
	
}
