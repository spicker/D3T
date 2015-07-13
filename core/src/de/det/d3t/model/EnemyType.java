package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public enum EnemyType {
	KEVIN("enemy", 100, 1,1),
	BLACKSTONE("enemyBlackStone", 100, 1.9f,1),
	//level1
	BROWNLIGHT("enemyBrownLight",100,1.9f,100f),
	METAL("enemyMetal",100,1.9f,100f),
	METAL2("enemyMetal2",100,1.9f,1),
	DISCO("enemyDisco",100,2.1f,1),
	//level2
	EYEBALL("enemyEyeball",100,1.9f,100f),
	ELECTRO("enemyElectro2",100,1.9f,1),
	FOG("enemyFog",100,1.9f,1),
	BRAIN("enemyBrain",100,2f,1.1f),
	BOSS_EYEBALL("enemyEyeballMouth",1000,1.4f,1),
	//level3
	GRASS("enemyGrass",100,1.9f,1.1f),
	MUD("enemyMud",100,1.9f,1),
	TRIANGLES("enemyTriangles",100,1.9f,1.1f),
	//level4
	//BROWN
	BROWN("enemyBrown",100,1.9f,1.1f),
	//GRASS
	PLANT("enemyPlant",100,1.9f,1.2f),
	//level5
	BLUEGLOW("enemyBlueGlow",130,1.9f,1.2f),
	//FOG
	//PLANT
	//level6
	//BLUE_GLOW
	BLUEABSTRACT("enemyBlueAbstract",130,1.9f,1.3f),
	SPIRAL("enemySpiral",130,1.9f,1.3f),
	SPIRAL2("enemySpiral2",130,1.9f,1.3f),
	SEMIBOSS_LEOPARD("enemyLeopard",1000,5f,1),
	//level7
	REDLAVA("enemyRedLava",170,1.7f,1.4f),
	REDLAVA2("enemyRedLava2",170,1.7f,1.4f),
	LAVA("enemyLava",170,1.7f,1),
	//BLACK_STONE
	BOSS_KEVIN("enemyKevin",2000,6,1),
	BOSS_SUN("enemySun",1500,5,1);
	
	
	private Texture texture;
	private float hp;
	private float scale;
	private float accelGrow;
	
	EnemyType(String texturename, float hp, float scale, float accelGrow){
		texture = TextureFactory.getTexture(texturename);
		if(texture == null){
			texture = TextureFactory.getTexture("enemy");
		}
		this.hp = hp;
		this.scale = scale;
		this.accelGrow = accelGrow;
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
	
	public float getAccelerationGrow(){
		return accelGrow;
	}
	
}
