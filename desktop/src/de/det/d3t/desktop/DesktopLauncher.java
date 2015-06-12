package de.det.d3t.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.det.d3t.controller.FrameController;
import de.det.d3t.frame.MenuFrame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.fullscreen = true;
		config.height = 800;
		config.width = 1200;
		new LwjglApplication(new FrameController(), config);
	}
}

