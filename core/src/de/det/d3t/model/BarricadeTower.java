package de.det.d3t.model;

import de.det.d3t.TextureFactory;

public class BarricadeTower extends Tower {
	private float hpMult = 5;
	private static float sizeMult = 1.5f;
	RotatingImage deco;

	public BarricadeTower(float x, float y, float scale) {
		super(x, y, scale * sizeMult);

		this.maxHp *= hpMult;
		this.hp *= hpMult;

		deco = new RotatingImage(TextureFactory.getTexture("barricade"), this,0);
		deco.setBounds(getCenterX(), getCenterY(), 1000, 1000);
		//deco.setRoationSpeed(-20);
		deco.setAxisRoationSpeed(25);
		addComponent(deco);

	}
}
