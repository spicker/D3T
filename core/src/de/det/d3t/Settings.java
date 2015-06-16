package de.det.d3t;

public class Settings {
	public static final float aspectRatioX = 16;
	public static final float aspectRatioY = 9;
	public static final int aspectRatioMultiplier = 1000;
	public static final float viewportHeight = aspectRatioY * aspectRatioMultiplier;
	public static final float viewportWidth = aspectRatioX * aspectRatioMultiplier;
	public static final float basePositionX = 2250;
	public static final float basePositionY = 4500;
	
	private static float basePositionMenuX = 600;
	private static float basePositionMenuY = 500;
		
	private static float bgm = 1f;
	private static float sfx = 1f;
	
	public static float getBgm() {
		return bgm;
	}
	public static void setBgm(float bgm) {
		Settings.bgm = bgm;
	}
	public static float getSfx() {
		return sfx;
	}
	public static void setSfx(float sfx) {
		Settings.sfx = sfx;
	}
	public static float getBasePositionMenuX() {
		return basePositionMenuX;
	}
	public static void setBasePositionMenuX(float basePositionMenuX) {
		Settings.basePositionMenuX = basePositionMenuX;
	}
	public static float getBasePositionMenuY() {
		return basePositionMenuY;
	}
	public static void setBasePositionMenuY(float basePositionMenuY) {
		Settings.basePositionMenuY = basePositionMenuY;
	}
	
	
		

}
