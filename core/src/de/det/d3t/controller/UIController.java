package de.det.d3t.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

public class UIController implements InputProcessor {

	private FileHandle handle = Gdx.files.internal("textures/ui/ingame/uiNew.png");
	private Pixmap ui = new Pixmap(handle);	
	private boolean startedValid;
	private boolean startedInvalid;
	private float fx = (float)ui.getWidth() / (float)Gdx.graphics.getWidth();
	private float fy = (float)ui.getHeight()/ (float)Gdx.graphics.getHeight();
	private Color newColor;
	
	public UIController() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Color startPoint = new Color(ui.getPixel((int)(screenX*fx),(int)(screenY*fy)));
		if (startPoint.a == 0){
		startedValid = true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		startedValid = false;
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(!startedValid){
			newColor = new Color(ui.getPixel((int)(screenX*fx),(int)(screenY*fy)));
			return true;
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
