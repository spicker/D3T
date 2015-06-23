package de.det.d3t.model;

import java.util.ArrayList;

public class Wave extends ArrayList<Enemy> {

	private static final long serialVersionUID = -2236476536541217050L;

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
	private float incomePerMinute;

	/**
	 * 
	 * @param delayAfter
	 *            The delay in seconds after the wave before a new wave starts
	 * @param incomeForCompletion
	 *            The income the player receives for killing all enemies
	 * @param incomePerMinute
	 *            The income the player receives per minute
	 */
	public Wave(float delayAfter, float incomeForCompletion,
			float incomePerMinute) {
		super();
		this.delayAfter = delayAfter;
		this.incomeForCompletion = incomeForCompletion;
		this.incomePerMinute = incomePerMinute;
	}

	/**
	 * Adds n clones of enemy to the list
	 * 
	 * @param n
	 * @param enemy
	 */
	public void addMultiple(int n, Enemy enemy) {
		for (int i = 0; i < n; i++) {
			Enemy clonedEnemy = new Enemy(enemy.getCenterX(),
					enemy.getCenterY(), enemy.getScaleX(), enemy.isIngame());
			add(clonedEnemy);
		}
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

	public float getIncomePerMinute() {
		return incomePerMinute;
	}

	public void setIncomePerMinute(float incomePerMinute) {
		this.incomePerMinute = incomePerMinute;
	}

}
