package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.det.d3t.TextureFactory;

public class BallsOfSteel extends Actor {

	private BillardBall b1, b2;
	private LineSegment l;
	
	private float time = 4;

	public BallsOfSteel(Texture ballTexture, Texture lineTexture, Tower start,
			Enemy target, float length) {

		b1 = new BillardBall(ballTexture, start, target, 0.8f);
		b2 = new BillardBall(ballTexture, start, target, 0.8f);

		b1.setMass(1);
		b2.setMass(1);
		
		b1.setVelocity(500);
		b2.setVelocity(500);
		
		float dirX = target.getCenterX() - start.getCenterX();
		float dirY = target.getCenterY() - start.getCenterY();

		float rotX = dirY / (Math.abs(dirX) + Math.abs(dirY));
		float rotY = dirX / (Math.abs(dirX) + Math.abs(dirY));

		float offsetX = rotX * (length / 2);
		float offsetY = rotY * (length / 2);
		
		b1.setPosition(b1.getCenterX() - offsetX, b1.getCenterY() + offsetY);
		b2.setPosition(b2.getCenterX() + offsetX, b2.getCenterY() - offsetY);

		l = new LineSegment(lineTexture, b1.getCenterX(), b1.getCenterY(),
				b2.getCenterX(), b2.getCenterY());
		
		//TODO : scale l?
		
		start.getStage().addActor(b2);
		start.getStage().addActor(b1);
		start.getStage().addActor(l);
		
		System.out.println("balls b1 texturewidth: " +TextureFactory.getTexture("black1").getWidth());
		System.out.println("balls b1 radius: " +b1.getRadius());
		System.out.println("balls b1 centerx: "+b1.getCenterX()+", centery: "+b1.getCenterY());
		System.out.println("balls b1 x: "+b1.getX()+", y: "+b1.getY());
		System.out.println("balls b1 centerx: "+b1.getCenterX()+", centery: "+b1.getCenterY());
		
		System.out.println("balls b2 x: "+b2.getX()+", y: "+b2.getY());
		System.out.println("balls b2 centerx: "+b2.getCenterX()+", centery: "+b2.getCenterY());
	}

	@Override
	public void act(float delta) {

		time -= delta;

		if (Enemy.getAllEnemys().isEmpty() || time < 0) {
			//System.out.println("balls remove");
			remove();
		}

		l.getCon().setAnkers(b2.getCenterX(), b2.getCenterY(), b1.getCenterX(),
				b1.getCenterY());

		super.act(delta);
	}

	@Override
	public boolean remove() {
		b1.remove();
		b2.remove();
		l.remove();
		return super.remove();
	}
}
