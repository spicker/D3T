package de.det.d3t;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Entity extends Image {
	private static ArrayList<Entity> content = new ArrayList<Entity>();
	
	private Texture texture;
	
	protected Entity(Texture texture) {
		super(texture);
		content.add(this);
		this.texture = texture;
	}
	
	public abstract void act(float delta);
	
}
