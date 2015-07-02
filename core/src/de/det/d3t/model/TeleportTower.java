package de.det.d3t.model;

import de.det.d3t.TextureFactory;

public class TeleportTower extends Tower {

	private float cd = 1f;
	private float missileVel = 4000;
	private float missileSize = 100;

	private float time = cd;
	RotatingImage deco;
	RotatingImage deco2;

	public TeleportTower(float x, float y, float scale) {
		super(x, y, scale);
		deco = new RotatingImage(TextureFactory.getTexture("blue1"), this, 130);
		// deco2 = new RotatingImage(TextureFactory.getTexture("red1"), this,
		// 130);
		addComponent(deco);
		// addComponent(deco2);
		deco.setBounds(0, 0, 150, 150);
		// deco2.setBounds(0, 0, 150, 150);
		// deco2.setRotation(180f);
	}

	@Override
	public void act(float delta) {
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
			Missile m = new Missile(
					TextureFactory.getTexture("teleportMissile"), this, target,
					missileVel, missileSize);
			getStage().addActor(m);
			m.setAction((Enemy e) -> {
				if (e != null) {

					// TODO: Anstatt (0,0) zum Spawnpunkt?
					e.setPosition(0, 0);

				}
			});
		}
	}
}
