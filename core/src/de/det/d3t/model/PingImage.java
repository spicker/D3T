package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PingImage extends Image {
	private float scale = 0;
	
	private AoeTower bind;
	

	public PingImage(Texture texture, float minScale, AoeTower bind) {
		super(texture);
		this.bind = bind;
		this.scale = minScale;
		
		
	}

	@Override
	public void act(float delta) {
		setBounds(bind.getCenterX() - scale / 2, bind.getCenterY() - scale / 2,
				scale, scale);
		
		scale = bind.getPingCurSize();
		super.act(delta);
	}
}
