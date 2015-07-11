package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public enum EnemyType {
	KEVIN("enemy", 100, 1),
	BLACKSTONE("enemyBlackStone", 100, 1.3f),
	//level1
	BROWNLIGHT("enemyBrownLight",100,1.3f),
	METAL("enemyMetal",100,1.3f),
	METAL2("enemyMetal2",100,1.3f),
	DISCO("enemyDisco",100,1.6f),
	//level2
	EYEBALL("enemyEyeball",100,1.3f),
	ELECTRO("enemyElectro2",100,1.3f),
	FOG("enemyFog",100,1.3f),
	BRAIN("enemyBrain",100,1.5f),
	BOSS_EYEBALL("enemyEyeballMouth",100,1),
	//level3
	GRASS("enemyGrass",100,1.3f),
	MUD("enemyMud",100,1.3f),
	TRIANGLES("enemyTriangles",100,1.3f),
	//level4
	//BROWN
	BROWN("enemyBrown",100,1.3f),
	//GRASS
	PLANT("enemyPlant",100,1.3f),
	//level5
	BLUEGLOW("enemyBlueGlow",100,1.3f),
	//FOG
	//PLANT
	//level6
	//BLUE_GLOW
	BLUEABSTRACT("enemyBlueAbstract",100,1.3f),
	SPIRAL("enemySpiral",100,1.3f),
	SPIRAL2("enemySpiral2",100,1.3f),
	SEMIBOSS_LEOPARD("enemyLeopard",100,2f),
	//level7
	REDLAVA("enemyRedLava",100,1.4f),
	REDLAVA2("enemyRedLava2",100,1.4f),
	LAVA("enemyLava",100,1.4f),
	//BLACK_STONE
	BOSS_KEVIN("enemyKevin",100,1),
	BOSS_SUN("enemySun",100,3);
	
	
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
