package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public enum EnemyType {
	KEVIN("enemy", 100, 1),
	BLACKSTONE("enemyBlackStone", 100, 1.7f),
	//level1
	BROWNLIGHT("enemyBrownLight",100,1.7f),
	METAL("enemyMetal",100,1.7f),
	METAL2("enemyMetal2",100,1.7f),
	DISCO("enemyDisco",100,1.6f),
	//level2
	EYEBALL("enemyEyeball",100,1.7f),
	ELECTRO("enemyElectro2",100,1.7f),
	FOG("enemyFog",100,1.7f),
	BRAIN("enemyBrain",100,1.5f),
	BOSS_EYEBALL("enemyEyeballMouth",1000,1),
	//level3
	GRASS("enemyGrass",100,1.7f),
	MUD("enemyMud",100,1.7f),
	TRIANGLES("enemyTriangles",100,1.7f),
	//level4
	//BROWN
	BROWN("enemyBrown",100,1.7f),
	//GRASS
	PLANT("enemyPlant",100,1.7f),
	//level5
	BLUEGLOW("enemyBlueGlow",130,1.7f),
	//FOG
	//PLANT
	//level6
	//BLUE_GLOW
	BLUEABSTRACT("enemyBlueAbstract",130,1.7f),
	SPIRAL("enemySpiral",130,1.7f),
	SPIRAL2("enemySpiral2",130,1.7f),
	SEMIBOSS_LEOPARD("enemyLeopard",1000,2f),
	//level7
	REDLAVA("enemyRedLava",170,1.4f),
	REDLAVA2("enemyRedLava2",170,1.4f),
	LAVA("enemyLava",170,1.4f),
	//BLACK_STONE
	BOSS_KEVIN("enemyKevin",2000,6),
	BOSS_SUN("enemySun",1500,3);
	
	
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
