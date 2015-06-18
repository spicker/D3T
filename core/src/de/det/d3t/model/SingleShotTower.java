package de.det.d3t.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;

public class SingleShotTower extends Tower {
	public float knockStrength = 20000;
	private float cd = 1f;
	RotatingImage deco;
	RotatingImage deco2;

	public SingleShotTower(float x, float y, float scale) {
		super(x, y, scale);
		deco = new RotatingImage(TextureFactory.getTexture("red1"), this, 130);
		deco2 = new RotatingImage(TextureFactory.getTexture("red1"), this, 130);
		deco.setBounds(0, 0, 150, 150);
		deco2.setBounds(0, 0, 150, 150);
		deco2.setRotation(180f);
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
	
	@Override
	protected void setStage(Stage stage) {
		stage.addActor(deco);
		stage.addActor(deco2);
	}
	
	public void shoot(){
		Enemy e = getNearest(Enemy.getAllEnemys());
		if(e != null){
			float targetX = e.getCenterX() - getCenterX();
			float targetY = e.getCenterY() - getCenterY();
			float length = (float) Math.sqrt(targetX * targetX + targetY * targetY);
			targetX /= length;
			targetY /= length;
			e.addForce(targetX * knockStrength, targetY * knockStrength);
		}
	}

}
