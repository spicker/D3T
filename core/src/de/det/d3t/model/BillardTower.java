package de.det.d3t.model;

import de.det.d3t.TextureFactory;

public class BillardTower extends Tower {

	private float shootTime = 1;
	private float time = shootTime;
	private float ballSize = 0.7f;
	RotatingImage deco;
	
	public BillardTower(float x, float y, float scale) {
		super(x, y, scale);
		
		deco = new RotatingImage(TextureFactory.getTexture("black1"), this, 130);
		addComponent(deco);
		deco.setBounds(0, 0, 150, 150);
	}

	@Override
	public void act(float delta) {
		if(isActive() == false){
			return;
		}
		
		time -= delta;
		if (time < 0) {
			time = shootTime;
			shoot();
		}

		super.act(delta);
	}

	public void shoot() {
		Enemy target = getNearest(Enemy.getAllEnemys());
		if (target != null && target.getStage() != null) {
			BillardBall m = new BillardBall(
					TextureFactory.getTexture("black1"), this,
					getNearest(Enemy.getAllEnemys()), ballSize);
			// BillardBall b = new
			// BillardBall(getCenterX(),getCenterY(),2,getNearest(Enemy.getAllEnemys()));
			getStage().addActor(m);
		}
	}

}
