package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

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
	
	private static final int        FRAME_COLS = 1;   
    private static final int        FRAME_ROWS = 20; 
	
	Animation                       animation;        
    TextureRegion[]                 frames;        
    TextureRegion                   currentFrame;        

    float stateTime = 0f;                                     

	
	
	public Connection(float x1, float y1, float x2, float y2, Texture t, float scaleLength, float scaleWidth, float speed){
		
		TextureRegion[][] tmp = TextureRegion.split(t, t.getWidth()/FRAME_COLS, t.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(speed, frames);
		
		this.scaleWidth = scaleWidth;
		this.scaleLength = scaleLength;
		this.texture = t;
		t.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		this.speed = speed;
		
		t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		currentFrame = animation.getKeyFrame(stateTime, true);
		
		setAnkers(x1,y1,x2,y2);
		
	}
	
	public void setAnkers(float x1, float y1, float x2, float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		step = currentFrame.getRegionWidth() * scaleLength;
		width = currentFrame.getRegionHeight() * scaleWidth;
		difX = x2 - x1;
		difY = y2 - y1;
		distance = (float) Math.sqrt(difX * difX + difY * difY);
		difX /= distance;
		difY /= distance;
		angle = (float) Math.atan2(difY, difX);
		angle = (float) Math.toDegrees(angle);
		
		segments.clear();
		Sprite s = null;
		
		int c = 0;
		for (float lx = 0; lx < distance; lx += step) {
			s = new Sprite(texture);
			s.setBounds(x1 + (step * difX) * c, y1 + (step * difY) * c - width
					/ 2, step, width);
			s.setOrigin(0, width / 2f);
			s.setRotation(angle);
			segments.add(s);
			c++;
		}
		
		float stx = s.getX() + step * difX;
		float sty = s.getY() + step * difY;
		stx -= x2;
		sty -= y2;
		float distance =  (float) Math.sqrt(stx * stx + sty * sty);
		s.setU2(1 - (distance / step));
		s.setSize(step - distance, width);

		
	}
	
	public float getDistance() {
		return distance;
	}
	
	public float getX1() {
		return x1;
	}
	
	public float getY1() {
		return y1;
	}
	
	public float getX2() {
		return x2;
	}
	
	public float getY2() {
		return y2;
	}
	
	public float getLineWidth() {
		return width;
	}
	
	@Override
	public void act(float delta) {
		
		stateTime += delta;
        currentFrame = animation.getKeyFrame(stateTime, true);
        
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		for(Sprite s: segments){
			s.setRegion(currentFrame.getRegionX(), currentFrame.getRegionY(),s.getRegionWidth(), currentFrame.getRegionHeight());
			s.draw(batch);
		}
	}

	public float getScaleWidth() {
		return scaleWidth;
	}

	public void setScaleWidth(float scaleWidth) {
		this.scaleWidth = scaleWidth;
	}

	public float getScaleLength() {
		return scaleLength;
	}

	public void setScaleLength(float scaleLength) {
		this.scaleLength = scaleLength;
	}

	
}
