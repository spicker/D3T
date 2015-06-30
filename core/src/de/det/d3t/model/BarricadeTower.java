package de.det.d3t.model;

public class BarricadeTower extends Tower {
	
	
	
	public BarricadeTower(float x, float y, float scale){
		super(x,y,scale);
		
		this.maxHp *= 2;
	}
}
