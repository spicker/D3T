package de.det.d3t.controller;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;



public class CameraInputController implements InputProcessor {
	OrthographicCamera cam;
	float lastTouchX;
	float lastTouchY;
	
	
	public CameraInputController(OrthographicCamera cam) {
		this.cam = cam;
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
		lastTouchX = screenX;
		lastTouchY = screenY;
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//System.out.println(screenX);
		cam.position.x += lastTouchX - screenX;
		lastTouchX = screenX;
		cam.position.y += screenY - lastTouchY;
		lastTouchY = screenY;
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		cam.zoom += amount / 70f;
		return false;
	}

}
