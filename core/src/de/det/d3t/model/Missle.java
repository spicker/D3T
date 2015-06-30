package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Missle extends Image {
	private Tower start;
	private Enemy target;
	private float velocity;
	private HitAction action;
	
	public Missle(Texture texture, Tower start, Enemy target, float velocity, float radius) {
		super(texture);
		this.start = start;
		this.target = target;
		this.velocity = velocity;
		setBounds(start.getCenterX() - radius / 2, start.getCenterY() - radius / 2, radius, radius);
	}
	
	public void setAction(HitAction action) {
		this.action = action;
	}
	
	@Override
	public void act(float delta) {
		float x = getX() + getWidth() / 2;
		float y = getY() + getHeight() / 2;
		if(target == null || target.getStage() == null){
			target = start.getNearest(Enemy.getAllEnemys());
			//no enemys left
			if(target == null){
				remove();
				return;
			}
		}
		float difX = target.getCenterX() - x;
		float difY = target.getCenterY() - y;	
		float length = (float) Math.sqrt(difX * difX + difY * difY);
		
		if(length < getHeight() / 2 + target.getRadius()){
			action.onHit(target);
			remove();
		}else{
			difX /= length;
			difY /= length;
			setPosition(getX() + difX * velocity * delta, getY() + difY * velocity * delta);
			super.act(delta);
		}
	}
	

}
