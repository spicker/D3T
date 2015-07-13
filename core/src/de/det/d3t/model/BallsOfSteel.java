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

		b1 = new BillardBall(ballTexture, start, target, 0.5f);
		b2 = new BillardBall(ballTexture, start, target, 0.5f);

		b1.setMass(1);
		b2.setMass(1);

		b1.setVelocity(500);
		b2.setVelocity(500);

		float dirX = target.getX() - start.getX();
		float dirY = target.getY() - start.getY();

		float rotX = -(dirY / (Math.abs(dirX) + Math.abs(dirY)));
		float rotY = dirX / (Math.abs(dirX) + Math.abs(dirY));

		float offsetX = rotX * (length / 2);
		float offsetY = rotY * (length / 2);

		// float ballRadius = b1.getRadius();
		b1.setPosition(start.getCenterX() + offsetX - b1.getRadius(),
				start.getCenterY() + offsetY - b1.getRadius());
		b2.setPosition(start.getCenterX() - offsetX - b2.getRadius(),
				start.getCenterY() - offsetY - b2.getRadius());

		float x2 = b2.getCenterX() + rotX * b2.getRadius();
		float y2 = b2.getCenterY() + rotY * b2.getRadius();
		float x1 = b1.getCenterX() - rotX * b1.getRadius();
		float y1 = b1.getCenterY() - rotY * b1.getRadius();
		l = new LineSegment(lineTexture, x2, y2, x1, y1);

		l.getCon().setScaleWidth(0.3f);
		// TODO : scale l?

		start.getStage().addActor(b2);
		start.getStage().addActor(b1);
		start.getStage().addActor(l);

		// System.out.println("balls tower radius: " + start.getRadius());
		// System.out.println("balls start x: " + start.getX() + ", y: "
		// + start.getY());
		// System.out.println("balls tower centerx: " + start.getCenterX()
		// + ", centery: " + start.getCenterY());
		//
		// System.out.println("balls b1 texturewidth: "
		// + TextureFactory.getTexture("black1").getWidth());
		// System.out.println("balls b1 radius: " + b1.getRadius());
		// System.out.println("balls b1 centerx: " + b1.getCenterX()
		// + ", centery: " + b1.getCenterY());
		// System.out.println("balls b1 x: " + b1.getX() + ", y: " + b1.getY());
		// System.out.println("balls b1 centerx: " + b1.getCenterX()
		// + ", centery: " + b1.getCenterY());
		//
		// System.out.println("balls b2 x: " + b2.getX() + ", y: " + b2.getY());
		// System.out.println("balls b2 centerx: " + b2.getCenterX()
		// + ", centery: " + b2.getCenterY());
	}

	@Override
	public void act(float delta) {

		time -= delta;

		if (Enemy.getAllEnemys().isEmpty() || time < 0) {
			// System.out.println("balls remove");
			remove();
		}
		float dirX = b2.getX() - b1.getX();
		float dirY = b2.getY() - b1.getY();
		float rotX = dirX / (Math.abs(dirX) + Math.abs(dirY));
		float rotY = dirY / (Math.abs(dirX) + Math.abs(dirY));

		float x2 = b2.getCenterX() - rotX * b2.getRadius();
		float y2 = b2.getCenterY() - rotY * b2.getRadius();
		float x1 = b1.getCenterX() + rotX * b1.getRadius();
		float y1 = b1.getCenterY() + rotY * b1.getRadius();

		l.getCon().setAnkers(x2, y2, x1, y1);

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
