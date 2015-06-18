package de.det.d3t.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import de.det.d3t.TextureFactory;

public class MagnetTower extends Tower {
	public float knockStrength = 2000;
	private float cd = 1f;
	BlinkImage deco;
	SpriteBatch sp;

	public MagnetTower(float x, float y, float scale) {
		super(x, y, scale);
		deco = new BlinkImage(TextureFactory.getAnimation("arrows_anim"), this, 0,-1,250,10);
		addComponent(deco);
		deco.setBounds(0, 0, 150, 150);
		sp = new SpriteBatch();
	}
	
	@Override
	public void act(float delta) {
		sp.begin();
		cd -= delta;
		if(cd < 0){
			cd = 1f;
			shoot();
		}
		deco.act(delta);
		deco.draw(sp, 0);
		sp.end();
		super.act(delta);
	}

	
	public void shoot(){

	}

}
