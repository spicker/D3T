package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.det.d3t.TextureFactory;

public class Connection extends Actor {
	private ArrayList<Sprite> segments = new ArrayList<>();
	private float scaleWidth;
	private float scaleLength;
	private Texture texture;
	private float speed;
	private float step;
	private float width;
	private float difX;
	private float difY;
	private float distance;
	private float angle;
	private float x1;
	private float y1;
	private float x2;
	private float y2;

	
	
	public Connection(float x1, float y1, float x2, float y2, Texture t, float scaleLength, float scaleWidth, float speed){
		this.scaleWidth = scaleWidth;
		this.scaleLength = scaleLength;
		this.texture = t;
		this.speed = speed;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		step = t.getWidth() * scaleLength;
		width = t.getHeight() * scaleWidth;
		difX = x2 - x1;
		difY = y2 - y1;
		distance = (float) Math.sqrt(difX * difX + difY * difY);
		difX /= distance;
		difY /= distance;
		angle = (float) Math.atan2(difY, difX);
		angle = (float) Math.toDegrees(angle);
		int c = 0;
		for (float lx = 0; lx < distance; lx += step) {
			Sprite s = new Sprite(texture);
			s.setU2(0);
			s.setV2(0);
			s.setV(1f);
			s.setU(1);
			s.setBounds(x1 + (step * difX) * c, y1 + (step * difY) * c - width
					/ 2, step, width);
			s.setOrigin(0, width / 2f);
			s.setRotation(angle);
			segments.add(s);
			c++;
		} 
	}
	
	public float getDistance() {
		return distance;
	}
	
	public void setAnkers(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		step = texture.getWidth() * scaleLength;
		width = texture.getHeight() * scaleWidth;
		difX = x2 - x1;
		difY = y2 - y1;
		distance = (float) Math.sqrt(difX * difX + difY * difY);
		difX /= distance;
		difY /= distance;
		angle = (float) Math.atan2(difY, difX);
		angle = (float) Math.toDegrees(angle);
		int c = 0;
		for (float lx = 0; lx < distance; lx += step) {
			Sprite s = new Sprite(texture);
			s.setU2(0);
			s.setV2(0);
			s.setV(1f);
			s.setU(1);
			s.setBounds(x1 + (step * difX) * c, y1 + (step * difY) * c - width
					/ 2, step, width);
			s.setOrigin(0, width / 2f);
			s.setRotation(angle);
			segments.add(s);
			c++;
		}
	}
	
	@Override
	public void act(float delta) {
		boolean first = true;
		for(Sprite s : segments){
			if(!first){
				s.setPosition(s.getX() + difX * delta * speed, s.getY() + difY * delta * speed);
			}else{
				first = false;
			}
		}
		if(segments.size() > 0){
			Sprite s = segments.get(segments.size() - 1);
			float stx = s.getX() + step * difX;
			float sty = s.getY() + step * difY;
			stx -= x2;
			sty -= y2;
			float distance =  (float) Math.sqrt(stx * stx + sty * sty);
			if(distance > step && segments.size() > this.distance / step){
				//segments.remove(s);
			}else{
				s.setU2(1 - (distance / step));
				s.setSize(step - distance, width);
			}
			
			if(segments.size() >= 2){
				s = segments.get(0);
				Sprite s2 = segments.get(1);
				stx = s2.getX() - x1;
				sty = s2.getY() - y1;
				distance =  (float) Math.sqrt(stx * stx + sty * sty);
				if(distance > step){
					s.setSize(step + 1, width);
					Sprite s3 = new Sprite(texture);
					s3.setBounds(x1, y1, distance - step, width);
					s3.setOrigin(0, width / 2f);
					s3.setRotation(angle);
					segments.add(0, s3);
				}else{
					s.setU(1 - (distance / step));
					s.setSize(step - (step - distance), width);
				}
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		for(Sprite s: segments){
			s.draw(batch);
		}
	}

}
