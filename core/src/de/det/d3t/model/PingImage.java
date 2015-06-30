package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class PingImage extends Image {
	private float scale = 0;
	private Texture texture;
	private AoeTower bind;
	private float maxScale;
	private float minScale;
	private float pingTime;
	private float time;
	private float pingDuration;

	public PingImage(Texture texture, float minScale, float maxScale,
			float pingTime,float pingDuration, AoeTower bind) {
		super(texture);
		this.bind = bind;
		this.scale = minScale;
		this.maxScale = maxScale;
		this.minScale = minScale;
		this.texture = texture;
		this.pingDuration = pingDuration;
		this.pingTime = pingTime;

	}

	@Override
	public void act(float delta) {
		setBounds(bind.getCenterX() - scale / 2, bind.getCenterY() - scale / 2,
				scale, scale);
		
		scale = bind.getPingCurSize();
		super.act(delta);
	}
}
