package de.det.d3t.model;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;

public class BallsTower extends Tower {
	private float repeat = 1;
	private float length = 200;
	private float time = repeat;
	RotatingImage deco, deco2, deco3, deco4, deco5, deco6, deco7, deco8, deco9;

	public BallsTower(float x, float y, float scale) {
		super(x, y, scale);

		deco = new RotatingImage(TextureFactory.getTexture("black1"), this,
				130 / Settings.scaleConst + 15);
		addComponent(deco);
		deco.setBounds(0, 0, 200, 200);

		deco4 = new RotatingImage(TextureFactory.getTexture("red1"), this,
				70 / Settings.scaleConst);
		deco5 = new RotatingImage(TextureFactory.getTexture("red1"), this,
				70 / Settings.scaleConst);
		deco6 = new RotatingImage(TextureFactory.getTexture("red1"), this,
				70 / Settings.scaleConst);
		deco7 = new RotatingImage(TextureFactory.getTexture("red1"), this,
				70 / Settings.scaleConst);
		deco8 = new RotatingImage(TextureFactory.getTexture("red1"), this,
				70 / Settings.scaleConst);
		deco9 = new RotatingImage(TextureFactory.getTexture("red1"), this,
				70 / Settings.scaleConst);

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

		addComponent(deco4);
		addComponent(deco5);
		addComponent(deco6);
		addComponent(deco7);
		addComponent(deco8);
		addComponent(deco9);

	}

	@Override
	public void act(float delta) {
		if (isActive() == false) {
			return;
		}

		time -= delta;
		if (time < 0) {
			time = repeat;
			shoot();
		}

		super.act(delta);
	}

	private void shoot() {
		Enemy target = getNearest(Enemy.getAllEnemys());
		if (target != null && target.getStage() != null) {
			BallsOfSteel b = new BallsOfSteel(TextureFactory.getTexture("black1"), TextureFactory.getTexture("ropeTexture"), this, target, length);
			getStage().addActor(b);
		}

	}
}
