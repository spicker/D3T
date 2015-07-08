package de.det.d3t.model;

import java.util.ArrayList;

public class Wave extends ArrayList<Enemy> {

	private static final long serialVersionUID = -2236476536541217050L;
	private ArrayList<EnemyType> queuedEnemyList = new ArrayList<>();
	
	/**
	 * The delay in seconds after the wave before a new wave starts
	 */
	private float delayAfter;

	/**
	 * The income the player receives for killing all enemies
	 */
	private float incomeForCompletion;

	/**
	 * The income the player receives per minute
	 */
	private float incomePerKill;
	
	private int deceased = 0;
	private boolean waveCompleted = false;

	/**
	 * 
	 * @param delayAfter
	 *            The delay in seconds after the wave before a new wave starts
	 * @param incomeForCompletion
	 *            The income the player receives for killing all enemies
	 * @param incomePerKill
	 *            The income the player receives per kill
	 */
	public Wave(float delayAfter, float incomeForCompletion,
			float incomePerKill) {
		super();
		this.delayAfter = delayAfter;
		this.incomeForCompletion = incomeForCompletion;
		this.incomePerKill = incomePerKill;
	}
	
	/**
	 * Adds n standard enemies to the list
	 * 
	 * @param n
	 */
	public void addMultiple(int n) {
		for (int i = 0; i < n; i++) {
			EnemyType enemy = EnemyType.KEVIN;
			queuedEnemyList.add(enemy);
		}
	}
	
	/**
	 * Adds n enemies of given type to the list
	 * 
	 * @param n
	 * @param type
	 */
	public void addMultiple(int n, EnemyType type) {
		for (int i = 0; i < n; i++) {
			queuedEnemyList.add(type);
		}
	}
	
	public boolean isCompleted(){
		return waveCompleted;
	}
	
	public void spawn(){
		for(EnemyType values : queuedEnemyList){
			Enemy enemy = new Enemy(0, 0, values);
			add(enemy);
		}
		
	}
	
	public void complete(){
		waveCompleted = true;
	}

	public float getDelayAfter() {
		return delayAfter;
	}

	public void setDelayAfter(float delayAfter) {
		this.delayAfter = delayAfter;
	}

	public float getIncomeForCompletion() {
		return incomeForCompletion;
	}

	public void setIncomeForCompletion(float incomeForCompletion) {
		this.incomeForCompletion = incomeForCompletion;
	}

	public float getIncomePerKill() {
		return incomePerKill;
	}

	public void setIncomePerKill(float incomePerKill) {
		this.incomePerKill = incomePerKill;
	}

	public int getDeceased() {
		return deceased;
	}

	public void updateDeceased() {
		deceased = 0;
		for (Enemy e : this){
			if (e.getHp() < 0.1f){
				deceased++;
			}
		}
	}

}
