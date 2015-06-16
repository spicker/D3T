package de.det.d3t.util;

import java.io.File;
import java.nio.ByteBuffer;
import java.lang.String;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;

public class Screenshooter {
	 private static int counter = 1;

	 public static void saveScreenshot(){
	        try{
	            FileHandle fh;
	            do{
	                fh = new FileHandle("screenshot" + counter++ + ".png");
	            }while (fh.exists());
	            Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
	            PixmapIO.writePNG(fh, pixmap);
	            pixmap.dispose();
	        }catch (Exception e){
	        }
	    }

	    private static Pixmap getScreenshot(int x, int y, int w, int h, boolean flipY){
	        Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);

	        final Pixmap pixmap = new Pixmap(w, h, Format.RGBA8888);
	        ByteBuffer pixels = pixmap.getPixels();
	        Gdx.gl.glReadPixels(x, y, w, h, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixels);

	        final int numBytes = w * h * 4;
	        byte[] lines = new byte[numBytes];
	        if (flipY){
	            pixels.clear();
	            pixels.get(lines);
	        }else{
	            final int numBytesPerLine = w * 4;
	            for (int i = 0; i < h; i++){
	                pixels.position((h - i - 1) * numBytesPerLine);
	                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
	            }
	            pixels.clear();
	            pixels.put(lines);
	        }
	        return pixmap;
	    }

}
