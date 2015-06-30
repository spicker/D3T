package de.det.d3t;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import de.det.d3t.model.Circle;
import de.det.d3t.model.Connection;
import de.det.d3t.model.Enemy;
import de.det.d3t.model.Entity;
import de.det.d3t.model.LineSegment;
import de.det.d3t.model.Tower;

public class CollisionFactory{
	
	public static void checkCollsionE(Entity a, Entity b){
		if(a instanceof Circle){
			if(b instanceof Circle){
				if(checkCollision((Circle) a, (Circle) b)){
					if(b instanceof Enemy && a instanceof Enemy){
						collide((Enemy) a, (Enemy) b);
					}else if(b instanceof Enemy && a instanceof Tower){
						collide((Tower) a, (Enemy) b);
					}else if(a instanceof Enemy && b instanceof Tower){
						collide((Tower) b, (Enemy) a);
					}
				}
			}
			if(a instanceof Enemy && b instanceof LineSegment){
				collide((LineSegment) b, (Enemy) a);
			}
		}else if(a instanceof LineSegment){
			if(b instanceof Enemy){
				collide((LineSegment) a, (Enemy) b);
			}
		}
	}
	
	public static boolean checkCollision(Circle a, Circle b){
		 if (a == b){
			 return false;
		 }
		 float x = a.getCenterX() - b.getCenterX();
		 float y = a.getCenterY() - b.getCenterY();
		 return Math.sqrt((x * x) + (y * y)) < a.getRadius() + b.getRadius();
	}
	public static void collide(LineSegment s, Enemy e){
		Connection con = s.getCon();
		float l2 = con.getDistance();
		float res;
//		if(l2 == 0){
//			float x = e.getCenterX() - 
//			res = distToVecSquared(v1)
//		}
//		var t = ((this.x - v1.x) * (v2.x - v1.x) + (this.y - v1.y) * (v2.y - v1.y)) / l2
//		if (t < 0) 
//			return this.distToVecSquared(v1)
//		if (t > 1) 
//			return this.distToVecSquared(v2)
//		return this.distToVecSquared( vec2( v1.x + t * (v2.x - v1.x), v1.y + t * (v2.y - v1.y) ) )
	}
	
	public static void collide(Tower a, Enemy b){
		float collX = a.getCenterX() - b.getCenterX();
		float collY = a.getCenterY() - b.getCenterY();
	    float distance = (float) Math.sqrt(collX * collX + collY * collY);
	    float mtdX = collX * (((a.getRadius() + b.getRadius()) - distance) / distance);
	    float mtdY = collY * (((a.getRadius() + b.getRadius()) - distance) / distance);
	    float im1 = 1f / 100f; 
	    float im2 = 1 / b.getMass();
	    
	    float addA = im1 / (im1 + im2);
	    float addB = im2 / (im1 + im2);
	    b.setPosition(b.getX() - (mtdX * addB), b.getY() - (mtdY * addB));
	    
	    float vX = -b.getVelocityX();
	    float vY = -b.getVelocityY();
	    float vn = new Vector2(vX, vY).dot(new Vector2(mtdX, mtdY).nor());
	    if (vn > 0.0f) return;
	    float i = 80f;//(-(1.0f - 0.94f) * vn) / (im1 + im2);
	    float impulseX = mtdX * i;
	    float impulseY = mtdY * i;
	   
	    b.setVelocityX(b.getVelocityX() - (impulseX * im2));
	    b.setVelocityY(b.getVelocityY() - (impulseY * im2));
	}
	
	
	public static void collide(Enemy a, Enemy b){
		float collX = a.getCenterX() - b.getCenterX();
		float collY = a.getCenterY() - b.getCenterY();
	    float distance = (float) Math.sqrt(collX * collX + collY * collY);
	    float mtdX = collX * (((a.getRadius() + b.getRadius()) - distance) / distance);
	    float mtdY = collY * (((a.getRadius() + b.getRadius()) - distance) / distance);
	    float im1 = 1 / a.getMass(); 
	    float im2 = 1 / b.getMass();
	    
	    float addA = im1 / (im1 + im2);
	    float addB = im2 / (im1 + im2);
	    a.setPosition(a.getX() + (mtdX * addA), a.getY() + (mtdY * addA));
	    b.setPosition(b.getX() - (mtdX * addB), b.getY() - (mtdY * addB));
	    
	    float vX = a.getVelocityX() - b.getVelocityX();
	    float vY = a.getVelocityY() - b.getVelocityY();
	    float vn = new Vector2(vX, vY).dot(new Vector2(mtdX, mtdY).nor());
	    if (vn > 0.0f) return;
	    float i = 30f;//(-(1.0f - 0.94f) * vn) / (im1 + im2);
	    float impulseX = mtdX * i;
	    float impulseY = mtdY * i;
	    
	    a.setVelocityX(a.getVelocityX() + (impulseX * im1));
	    a.setVelocityY(a.getVelocityY() + (impulseY * im1));
	    
	    b.setVelocityX(b.getVelocityX() - (impulseX * im2));
	    b.setVelocityY(b.getVelocityY() - (impulseY * im2));
	}
	
	public static boolean hasIntersect(Entity t, float x, float y, float radius){
		if(t instanceof Circle){
			Circle c = (Circle) t;
			float difX = x - c.getCenterX();
			float difY = y - c.getCenterY();
			float distance = (float) Math.sqrt(difX*difX + difY*difY);
			return distance < (radius + c.getRadius());
		}else{
			return false;
		}
	}
}
