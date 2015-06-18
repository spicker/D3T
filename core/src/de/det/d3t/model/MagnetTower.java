package de.det.d3t.model;

import de.det.d3t.TextureFactory;

public class MagnetTower extends Tower {
	public float knockStrength = 2000;
	private float cd = 1f;
	BlinkImage deco;

	public MagnetTower(float x, float y, float scale) {
		super(x, y, scale);
		//deco = new BlinkImage(TextureFactory.getTexture("arrows_anim_0"), this, 0,-1,250,"arrows_anim",10);
		addComponent(deco);
		deco.setBounds(0, 0, 150, 150);
	}
	
	@Override
	public void act(float delta) {
		cd -= delta;
		if(cd < 0){
			cd = 1f;
			shoot();
		}
		super.act(delta);
	}

	
	public void shoot(){

	}

}
