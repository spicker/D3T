package de.det.d3t.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.tools.texturepacker.*;

public class texturePacker {
	    public static void main (String[] args) throws Exception {
	        //TexturePacker.process(inputDir, outputDir, packFileName);
	    	TexturePacker.process(Gdx.files.internal("toBePacked/").toString(), Gdx.files.internal("packed/").toString(), "pack1");
	    }
	
}
