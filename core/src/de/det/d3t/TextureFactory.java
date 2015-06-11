package de.det.d3t;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;


public class TextureFactory {
	
	/////////////////////GameTextures/////////////////////
	public static Texture basic;
	public static Texture enemy;
	/////////////////////GameTextures/////////////////////
	
	
	
	/////////////////////MenuTextures/////////////////////
	public static Texture menuBackground;
	public static Texture menuUIBg;
	/*public static Texture menuUIStartGameSetup;
	public static Texture menuUIStartSettings;
	public static Texture menuUIStartAbout;
	public static Texture menuUIStartLoad;
	public static Texture menuUIStartEasterEgg;*/
	/////////////////////MenuTextures/////////////////////
	
	
	
	public static void loadAllGameTextures(){
		basic = new Texture("textures/pixel.png");
		enemy = new Texture("textures/enemy.png");
	}
	
	public static void loadAllMenuTextures(){
		//TODO: add textures for menu
		
	}

	

}
