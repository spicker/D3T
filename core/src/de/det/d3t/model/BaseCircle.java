package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Stage;

import de.det.d3t.TextureFactory;

public class BaseCircle extends Circle {

	ArrayList<RotatingImage> deco = new ArrayList<RotatingImage>();

	public BaseCircle(float radius) {
		super(TextureFactory.getTexture("wave_0"), radius);
		this.setWidth(radius*2);
		this.setHeight(radius*2);

		RotatingImage d1 = new RotatingImage(
				TextureFactory.getTexture("enemy"), this, 0);
		deco.add(d1);
		d1.setRotation((360 / 1));
		d1.setRoationSpeed(50);
		d1.setSize(1300, 1300);

		RotatingImage d2 = new RotatingImage(TextureFactory.getTexture("red1"),
				this, 0);
		deco.add(d2);
		d2.setRotation((360 / 1));
		d2.setRoationSpeed(-100);
		d2.setSize(800, 800);

		RotatingImage d3 = new RotatingImage(
				TextureFactory.getTexture("black1"), this, 0);
		deco.add(d3);
		d3.setRotation((360 / 1));
		d3.setRoationSpeed(100);
		d3.setSize(200, 200);
		for (int i = 0; i < 20; i++) {
			RotatingImage d = new RotatingImage(
					TextureFactory.getTexture("enemyGreen"), this, 80);
			deco.add(d);
			d.setRotation(i * (360 / 20));
			d.setRoationSpeed(100);
			d.setSize(80, 80);
		}

		for (int i = 0; i < 20; i++) {
			RotatingImage d = new RotatingImage(
					TextureFactory.getTexture("red1"), this, 100);
			deco.add(d);
			d.setRotation(i * (360 / 20));
			d.setRoationSpeed(-80);
			d.setSize(100, 100);
		}

		for (int i = 0; i < 10; i++) {
			RotatingImage d = new RotatingImage(
					TextureFactory.getTexture("blue1"), this, 50);
			deco.add(d);
			d.setRotation(i * (360 / 10));
			d.setRoationSpeed(-40);
			d.setSize(140, 140);
		}
		

		for (int i = 0; i < 3; i++) {
			RotatingImage d = new RotatingImage(
					TextureFactory.getTexture("yellow1"), this, 15);
			deco.add(d);
			d.setRotation(i * (360 / 3));
			d.setRoationSpeed(60);
			d.setSize(140, 140);
		}

	}

	@Override
	protected void setStage(Stage stage) {
		for (RotatingImage r : deco) {
			stage.addActor(r);
		}
		super.setStage(stage);
	}

	@Override
	public void act(float delta) {
	}

}
