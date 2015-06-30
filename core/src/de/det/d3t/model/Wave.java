package de.det.d3t.model;

import java.util.ArrayList;

public class Wave extends ArrayList<Enemy> {

	private static final long serialVersionUID = -2236476536541217050L;
	private ArrayList<EnemyValues> queuedEnemyList = new ArrayList<>();
	
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
	 * Adds n clones of enemy to the list
	 * 
	 * @param n
	 * @param enemy
	 */
	public void addMultiple(int n, float scale, float maxHp, float mass) {
		for (int i = 0; i < n; i++) {
			EnemyValues enemy = new EnemyValues();
			enemy.mass = mass;
			enemy.scale = scale;
			enemy.maxhp = maxHp;
			queuedEnemyList.add(enemy);
		}
	}
	
	/**
	 * Adds n clones of enemy to the list
	 * 
	 * @param n
	 * @param enemy
	 */
	public void addMultiple(int n) {
		for (int i = 0; i < n; i++) {
			EnemyValues enemy = new EnemyValues();
			queuedEnemyList.add(enemy);
		}
	}
	
	public boolean isCompleted(){
		return waveCompleted;
	}
	
	public void spawn(){
		for(EnemyValues values : queuedEnemyList){
			Enemy enemy = new Enemy(0, 0, 1);
			enemy.setScale(values.scale);
			enemy.setMaxHp(values.maxhp);
			enemy.setHp(values.maxhp);
			enemy.setMass(values.mass);
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
	
	private class EnemyValues{
		protected float scale = 1;
		protected float maxhp = 100;
		protected float mass = 1;
	}

}
