package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;

/**
 * Represents one level of the game, containing information about waves and such
 * 
 * @author DET1
 *
 */
public class Level {

	private String name;
	private TiledMap tiledMap;
	private int id;
	private ArrayList<Wave> waveList = new ArrayList<Wave>();

	/**
	 * Number of seconds to pass between starting the map and starting the first
	 * wave
	 */
	private float initialDelay;
	
	private Wave currentWave;
	private int curWaveId;

	public Level(String name, TiledMap tiledMap, int id, float initialDelay) {
		super();
		this.name = name;
		this.tiledMap = tiledMap;
		this.id = id;
		this.initialDelay = initialDelay;
		
	}

	public Level(String name, TiledMap tiledMap, int id,
			ArrayList<Wave> waveList) {
		super();
		this.name = name;
		this.tiledMap = tiledMap;
		this.id = id;
		this.waveList = waveList;
	}

	/**
	 * Adds the wave after the current last wave
	 * 
	 * @param wave
	 */
	public void addWave(Wave wave) {
		waveList.add(wave);
	}

	/**
	 * Adds the wave as the first wave, keeps the current first wave as seconds
	 * and so on
	 * 
	 * @param wave
	 */
	public void addFirstWave(Wave wave) {
		waveList.add(0, wave);
	}

	/**
	 * Adds the wave at position pos in the wave list. If the position doesn't
	 * exist yet the wave is put as the last wave in the list
	 * 
	 * @param wave
	 * @param pos
	 */
	public void addWaveAtPosition(Wave wave, int pos) {
		if (waveList.size() > pos) {
			waveList.add(pos, wave);
		} else {
			waveList.add(wave);
		}
	}

	/**
	 * Sets the current wave to the first wave
	 * @return The first wave, or null if the wavelist is empty
	 */
	public Wave start(){
		if(waveList.size() == 0){
			System.err.println("Level: Can not start level " + name + "[" + id + "] - Wavelist is empty");
			return null;
		}
		
		currentWave = waveList.get(0);
		curWaveId = 0;
		return currentWave;
	}
	
	/**
	 * Sets the current wave to the next wave
	 * @return The next wave, or null if there is no next wave
	 */
	public Wave nextWave(){
		curWaveId++;
		Wave nextWave = waveList.get(curWaveId);;
		if(nextWave == null){
			System.err.println("Level: Can not go to the next(" + curWaveId + "nth) wave in level " + name + "[" + id + "] - Wave is null");
			curWaveId--;
			return null;
		}
		
		currentWave = nextWave;
		return currentWave;
	}
	
	/**
	 * Returns the position of the current wave in the wave list
	 */
	public int getCurrentWavePosition(){
		return curWaveId;
	}

	/**
	 * Returns the number of waves in this level
	 */
	public int getNumberOfWaves(){
		return waveList.size();
	}
	
	public String getName() {
		return name;
	}

	public TiledMap getTiledMap() {
		return tiledMap;
	}

	public int getId() {
		return id;
	}

	public float getInitialDelay() {
		return initialDelay;
	}

	public Wave getCurrentWave() {
		return currentWave;
	}
	
	
	
}
