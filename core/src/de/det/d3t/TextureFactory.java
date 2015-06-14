package de.det.d3t;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class TextureFactory {
		
	private static Map<String, Texture> textures = new HashMap<String, Texture>();
	private static Map<String, BitmapFont> fonts = new HashMap<String, BitmapFont>();
	
	
	public static void loadAllGameRessources(){
		addTexture("basic", new Texture("textures/pixel.png"));
		addTexture("enemy", new Texture("textures/enemy.png"));
	}
	
	public static void loadAllMenuRessources(){
		addTexture("basic", new Texture("textures/pixel.png"));
		addTexture("enemy", new Texture("textures/enemy.png"));
		
		addTexture("button_metal", new Texture("textures/ui/buttons/button_metal.png"));
		addTexture("button_metal_down", new Texture("textures/ui/buttons/button_metal_down.png"));
		addTexture("button_metal_over", new Texture("textures/ui/buttons/button_metal_over.png"));
		
		addTexture("menuBackground", new Texture(Gdx.files.internal("textures/background/bluebubbles.png")));
		
		addTexture("menuTitle", new Texture(Gdx.files.internal("textures/ui/title/D3t_title_1.png")));
		
		addFont("White" , new BitmapFont(Gdx.files.internal("fonts/whitefont.fnt"), false));
		addFont("Black", new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false));
		addFont("Alpha_Echo", new BitmapFont(Gdx.files.internal("fonts/alpha_echo.fnt"), false));
		addFont("OptionFont", new BitmapFont(Gdx.files.internal("fonts/OptionFont.fnt"), false));
		addFont("vr", new BitmapFont(Gdx.files.internal("fonts/vr.fnt"),false));
	}
	
	
	public static void loadAllFonts(){
		addFont("White" , new BitmapFont(Gdx.files.internal("fonts/whitefont.fnt"), false));
		addFont("Black", new BitmapFont(Gdx.files.internal("fonts/font.fnt"), false));
		addFont("Alpha_Echo", new BitmapFont(Gdx.files.internal("fonts/alpha_echo.fnt"), false));
		addFont("OptionFont", new BitmapFont(Gdx.files.internal("fonts/OptionFont.fnt"), false));
		addFont("berlin_sans", new BitmapFont(Gdx.files.internal("fonts/berlin_sans_250_b.fnt"),false));
		addFont("cracked_johnnie", new BitmapFont(Gdx.files.internal("fonts/cracked_johnnie_250_b.fnt"),false));
		addFont("emmet", new BitmapFont(Gdx.files.internal("fonts/emmet_250_b.fnt"),false));
		addFont("maiandra", new BitmapFont(Gdx.files.internal("fonts/maiandra_250_b.fnt"),false));
		addFont("papyrus", new BitmapFont(Gdx.files.internal("fonts/papyrus_250_b.fnt"),false));
		addFont("tempus", new BitmapFont(Gdx.files.internal("fonts/tempus_sans_250_b.fnt"),false));
		addFont("toledo", new BitmapFont(Gdx.files.internal("fonts/toledo250_b.fnt"),false));
		addFont("tz", new BitmapFont(Gdx.files.internal("fonts/tz_250_b.fnt"),false));
		addFont("vr_256b", new BitmapFont(Gdx.files.internal("fonts/vr_256_b.fnt"),false));
		addFont("vr_256w", new BitmapFont(Gdx.files.internal("fonts/vr_256_w.fnt"),false));
		addFont("vr_gradient_blue", new BitmapFont(Gdx.files.internal("fonts/vr_gradient_blue_256.fnt"),false));
		addFont("vr_gradient_green", new BitmapFont(Gdx.files.internal("fonts/vr_gradient_green_256.fnt"),false));
		addFont("vr_gradient_orange", new BitmapFont(Gdx.files.internal("fonts/vr_gradient_orange_256.fnt"),false));
		addFont("vr_outline", new BitmapFont(Gdx.files.internal("fonts/vr_outline_256_b.fnt"),false));
		addFont("vr_white", new BitmapFont(Gdx.files.internal("fonts/vr_white.fnt"),false));
		if(!fonts.containsKey("vr")){
			addFont("vr", new BitmapFont(Gdx.files.internal("fonts/vr.fnt"),false));
		}		
	}
	
	public static void loadAllButtons(){
		addTexture("button_brown", new Texture("textures/ui/buttons/button_brown.png"));
		addTexture("button_brown_down", new Texture("textures/ui/buttons/button_brown_down.png"));
		addTexture("button_brown_over", new Texture("textures/ui/buttons/button_brown_over.png"));
		
		addTexture("button_electro", new Texture("textures/ui/buttons/button_electro.png"));
		addTexture("button_electro_down", new Texture("textures/ui/buttons/button_electro_down.png"));
		addTexture("button_electro_over", new Texture("textures/ui/buttons/button_electro_over.png"));
		
		addTexture("button_metal", new Texture("textures/ui/buttons/button_metal.png"));
		addTexture("button_metal_down", new Texture("textures/ui/buttons/button_metal_down.png"));
		addTexture("button_metal_over", new Texture("textures/ui/buttons/button_metal_over.png"));
		
		addTexture("button_quest", new Texture("textures/ui/buttons/button_quest.png"));
		addTexture("button_quest_down", new Texture("textures/ui/buttons/button_quest_down.png"));
		addTexture("button_quest_over", new Texture("textures/ui/buttons/button_quest_over.png"));
		
		addTexture("button_wood", new Texture("textures/ui/buttons/button_wood.png"));
		addTexture("button_wood_down", new Texture("textures/ui/buttons/button_wood_down.png"));
		addTexture("button_wood_over", new Texture("textures/ui/buttons/button_wood_over.png"));
		
		addTexture("button_white", new Texture("textures/ui/buttons/button_white.png"));
		addTexture("button_white_down", new Texture("textures/ui/buttons/button_white_down.png"));
		addTexture("button_white_over", new Texture("textures/ui/buttons/button_white.png")); //maybe put another texture here
		
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
	
	public static BitmapFont getFont(String title)	{
		return fonts.get(title);
	}
	
	//generate Font with border and shadow
	public static BitmapFont getFont(String title, int fontSize, Color fontColor, int borderWidth, Color borderColor, Color shadowColor, int shadowOffsetX, int shadowOffsetY) {
	    FileHandle fontFile = Gdx.files.internal("fonts/"+ title +".ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = fontSize;
	    parameter.borderWidth = borderWidth;
	    parameter.borderColor = borderColor;
	    parameter.color = fontColor;
	    parameter.shadowColor = shadowColor;
	    parameter.shadowOffsetX = shadowOffsetX;
	    parameter.shadowOffsetY = shadowOffsetY;
	    BitmapFont font = generator.generateFont(parameter);
	    generator.dispose();
	    return font;
	}
	
	//generate Font with border
	public static BitmapFont getFont(String title, int fontSize, Color fontColor, int borderWidth, Color borderColor) {
	    FileHandle fontFile = Gdx.files.internal("fonts/"+ title +".ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = fontSize;
	    parameter.borderWidth = borderWidth;
	    parameter.borderColor = borderColor;
	    parameter.color = fontColor;
	    BitmapFont font = generator.generateFont(parameter);
	    generator.dispose();
	    return font;
	}
	
	//generate Font with shadow
	public static BitmapFont getFont(String title, int fontSize, Color fontColor, Color shadowColor, int shadowOffsetX, int shadowOffsetY) {
	    FileHandle fontFile = Gdx.files.internal("fonts/"+ title +".ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = fontSize;
	    parameter.color = fontColor;
	    parameter.shadowColor = shadowColor;
	    parameter.shadowOffsetX = shadowOffsetX;
	    parameter.shadowOffsetY = shadowOffsetY;
	    BitmapFont font = generator.generateFont(parameter);
	    generator.dispose();
	    return font;
	}
	
	//generate simple font
	public static BitmapFont getFont(String title, int fontSize, Color fontColor) {
	    FileHandle fontFile = Gdx.files.internal("fonts/"+ title +".ttf");
	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
	    FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	    parameter.size = fontSize;
	    parameter.color = fontColor;
	    BitmapFont font = generator.generateFont(parameter);
	    generator.dispose();
	    return font;
	}
	
	

	
	
	public static void changeImage(Image image, Texture texture) {
        TextureRegion tr;
        tr = new TextureRegion(texture);                
        TextureRegionDrawable trd = new TextureRegionDrawable(tr);
        image.setDrawable(trd);
	}
	

	

}
