package de.det.d3t.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Particle extends Actor {

	private ParticleEffect particleEffect;
	private int x = 400;
	private int y = 600;

	public Particle(){
		createParticles();
		adjustAngle();
	}


	public void createParticles(){
		particleEffect = new ParticleEffect();
		particleEffect.load(Gdx.files.internal("effects/fireParticles.p"),Gdx.files.internal("effects"));
	}

	public void adjustAngle(){
		for (int i = 0; i < particleEffect.getEmitters().size; i++) { //get the list of emitters - things that emit particles
	        particleEffect.getEmitters().get(i).getAngle().setLow(340); //low is the minimum rotation
	        particleEffect.getEmitters().get(i).getAngle().setHigh(380); //high is the max rotation
	     }

	}


	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#draw(com.badlogic.gdx.graphics.g2d.SpriteBatch, float)
	 */
	public void draw(SpriteBatch batch, float parentAlpha) {
		particleEffect.draw(batch);
	}


	/* (non-Javadoc)
	 * @see com.badlogic.gdx.scenes.scene2d.Actor#act(float)
	 */
	@Override
	public void act(float delta) {
	    super.act(delta);
	    particleEffect.setPosition(x,y); 
	    particleEffect.update(delta);
	    particleEffect.start();
	}


	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
}
