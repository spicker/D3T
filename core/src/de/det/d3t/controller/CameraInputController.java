package de.det.d3t.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
		float factor = 1;
		
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)){
			factor = 10;
		}
		
		cam.zoom = Math.max(0.02f, cam.zoom+(amount/100f)*factor);
		//cam.zoom += amount / 10f;
		return false;
	}

}
