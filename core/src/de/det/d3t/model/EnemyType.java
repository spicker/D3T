package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public enum EnemyType {
	KEVIN("enemy", 100, 1),
	BLACKSTONE("enemyBlackStone", 100, 1);
	
	
	
	private Texture texture;
	private float hp;
	private float scale;
	
	EnemyType(String texturename, float hp, float scale){
		texture = TextureFactory.getTexture(texturename);
		if(texture == null){
			texture = TextureFactory.getTexture("enemy");
		}
		this.hp = hp;
		this.scale = scale;
	}

	public Texture getTexture() {
		return texture;
	}

	public float getHp() {
		return hp;
	}

	public float getScale() {
		return scale;
	}
	
	
	
}
