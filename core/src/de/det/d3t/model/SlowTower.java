package de.det.d3t.model;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;

public class SlowTower extends Tower {
	private float slowFactor = 2;
	private float cd = 1;
	private float missileSize = 100;
	private float missileVel = 2000;

	private float time = cd;
	RotatingImage deco;
	RotatingImage deco2;

	RotatingImage deco4;
	RotatingImage deco5;
	RotatingImage deco6;
	RotatingImage deco7;
	RotatingImage deco8;
	RotatingImage deco9;

	public SlowTower(float x, float y, float scale) {
		super(x, y, scale);
		deco = new RotatingImage(TextureFactory.getTexture("green1"), this, 130/Settings.scaleConst + 15);

		deco4 = new RotatingImage(TextureFactory.getTexture("orange1"), this,
				70/Settings.scaleConst);
		deco5 = new RotatingImage(TextureFactory.getTexture("orange1"), this,
				70/Settings.scaleConst);
		deco6 = new RotatingImage(TextureFactory.getTexture("orange1"), this,
				70/Settings.scaleConst);
		deco7 = new RotatingImage(TextureFactory.getTexture("orange1"), this,
				70/Settings.scaleConst);
		deco8 = new RotatingImage(TextureFactory.getTexture("orange1"), this,
				70/Settings.scaleConst);
		deco9 = new RotatingImage(TextureFactory.getTexture("orange1"), this,
				70/Settings.scaleConst);

		deco4.setBounds(0, 0, 90, 90);
		deco4.setRoationSpeed(-300);
		deco5.setBounds(0, 0, 90, 90);
		deco5.setRoationSpeed(-300);
		deco5.setRotation(60);
		deco6.setBounds(0, 0, 90, 90);
		deco6.setRoationSpeed(-300);
		deco6.setRotation(120);
		deco7.setBounds(0, 0, 90, 90);
		deco7.setRoationSpeed(-300);
		deco7.setRotation(180);
		deco8.setBounds(0, 0, 90, 90);
		deco8.setRoationSpeed(-300);
		deco8.setRotation(240);
		deco9.setBounds(0, 0, 90, 90);
		deco9.setRoationSpeed(-300);
		deco9.setRotation(300);

		// deco2 = new RotatingImage(TextureFactory.getTexture("red1"), this,
		// 130);

		// addComponent(deco2);
		deco.setBounds(0, 0, 185,185);
		// deco2.setBounds(0, 0, 150, 150);
		// deco2.setRotation(180f);

		addComponent(deco4);
		addComponent(deco5);
		addComponent(deco6);
		addComponent(deco7);
		addComponent(deco8);
		addComponent(deco9);

		addComponent(deco);
	}

	@Override
	public void act(float delta) {
		if (isActive() == false) {
			return;
		}

		time -= delta;
		if (time < 0) {
			time = cd;
			shoot();
		}
		super.act(delta);
	}

	@Override
	public boolean remove() {
		// TODO Auto-generated method stub
		return super.remove();
	}

	public void shoot() {
		Enemy target = getNearest(Enemy.getAllEnemys());
		if (target != null && target.getStage() != null) {
			Missile m = new Missile(TextureFactory.getTexture("slowMissile"),
					this, target, missileVel, missileSize);
			getStage().addActor(m);
			m.setAction((Enemy e) -> {
				if (e != null) {

					// e.setAcceleration(e.getAcceleration() / slowFactor);

					e.setVelocityX(e.getVelocityX() / slowFactor);
					e.setVelocityY(e.getVelocityY() / slowFactor);

				}
			});
		}
	}

}
