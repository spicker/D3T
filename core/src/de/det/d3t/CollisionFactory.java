package de.det.d3t;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import de.det.d3t.model.BillardBall;
import de.det.d3t.model.Circle;
import de.det.d3t.model.Connection;
import de.det.d3t.model.Enemy;
import de.det.d3t.model.Entity;
import de.det.d3t.model.LineSegment;
import de.det.d3t.model.Tower;

public class CollisionFactory {

	public static void checkCollsionE(Entity a, Entity b) {
		if (a instanceof Circle) {
			if (b instanceof Circle) {
				if (checkCollision((Circle) a, (Circle) b)) {
					if (b instanceof Enemy && a instanceof Enemy) {
						collide((Enemy) a, (Enemy) b);
					} else if (b instanceof Enemy && a instanceof Tower) {
						collide((Tower) a, (Enemy) b);
					} else if (a instanceof Enemy && b instanceof Tower) {
						collide((Tower) b, (Enemy) a);
					} else if (a instanceof Enemy && b instanceof BillardBall) {
						collide((Enemy) a, (BillardBall) b);
					} else if (b instanceof Enemy && a instanceof BillardBall) {
						collide((Enemy) b, (BillardBall) a);
					}
				}
			}
			if (a instanceof Enemy && b instanceof LineSegment) {
				collide((LineSegment) b, (Enemy) a);
			}
		} else if (a instanceof LineSegment) {
			if (b instanceof Enemy) {
				collide((LineSegment) a, (Enemy) b);
			}
		}
	}

	public static boolean checkCollision(Circle a, Circle b) {
		if (a == b) {
			return false;
		}
		float x = a.getCenterX() - b.getCenterX();
		float y = a.getCenterY() - b.getCenterY();
		return Math.sqrt((x * x) + (y * y)) < a.getRadius() + b.getRadius();
	}

	public static void collide(LineSegment s, Enemy e) {
		Connection con = s.getCon();
		float l2 = con.getDistance();
		l2 *= l2;
		float dist;
		if(l2 == 0){
			float difX = e.getCenterX() - con.getX1();
			float difY = e.getCenterY() - con.getY1();
			dist = (float) Math.sqrt(difX * difX + difY * difY);
		}else{
			float t = ((e.getCenterX() - con.getX1()) * (con.getX2() - con.getX1()) + 
					(e.getCenterY() - con.getY1()) * (con.getY2() - con.getY1())) / l2;
			if(t < 0){
				float difX = e.getCenterX() - con.getX1();
				float difY = e.getCenterY() - con.getY1();
				dist = (float) Math.sqrt(difX * difX + difY * difY);
			}else if(t > 1){ 
				float difX = e.getCenterX() - con.getX2();
				float difY = e.getCenterY() - con.getY2();
				dist = (float) Math.sqrt(difX * difX + difY * difY);
			}else{
				float wurstX = con.getX1() + t * (con.getX2() - con.getX1());
				float wurstY = con.getY1() + t * (con.getY2() - con.getY1());
				float difX = e.getCenterX() - wurstX;
				float difY = e.getCenterY() - wurstY;
				dist = (float) Math.sqrt(difX * difX + difY * difY);
			}
		}
		if(dist < con.getLineWidth() / 2 + e.getRadius()){
			float conNorX = con.getX1() - con.getX2();
			float conNorY = con.getY1() - con.getY2();
			float length = (float) Math.sqrt(conNorX * conNorX + conNorY * conNorY);
			conNorX /= length;
			conNorY /= length;
			//check which side the ball hits based on the balls velocity
			float dot = conNorX * e.getVelocityX() + conNorY * e.getVelocityY();
			//check which side the ball hits based on the balls velocity
			if(Math.sqrt(e.getVelocityX() * e.getVelocityX() + e.getVelocityY() * e.getVelocityY()) * Gdx.graphics.getDeltaTime() < (con.getWidth() / 2 + e.getRadius())){
				float wurstX = con.getX1() - e.getCenterX();
				float wurstY = con.getY1() - e.getCenterY();
				dot = wurstX * conNorX + wurstY * conNorY;
				//System.out.println("wurst is love wurst is life");
			}
			float tempX = conNorX;
			if(dot > 0){
				conNorX = -conNorY;
				conNorY = tempX;
			} else {
				conNorX = conNorY;
				conNorY = -tempX;
			}
			e.setPosition(e.getX() - conNorX * (dist - (con.getLineWidth() / 2 + e.getRadius())), 
					e.getY() - conNorY * (dist - (con.getLineWidth() / 2 + e.getRadius())));
			dot = conNorX * e.getVelocityX() + conNorY * e.getVelocityY();
			float newVelX = -2 * dot * conNorX + e.getVelocityX();
			float newVelY = -2 * dot * conNorY + e.getVelocityY();
			e.setVelocityX(newVelX);
			e.setVelocityY(newVelY);
		}
	}

