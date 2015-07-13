package de.det.d3t.model;

import de.det.d3t.TextureFactory;

public class BaseCircle extends Circle {

	public BaseCircle(float radius) {
		super(TextureFactory.getTexture("wave_0"), radius);
		this.setWidth(radius*2);
		this.setHeight(radius*2);
	}
	
	@Override
	public void act(float delta) {
	}

}
