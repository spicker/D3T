package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import de.det.d3t.TextureFactory;

public class BlinkImage extends Actor {
	private float offset = 500;
	private Circle bind;
	private Texture texture;
	private float min;
	private float max;
	private int direction; //-1=backward  (large --> small), 0=staying, 1=forward (small --> large)
	private float animationSpeed;
	private float x;
	private float y;
	private long timeOld;
	private long timeNew;
	private int numberOfFrames;
	private int currentFrame;
	private String textureName;
	private float stateTime = 0;
	private Animation anim;
	private Image drawImage;
	/**
	 * 
	 * @param texture the texture of the first frame of the animation, numbered at the end with convention _Number, beginning with 0
	 * @param bind the main tower object to bind the Image to
	 * @param offset the offset to set the Image
	 * @param minScale the minimum Scale of the BlinkImage
	 * @param maxScale the maximum scale of the BlinkImage
	 * @param direction the direction of the scaling 
	 * @param animationSpeed the animation-speed in milliseconds
	 */
	public BlinkImage(Animation anim, Circle bind, float offset, int direction, float animationSpeed, int frames) {
		//super(anim.getKeyFrame(0));
		this.anim = anim;	
		this.offset = offset;
		this.bind = bind;
		this.direction = direction;
		this.animationSpeed = animationSpeed;	
		x = this.getX();
		y = this.getY();
		currentFrame = 1;	
		numberOfFrames = frames;
		
		timeOld = System.currentTimeMillis();
		drawImage = new Image(anim.getKeyFrame(0));
		drawImage.setBounds(bind.getCenterX() - bind.getWidth()/2, bind.getCenterY() - bind.getHeight()/2, bind.getWidth(), bind.getHeight());
	}

	
	public void setOffset(float offset) {
		this.offset = offset;
	}
	
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
		
	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getAnimationSpeed() {
		return animationSpeed;
	}

	public void setAnimationSpeed(float animationSpeed) {
		this.animationSpeed = animationSpeed;
	}

	@Override
	public void act(float delta) {
		
		super.act(delta);
		//direction -1 --> magnet tower --> inner
	    if(direction == -1){
		    if(numberOfFrames == anim.getKeyFrameIndex(stateTime)){
		    	stateTime = 0;
		    }
			stateTime = stateTime+delta;
	    }
	    //direction != -1 --> ant gravity tower --> outer
	    else{
	    	 if(anim.getKeyFrameIndex(stateTime) == 0){
			    	stateTime = 0.5f;
			    }
				stateTime = stateTime-delta;
	    }
	}


	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		drawImage.setDrawable(new TextureRegionDrawable(anim.getKeyFrame(stateTime)));
		drawImage.draw(batch, parentAlpha);
		
	}
	
	
	

	
	
	
	
}