	public static void collide(Tower a, Enemy b) {
		if(a.isActive() == false){
			return;
		}
		float collX = a.getCenterX() - b.getCenterX();
		float collY = a.getCenterY() - b.getCenterY();
		float distance = (float) Math.sqrt(collX * collX + collY * collY);
		float mtdX = collX
				* (((a.getRadius() + b.getRadius()) - distance) / distance);
		float mtdY = collY
				* (((a.getRadius() + b.getRadius()) - distance) / distance);
		float im1 = 1f / 100f;
		float im2 = 1 / b.getMass();

		float addA = im1 / (im1 + im2);
		float addB = im2 / (im1 + im2);
		b.setPosition(b.getX() - (mtdX * addB), b.getY() - (mtdY * addB));

		float vX = -b.getVelocityX();
		float vY = -b.getVelocityY();
		float vn = new Vector2(vX, vY).dot(new Vector2(mtdX, mtdY).nor());
		if (vn > 0.0f)
			return;
		float i = 80f;// (-(1.0f - 0.94f) * vn) / (im1 + im2);
		float impulseX = mtdX * i;
		float impulseY = mtdY * i;

		b.setVelocityX(b.getVelocityX() - (impulseX * im2));
		b.setVelocityY(b.getVelocityY() - (impulseY * im2));
	}

	public static void collide(Enemy a, Enemy b) {
		float collX = a.getCenterX() - b.getCenterX();
		float collY = a.getCenterY() - b.getCenterY();
		float distance = (float) Math.sqrt(collX * collX + collY * collY);
		float mtdX = collX
				* (((a.getRadius() + b.getRadius()) - distance) / distance);
		float mtdY = collY
				* (((a.getRadius() + b.getRadius()) - distance) / distance);
		float im1 = 1 / a.getMass();
		float im2 = 1 / b.getMass();

		float addA = im1 / (im1 + im2);
		float addB = im2 / (im1 + im2);
		a.setPosition(a.getX() + (mtdX * addA), a.getY() + (mtdY * addA));
		b.setPosition(b.getX() - (mtdX * addB), b.getY() - (mtdY * addB));

		float vX = a.getVelocityX() - b.getVelocityX();
		float vY = a.getVelocityY() - b.getVelocityY();
		float vn = new Vector2(vX, vY).dot(new Vector2(mtdX, mtdY).nor());
		if (vn > 0.0f)
			return;
		float i = 30f;// (-(1.0f - 0.94f) * vn) / (im1 + im2);
		float impulseX = mtdX * i;
		float impulseY = mtdY * i;

		a.setVelocityX(a.getVelocityX() + (impulseX * im1));
		a.setVelocityY(a.getVelocityY() + (impulseY * im1));

		b.setVelocityX(b.getVelocityX() - (impulseX * im2));
		b.setVelocityY(b.getVelocityY() - (impulseY * im2));
	}

	public static void collide(Enemy a, BillardBall b) {
		float collX = a.getCenterX() - b.getCenterX();
		float collY = a.getCenterY() - b.getCenterY();
		float distance = (float) Math.sqrt(collX * collX + collY * collY);
		float mtdX = collX
				* (((a.getRadius() + b.getRadius()) - distance) / distance);
		float mtdY = collY
				* (((a.getRadius() + b.getRadius()) - distance) / distance);
		float im1 = 1 / a.getMass();
		float im2 = 1 / b.getMass();

		float addA = im1 / (im1 + im2);
		float addB = im2 / (im1 + im2);
		a.setPosition(a.getX() + (mtdX * addA), a.getY() + (mtdY * addA));
		b.setPosition(b.getX() - (mtdX * addB), b.getY() - (mtdY * addB));

		float vX = a.getVelocityX() - b.getVelocityX();
		float vY = a.getVelocityY() - b.getVelocityY();
		float vn = new Vector2(vX, vY).dot(new Vector2(mtdX, mtdY).nor());
		if (vn > 0.0f)
			return;
		float i = 30f;// (-(1.0f - 0.94f) * vn) / (im1 + im2);
		float impulseX = mtdX * i;
		float impulseY = mtdY * i;

		a.setVelocityX(a.getVelocityX() + (impulseX * im1));
		a.setVelocityY(a.getVelocityY() + (impulseY * im1));

		b.setVelocityX(b.getVelocityX() - (impulseX * im2));
		b.setVelocityY(b.getVelocityY() - (impulseY * im2));
	}

	public static boolean hasIntersect(Entity t, float x, float y, float radius) {
		if (t instanceof Circle) {
			Circle c = (Circle) t;
			float difX = (x - radius / 2) - c.getCenterX();
			float difY = (y - radius / 2) - c.getCenterY();
			float distance = (float) Math.sqrt(difX * difX + difY * difY);
			return distance < (radius + c.getRadius());
		} else {
			return false;
		}
	}
}
