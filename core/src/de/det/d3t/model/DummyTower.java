package de.det.d3t.model;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;

public class DummyTower extends Tower {
	public float knockStrength = 20000;
	private float cd = 1f;
	//outer circle
	RotatingImage deco;
	RotatingImage deco2;
	RotatingImage deco21;
	RotatingImage deco22;

	
	//inner center
	RotatingImage deco3;
	
	//inner circle
	RotatingImage deco4;
	RotatingImage deco5;
	RotatingImage deco6;
	RotatingImage deco7;
	RotatingImage deco8;
	RotatingImage deco9;

	public DummyTower(float x, float y, float scale) {
		super(x, y, scale);
		deco = new RotatingImage(TextureFactory.getTexture("red1"), this, 130);
		deco2 = new RotatingImage(TextureFactory.getTexture("red1"), this, 130);
		deco21 = new RotatingImage(TextureFactory.getTexture("red1"), this, 130);
		deco22 = new RotatingImage(TextureFactory.getTexture("red1"), this, 130);

		deco3 = new RotatingImage(TextureFactory.getTexture("yellow1"), this, 0);
		
		deco4 = new RotatingImage(TextureFactory.getTexture("orange1"), this, 50);
		deco5 = new RotatingImage(TextureFactory.getTexture("orange1"), this, 50);
		deco6 = new RotatingImage(TextureFactory.getTexture("orange1"), this, 50);
		deco7 = new RotatingImage(TextureFactory.getTexture("orange1"), this, 50);
		deco8 = new RotatingImage(TextureFactory.getTexture("orange1"), this, 50);
		deco9 = new RotatingImage(TextureFactory.getTexture("orange1"), this, 50);
		
		deco.setBounds(0, 0, 150, 150);
		deco2.setBounds(0, 0, 150, 150);
		deco21.setBounds(0, 0, 150, 150);
		deco22.setBounds(0, 0, 150, 150);
		deco3.setBounds(0, 0, 100, 100);
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
		deco2.setRotation(180f);
		deco21.setRotation(90f);
		deco22.setRotation(270f);

	}
	
	@Override
	public void act(float delta) {
		/*cd -= delta;
		if(cd < 0){
			cd = 1f;
			shoot();
		}*/
		super.act(delta);
	}
	
	@Override
	protected void setStage(Stage stage) {
		stage.addActor(deco);
		stage.addActor(deco2);
		stage.addActor(deco21);
		stage.addActor(deco22);
		
		stage.addActor(deco4);
		stage.addActor(deco5);
		stage.addActor(deco6);
		stage.addActor(deco7);
		stage.addActor(deco8);
		stage.addActor(deco9);
		
		stage.addActor(deco3);

	}
	
	public void shoot(){
		/*Enemy e = getNearest(Enemy.getAllEnemys());
		if(e != null){
			float targetX = e.getCenterX() - getCenterX();
			float targetY = e.getCenterY() - getCenterY();
			float length = (float) Math.sqrt(targetX * targetX + targetY * targetY);
			targetX /= length;
			targetY /= length;
			e.addForce(targetX * knockStrength, targetY * knockStrength);
		}*/
	}

}
