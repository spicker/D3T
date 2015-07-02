package de.det.d3t.model;

import de.det.d3t.TextureFactory;

public class SlowTower extends Tower {
	private float slowFactor = 2;
	private float cd = 1;
	private float missileSize = 100;
	private float missileVel = 2000;
	
	private float time = cd;
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
		if(isActive() == false){
			return;
		}
		
		time -= delta;
		if(time < 0){
			time =cd;
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
		Missile m = new Missile(TextureFactory.getTexture("slowMissile"), this,getNearest(Enemy.getAllEnemys()), missileVel, missileSize);
		getStage().addActor(m);
		m.setAction((Enemy e) -> {
			if(e != null){
				
				//e.setAcceleration(e.getAcceleration() / slowFactor);
				
				e.setVelocityX(e.getVelocityX() / slowFactor);
				e.setVelocityY(e.getVelocityY() / slowFactor);

			}
		});
	}

}
