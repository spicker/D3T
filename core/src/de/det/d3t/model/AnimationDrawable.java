package de.det.d3t.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;


public class AnimationDrawable extends BaseDrawable{
	public final Animation anim;
	private TextureRegion currentFrame;
	private float stateTime = 0f;
	private float minwi;
	private float minhe;
	
	public AnimationDrawable(Animation anim, int minWidth, int minHeight){
	    this.anim = anim;
	    currentFrame = anim.getKeyFrame(0f);
	    setMinWidth(minWidth);
	    setMinHeight(minHeight);
	    minhe = minHeight;
	    minwi = minWidth;
	}
	
	
	public void act(float delta){
	    stateTime += delta;
	}
	
	public void reset()	{
	    stateTime = 0;
	    
	}
	
	@Override
	public void draw(Batch batch, float x, float y, float width, float height)	{
	   currentFrame = anim.getKeyFrame(stateTime, true);
	   batch.draw(currentFrame, x, y, width, height);
	}
	
}