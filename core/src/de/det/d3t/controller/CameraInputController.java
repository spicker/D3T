package de.det.d3t.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Vector3;



public class CameraInputController implements InputProcessor {
	private OrthographicCamera cam;
	private float lastTouchX;
	private float lastTouchY;
	private float screenFactorX;
	private float screenFactorY;
	
	
	public CameraInputController(OrthographicCamera cam) {
		this.cam = cam;
		this.screenFactorX = cam.viewportWidth / Gdx.graphics.getWidth();
		this.screenFactorY = cam.viewportHeight / Gdx.graphics.getHeight();
		cam.position.set(new Vector3(26000, 25000,1));
		cam.zoom = 1.2f;
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
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//System.out.println(screenX);
		this.screenFactorX = cam.viewportWidth / Gdx.graphics.getWidth();
		this.screenFactorY = cam.viewportHeight / Gdx.graphics.getHeight();
		cam.position.x += (lastTouchX - screenX) * screenFactorX * cam.zoom;
		lastTouchX = screenX;
		cam.position.y -= (lastTouchY - screenY) * screenFactorY * cam.zoom;
		lastTouchY = screenY;
		
		System.out.println(cam.position.x + " " + cam.position.y);
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		cam.zoom = Math.max(0.1f, cam.zoom+(amount/10f));
		//cam.zoom += amount / 10f;
		return false;
	}

}
