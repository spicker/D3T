package de.det.d3t.model;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;
import de.det.d3t.controller.LevelController;

public class TeleportTower extends Tower {

	private float cd = 1f;
	private float missileVel = 6000;
	private float missileSize = 100;

	private float time = cd;
	RotatingImage deco;
	RotatingImage deco2;
	RotatingImage deco3;
	RotatingImage deco4;
	RotatingImage deco5;
	RotatingImage deco6;
	RotatingImage deco7;
	RotatingImage deco8;
	RotatingImage deco9;

	public TeleportTower(float x, float y, float scale) {
		super(x, y, scale);
		deco = new RotatingImage(TextureFactory.getTexture("blue1"), this, 130/Settings.scaleConst + 15);
		// deco2 = new RotatingImage(TextureFactory.getTexture("red1"), this,
		// 130);
		deco3 = new RotatingImage(TextureFactory.getTexture("yellow1"), this, 0);
		deco4 = new RotatingImage(TextureFactory.getTexture("red1"), this, 50/Settings.scaleConst + 5);
		deco5 = new RotatingImage(TextureFactory.getTexture("red1"), this, 50/Settings.scaleConst + 5);
		deco6 = new RotatingImage(TextureFactory.getTexture("red1"), this, 50/Settings.scaleConst + 5);
		deco7 = new RotatingImage(TextureFactory.getTexture("red1"), this, 50/Settings.scaleConst + 5);
		deco8 = new RotatingImage(TextureFactory.getTexture("red1"), this, 50/Settings.scaleConst + 5);
		deco9 = new RotatingImage(TextureFactory.getTexture("red1"), this, 50/Settings.scaleConst + 5);

		deco3.setBounds(0, 0, 90, 90);
		deco4.setBounds(0, 0, 70, 70);
		deco4.setRoationSpeed(-330);
		deco5.setBounds(0, 0, 70, 70);
		deco5.setRoationSpeed(-330);
		deco5.setRotation(60);
		deco6.setBounds(0, 0, 70, 70);
		deco6.setRoationSpeed(-330);
		deco6.setRotation(120);
		deco7.setBounds(0, 0, 70, 70);
		deco7.setRoationSpeed(-330);
		deco7.setRotation(180);
		deco8.setBounds(0, 0, 70, 70);
		deco8.setRoationSpeed(-330);
		deco8.setRotation(240);
		deco9.setBounds(0, 0, 70, 70);
		deco9.setRoationSpeed(-330);
		deco9.setRotation(300);

		addComponent(deco4);
		addComponent(deco5);
		addComponent(deco6);
		addComponent(deco7);
		addComponent(deco8);
		addComponent(deco9);

		addComponent(deco);
		addComponent(deco3);

		// addComponent(deco2);
		deco.setBounds(0, 0, 185,185);
		// deco2.setBounds(0, 0, 150, 150);
		// deco2.setRotation(180f);
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
			Missile m = new Missile(
					TextureFactory.getTexture("teleportMissile"), this, target,
					missileVel, missileSize);
			getStage().addActor(m);
			m.setAction((Enemy e) -> {
				if (e != null) {
					float telX = 0;
					float telY = 0;
					LevelController lc = LevelController.getInstance();
					Random ran = new Random();
					ArrayList<Rectangle> spawnAreas = lc.getCurrentLevel()
							.getSpawnAreaList();

					telX = spawnAreas.get(ran.nextInt(spawnAreas.size()))
							.getX() + (ran.nextFloat() * 5000 - 2500);
					telY = spawnAreas.get(ran.nextInt(spawnAreas.size()))
							.getY() + (ran.nextFloat() * 5000 - 2500);
					// TODO: Anstatt (0,0) zum Spawnpunkt?
					// TODO: vorschlag: ein Punkt der auf einem gewissen Radius
					// vom Tower liegt und möglichst weit entfernt ist vom Ziel
					// oder einfahc Random auf dem Radius
					e.setPosition(telX, telY);

				}
			});
		}
	}
}
