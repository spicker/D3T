package de.det.d3t.model;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import de.det.d3t.CollisionFactory;

public abstract class Entity extends Image {
	private static ArrayList<Entity> content = new ArrayList<Entity>();
	
	private Texture texture;
	
	protected Entity(Texture texture) {
		super(texture);
		content.add(this);
		this.texture = texture;
	}
	
	public abstract void act(float delta);
	
	public static void checkCollisions(){
		@SuppressWarnings("unchecked")
		ArrayList<Entity> temp = (ArrayList<Entity>) content.clone();
		Iterator<Entity> it1 = temp.iterator();
		while(it1.hasNext()){
			Entity a = it1.next();
			it1.remove();
			for(Entity b : temp){
				CollisionFactory.checkCollsionE(a, b);
			}
		}
	}
	
	public static boolean isPlaceable(float x, float y, float radius){
		for(Entity t : content){
			if(CollisionFactory.hasIntersect(t, x, y, radius)){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean remove() {
		content.remove(this);
		return super.remove();
	}
	
}
