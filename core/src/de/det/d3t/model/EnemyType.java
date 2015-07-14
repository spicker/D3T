package de.det.d3t.model;

import com.badlogic.gdx.graphics.Texture;

import de.det.d3t.TextureFactory;

public enum EnemyType {
	KEVIN("enemy", 100, 1,1,1),
	BLACKSTONE("enemyBlackStone", 100, 1.9f,4f,1),
	//level1
	BROWNLIGHT("enemyBrownLight",100,1.9f,3f,1),
	METAL("enemyMetal",100,1.9f,3f,1),
	METAL2("enemyMetal2",100,1.9f,4f,1),
	DISCO("enemyDisco",100,2.1f,4f,1),
	//level2
	EYEBALL("enemyEyeball",100,1.9f,15f,1),
	ELECTRO("enemyElectro2",100,1.9f,15f,1),
	FOG("enemyFog",100,1.9f,15f,1),
	BRAIN("enemyBrain",100,2f,15f,1),
	BOSS_EYEBALL("enemyEyeballMouth",1000,1.4f,10f,5),
	//level3
	GRASS("enemyGrass",130,1.9f,10f,1),
	MUD("enemyMud",130,1.9f,9f,1),
	TRIANGLES("enemyTriangles",130,1.9f,9f,1),
	//level4
	//BROWN
	BROWN("enemyBrown",130,1.9f,10f,1),
	//GRASS
	PLANT("enemyPlant",130,1.9f,12f,1),
	//level5
	BLUEGLOW("enemyBlueGlow",150,1.9f,15f,1),
	//FOG
	//PLANT
	//level6
	//BLUE_GLOW
	BLUEABSTRACT("enemyBlueAbstract",180,1.9f,15f,1),
	SPIRAL("enemySpiral",180,1.9f,15f,1),
	SPIRAL2("enemySpiral2",180,1.9f,13f,1),
	SEMIBOSS_LEOPARD("enemyLeopard",1000,5f,15f,22),
	//level7
	REDLAVA("enemyRedLava",220,1.7f,12f,1),
	REDLAVA2("enemyRedLava2",220,1.7f,12f,1),
	LAVA("enemyLava",220,1.7f,10f,1),
	//BLACK_STONE
	BOSS_KEVIN("enemyKevin",1800,7,2f,1000),
	BOSS_SUN("enemySun",1500,5,9f,50);
	
	
	private Texture texture;
	private float hp;
	private float scale;
	private float accelGrow;
	private float mass;
	
	EnemyType(String texturename, float hp, float scale, float accelGrow, float mass){
		texture = TextureFactory.getTexture(texturename);
		if(texture == null){
			texture = TextureFactory.getTexture("enemy");
		}
		this.hp = hp;
		this.scale = scale;
		this.accelGrow = accelGrow;
		this.mass = mass;
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

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}
	
	
	
}
