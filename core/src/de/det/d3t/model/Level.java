package de.det.d3t.model;

import java.util.ArrayList;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;

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
	private ArrayList<Rectangle> spawnAreaList = new ArrayList<>();
	private BaseCircle base;
	private int gold = 50;
	private boolean complete = false;

	/**
	 * Number of seconds to pass between starting the map and starting the first
	 * wave
	 */
	private float initialDelay;
	
	private Wave currentWave;
	private int curWaveId = -1;

	/**
	 * Creates a new level with an empty wavelist
	 * @param name
	 * @param tiledMap
	 * @param id
	 * @param initialDelay Number of seconds to pass between starting the map and starting the first wave
	 */
	public Level(String name, TiledMap tiledMap, int id, float initialDelay) {
		super();
		this.name = name;
		this.tiledMap = tiledMap;
		this.id = id;
		this.initialDelay = initialDelay;
		base = new BaseCircle(100);
	}

	/**
	 * Creates a new level
	 * @param name
	 * @param tiledMap
	 * @param id
	 * @param waveList
	 * @param initialDelay Number of seconds to pass between starting the map and starting the first wave
	 */
	public Level(String name, TiledMap tiledMap, int id,
			ArrayList<Wave> waveList, ArrayList<Rectangle> spawnAreaList, float initialDelay) {
		super();
		this.name = name;
		this.tiledMap = tiledMap;
		this.id = id;
		this.waveList = waveList;
		this.initialDelay = initialDelay;
		this.spawnAreaList = spawnAreaList;
		base = new BaseCircle(100);
		base.setX(0);
		base.setY(0);
	}
	
	/**
	 * Creates a new level
	 * @param name
	 * @param tiledMap
	 * @param id
	 * @param waveList
	 * @param initialDelay Number of seconds to pass between starting the map and starting the first wave
	 */
	public Level(String name, TiledMap tiledMap, int id,
			ArrayList<Wave> waveList, ArrayList<Rectangle> spawnAreaList, float initialDelay, BaseCircle base) {
		super();
		this.name = name;
		this.tiledMap = tiledMap;
		this.id = id;
		this.waveList = waveList;
		this.initialDelay = initialDelay;
		this.spawnAreaList = spawnAreaList;
		this.base = base;
	}

	public Level(String name, TiledMap tiledMap, int id,
			float initialDelay, BaseCircle base) {
		super();
		this.name = name;
		this.tiledMap = tiledMap;
		this.id = id;
		this.initialDelay = initialDelay;
		this.base = base;
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
			curWaveId = 0;
			return null;
		}
		
		currentWave = waveList.get(0);
		curWaveId = 0;
		
		Settings.basePositionX = base.getCenterX();
		Settings.basePositionY = base.getCenterY();
		
		
		return currentWave;
	}
	
	public boolean hasStarted(){
		return curWaveId != -1;
	}
	
	public boolean hasNextWave(){
		if(curWaveId + 1 < waveList.size())
			return true;
		else
			return false;
	}
	
	/**
	 * Sets the current wave to the next wave
	 * @return The next wave, or null if there is no next wave
	 */
	public Wave nextWave(){
		curWaveId++;
		if(curWaveId >= waveList.size()){
			System.err.println("Level: Can not go to the next(" + curWaveId + "nth) wave in level " + name + "[" + id + "] - Wave is null");
			return null;
		}
		Wave nextWave = waveList.get(curWaveId);
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

	public ArrayList<Rectangle> getSpawnAreaList() {
		return spawnAreaList;
	}

	public void setSpawnAreaList(ArrayList<Rectangle> spawnAreaList) {
		this.spawnAreaList = spawnAreaList;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}
	
	public BaseCircle getBase() {
		return base;
	}

	public void updateGold(){
		if(complete)
			return;
		
		for (Wave v : waveList){
			int temp = v.getDeceased();
			v.updateDeceased();
			if (v.getDeceased() > temp){
				int diff = v.getDeceased() - temp;
				setGold(getGold()+(int)(diff*v.getIncomePerKill()));
			}
			if (!v.isCompleted() && v.getDeceased() == v.queueSize()){
				gold += v.getIncomeForCompletion();
				v.complete();
				
			}
			
		}
	}
	
	public boolean checkWin(){
		for(Wave v : waveList){
			if(v.isCompleted() == false){
				return false;
			}
		}
		
		complete = true;
		return true;
	}
	
	public boolean isComplete(){
		return complete;
	}
	
	public void remove(){
		for (Wave v : waveList){
			for (Enemy e : v){
				e.remove();
			}
		}
	}
	
}
