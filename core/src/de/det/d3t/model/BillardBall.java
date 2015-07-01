package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public class BillardBall extends Missile {

	private float scale = 1;
	private float difX, difY, length;
	private float radius;
	private float curLength;

	public BillardBall(Texture texture, Tower start, Enemy target,
			float velocity, float radius) {
		super(texture, start, target, velocity, radius);
		setBounds(start.getCenterX() - radius / 2, start.getCenterY() - radius
				/ 2, TextureFactory.getTexture("enemy").getWidth() * scale,
				TextureFactory.getTexture("enemy").getHeight() * scale);

		this.radius = radius;

		if (target != null) {
			difX = target.getCenterX() - getX() + radius;
			difY = target.getCenterY() - getY() + radius;
			length = (float) Math.sqrt(difX * difX + difY * difY);
			curLength = length;
			System.out.println("BillardBall: " + "difX: " + difX + ", difY: "
					+ difY + ", length: " + length);
			difX /= length;
			difY /= length;
			System.out.println("BillardBall: " + " difX: " + difX + ", difY: "
					+ difY);
		} else {
			remove();
		}
	}

	@Override
	public void act(float delta) {
		curLength = (float) Math.sqrt(difX * difX + difY * difY);
		ArrayList<Enemy> list = getAllInRange(Enemy.getAllEnemys(), radius);

		if (target == null || target.getStage() == null) {
			target = start.getNearest(Enemy.getAllEnemys());

			if (target == null) {
				remove();
				return;
			}
		}
		
		if (curLength < radius + target.getRadius()) {
			System.out.println("BillardBall: " + "hit");

			action.onHit(target);
		} else {
			// System.out.println("BillardBall: "+"move");
			setPosition(getX() + difX * velocity * delta, getY() + difY
					* velocity * delta);
		}
	}

	private ArrayList<Enemy> getAllInRange(ArrayList<Enemy> list,
			float radius2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
