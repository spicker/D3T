package de.det.d3t.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;

public class SlowTower extends Tower {
	public float slowFactor = 3;
	private float cd = 0.3f;
	RotatingImage deco;
	RotatingImage deco2;

	public SlowTower(float x, float y, float scale) {
		super(x, y, scale);
		deco = new RotatingImage(TextureFactory.getTexture("green1"), this, 130);
		//deco2 = new RotatingImage(TextureFactory.getTexture("red1"), this, 130);
		addComponent(deco);
		//addComponent(deco2);
		deco.setBounds(0, 0, 150, 150);
		//deco2.setBounds(0, 0, 150, 150);
		//deco2.setRotation(180f);
	}
	
	@Override
	public void act(float delta) {
		cd -= delta;
		if(cd < 0){
			cd = 0.3f;
			shoot();
		}
		super.act(delta);
	}
	
	@Override
	public boolean remove() {
		// TODO Auto-generated method stub
		return super.remove();
	}

	
	public void shoot(){
		Missle m = new Missle(TextureFactory.getTexture("singleShotMissle"), this,getNearest(Enemy.getAllEnemys()), 1000, 100);
		getStage().addActor(m);
		m.setAction((Enemy e) -> {
			if(e != null){
				e.setAcceleration(e.getAcceleration() / slowFactor);
				
			}
		});
	}

}
