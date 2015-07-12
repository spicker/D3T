package de.det.d3t.model;

import de.det.d3t.TextureFactory;

public class BaseCircle extends Circle {

	public BaseCircle(float radius) {
		super(TextureFactory.getTexture("wave_0"), radius);
	}
	
	@Override
	public void act(float delta) {
	}

}
