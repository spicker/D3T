package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public enum EnemyType {
	KEVIN("enemy"),
	BLACKSTONE("enemyBlackStone");
	
	
	
	private Texture texture;
	
	EnemyType(String texturename){
		texture = TextureFactory.getTexture(texturename);
	}

	public Texture getTexture() {
		return texture;
	}
	
}
