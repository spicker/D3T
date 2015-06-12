package de.det.d3t;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class TextureFactory {
	
	//public static TextureFactory texturefactory= new TextureFactory();
	
	private static Map<String, Texture> textures;
	private static Map<String, BitmapFont> fonts;
	
	
	
	
	/////////////////////GameTextures/////////////////////
	//public Texture basic;
	//public static Texture enemy;
	/////////////////////GameTextures/////////////////////
	
	
	
	/////////////////////MenuTextures/////////////////////
	//public Texture menuBackground;
	//public Texture menuUIBg;
	/*public static Texture menuUIStartGameSetup;
	public static Texture menuUIStartSettings;
	public static Texture menuUIStartAbout;
	public static Texture menuUIStartLoad;
	public static Texture menuUIStartEasterEgg;*/
	

	/////////////////////MenuTextures/////////////////////
	
	
	public TextureFactory(){
		textures = new HashMap<String, Texture>();
		fonts  = new HashMap<String, BitmapFont>();
		//loadTextures();
		//loadFonts();		
	}
	
	
	
	
	public static void loadAllGameRessources(){
		//basic = new Texture("textures/pixel.png");
		//enemy = new Texture("textures/enemy.png");
		addTexture("basic", new Texture("textures/pixel.png"));
		addTexture("enemy", new Texture("textures/enemy.png"));
	}
	
	public static void loadAllMenuRessources(){
		
		addTexture("ingameMenuButtonOver", new Texture("textures/menu/ingameMenuButtonOver.png"));
		addTexture("ingameMenuButton", new Texture("textures/menu/ingameMenuButton.png"));
		addTexture("ingameMenuButtonDown", new Texture("textures/menu/ingameMenuButtonDown.png"));
		
		//addTexture("menuBackground", new Texture(Gdx.files.internal("textures/menu/menubg.png")));
		
		addFont("White" , new BitmapFont(Gdx.files.internal("fonts/whitefont.fnt"), false));
		addFont("Black", new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false));
		addFont("Alpha_Echo", new BitmapFont(Gdx.files.internal("fonts/alpha_echo.fnt"), false));
		addFont("OptionFont", new BitmapFont(Gdx.files.internal("fonts/OptionFont.fnt"), false));
		addFont("vr", new BitmapFont(Gdx.files.internal("fonts/vr.fnt"),false));
		
	}

	private static void addTexture(String title, Texture texture) {
		textures.put(title, texture);
	}
	
	public static Texture getTexture(String title)	{
		return textures.get(title);
	}
	
	private static void addFont(String title, BitmapFont font)	{
		fonts.put(title, font);
	}
	
	public static BitmapFont getFont(String _Title)
	{
		return fonts.get(_Title);
	}
	
	public static void changeImage(Image image, Texture texture) {
        TextureRegion tr;
        tr = new TextureRegion(texture);                
        TextureRegionDrawable trd = new TextureRegionDrawable(tr);
        image.setDrawable(trd);
	}
	

}
