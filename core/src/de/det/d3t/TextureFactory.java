package de.det.d3t;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.TextureData.TextureDataType;

public class TextureFactory {
	public static Texture basic;
	public static Texture enemy;
	
	public static void loadAll(){
		basic = new Texture("textures/pixel.png");
		enemy = new Texture("textures/enemy.png");
	}

}
