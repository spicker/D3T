package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import de.det.d3t.TextureFactory;

public class Connection extends Actor {
	ArrayList<Sprite> segments = new ArrayList<>();
	
	public Connection(float x1, float y1, float x2, float y2, Texture t, float scaleLength, float scaleWidth){
		t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		float step = t.getWidth() * scaleLength;
		float width = t.getHeight() * scaleWidth;
		float difX = x1 - x2;
		float difY = y1 - y2;
		float distance = (float) Math.sqrt(difX * difX + difY * difY);
		difX /= distance;
		difY /= distance;
		float angle = (float) Math.atan2(difY, difX);
		angle = (float) Math.toDegrees(angle);
		int c = 0;
		for(float lx = x1; lx < distance; lx += step){
			Sprite s = new Sprite(t);
			s.setU2(0);
			s.setV2(0);
			s.setV(1f);
			s.setU(1);
			s.setBounds(x1 + (step * difX) * c, y1 + (step * difX) * c, step, width);
			s.setOrigin(0, width / 2f);
			s.setRotation(angle);
			segments.add(s);
			c++;
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
