package de.det.d3t.controller;

import java.io.BufferedReader;

import static java.lang.Integer.parseInt;
import static java.lang.Float.parseFloat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

import de.det.d3t.Settings;
import de.det.d3t.frame.GameFrame;
import de.det.d3t.model.Enemy;
import de.det.d3t.model.Level;
import de.det.d3t.model.Wave;

public class LevelController {

	private ArrayList<Level> levelList = new ArrayList<>();

	int currentLevel = 0;
	float timer = 0;
	float limit;
	private static LevelController instance;
	boolean buildingPhase = true;

	private Stage stage;
	
	private GameFrame gameFrame;
	
	public LevelController(GameFrame gameFrame){
		this.gameFrame = gameFrame;
	}
	
	public static LevelController getInstance(){
		if(instance == null){
			instance = new LevelController();
		}
		return instance;
	}
	
	public LevelController(){
		instance = this;
	}
	public void resetLevel(){
		getCurrentLevel().remove();
		loadLevelFromFile(getCurrentLevel().getName());
		limit = getCurrentLevel().getInitialDelay();
		buildingPhase = true;
		timer = 0;
	}

	public void startGame(Stage stage) {
		this.stage = stage;

		limit = getCurrentLevel().getInitialDelay();
	}

	public void update(float delta) {
		timer += delta;

		if (buildingPhase) {
			if (timer >= limit) {
				buildingPhase = false;
			} else {
				return;
			}
		}

		if (getCurrentLevel().hasStarted() == false) {
			getCurrentLevel().start();
			timer = 0;
			if(getCurrentLevel().hasNextWave()){
				limit = getCurrentLevel().getCurrentWave().getDelayAfter();
				spawnWave(getCurrentLevel().getCurrentWave());
			}
			return;
		}

		if (timer >= limit) {
			if (getCurrentLevel().hasNextWave()) {
				Wave wave = getCurrentLevel().nextWave();
				spawnWave(wave);
				timer = 0;
				limit = wave.getDelayAfter();
			} else {
				limit = Float.POSITIVE_INFINITY;
			}
		}
		
		getCurrentLevel().updateGold();
		if(getCurrentLevel().checkWin()){
			System.out.println("Finished Level " + currentLevel);
			Settings.getLevelConquered()[currentLevel] = true;
			if(levelList.size() > currentLevel + 1){
				Settings.getLevelUnlocked()[currentLevel + 1] = true;
			}
			gameFrame.levelFinished();
		}

	}

	private void spawnWave(Wave wave) {
		Random random = new Random();
		ArrayList<Enemy> alreadySpawned = new ArrayList<>();
		System.out.println("LevelController: Spawning wave. Size: "
				+ wave.size());
		for (Enemy enemy : wave) {
			Rectangle spawnarea = getCurrentLevel().getSpawnAreaList()
					.get(random.nextInt(getCurrentLevel().getSpawnAreaList()
							.size()));
			float x = spawnarea.x + random.nextFloat() * spawnarea.width;
			float y = spawnarea.y + random.nextFloat() * spawnarea.height;
			enemy.setX(x);
			enemy.setY(y);

			while (isValidSpawn(enemy, alreadySpawned) == false) {
				enemy.setX(enemy.getX() + 100);
				enemy.setY(enemy.getY() + 100);
			}
			alreadySpawned.add(enemy);
			stage.addActor(enemy);
		}

		for (Enemy enemy : wave) {
			enemy.setAcceleration(0);
			enemy.setVelocityX(0);
			enemy.setVelocityY(0);
		}
	}

	private boolean isValidSpawn(Enemy enemy, ArrayList<Enemy> alreadySpawned) {
		for (Enemy spawnedEnemy : alreadySpawned) {
			if ((spawnedEnemy.getX() - enemy.getX() < 500 && spawnedEnemy.getX()
					- enemy.getX() > -500)){
				return false;}
			if ((spawnedEnemy.getY() - enemy.getY() < 500 && spawnedEnemy.getY()
					- enemy.getY() > -500)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Adds the level at the position in the level list. If the position is
	 * greater than the current number of levels, it will be added to the end
	 * 
	 * @param level
	 * @param pos
	 */
	public void addLevelAtPosition(Level level, int pos) {
		if (levelList.size() <= pos)
			levelList.add(pos, level);
		else
			levelList.add(level);
	}

	private boolean loadLevelFromFile(String restartedLevelName){
		try {
			FileHandle fileHandle = Gdx.files
					.internal("configs/levels.properties");
			if (fileHandle.exists() == false) {
				System.err.println("File not found: " + fileHandle.path());
				return false;
			}
			BufferedReader buffReader = new BufferedReader(fileHandle.reader());

			String curLine = buffReader.readLine();

			if (curLine == null || !curLine.equals("D3T")) {
				System.err.println("Levels file empty or corrupted");
				buffReader.close();
				return false;
			}

			boolean inLevelTag = false;
			boolean inWaveTag = false;
			String levelName = "No name";
			String levelMapPath = "No path";
			float levelInitDelay = 0;
			ArrayList<Wave> waveList = new ArrayList<>();
			ArrayList<Rectangle> spawnAreaList = new ArrayList<>();

			while (curLine != null) {
				curLine = buffReader.readLine();

				if (curLine == null) {
					break;
				}

				if (curLine.isEmpty() || curLine.startsWith("#")
						|| curLine.equals("D3T"))
					continue;

				if (curLine.equals("LEVEL")) {
					inLevelTag = true;
					continue;
				}

				if (inLevelTag) {
					if (curLine.equals("ENDLEVEL")) {
						if (levelName.equals(restartedLevelName)){
						Level curLevel = new Level(levelName,
								new TmxMapLoader().load("tilemap/"
										+ levelMapPath), levelList.size(),
								levelInitDelay);
						curLevel.setSpawnAreaList(spawnAreaList);
						for (Wave curWave : waveList){
							curWave.spawn();
							curLevel.addWave(curWave);
						}

						int i;
						for (i=0; i < levelList.size();i++){
							if (levelList.get(i).getName().equals(restartedLevelName)){
								break;
							}
						}
						levelList.remove(i);
						levelList.add(i,curLevel);
						System.out.println(curLevel.getName() + "added on Position "+i);
						}

						inLevelTag = false;
						levelName = "No name";
						levelMapPath = "No path";
						levelInitDelay = 0;

						continue;
					}

					// split the two values
					String[] value = curLine.split(":");

					if (value[0].equals("name")) {
						levelName = value[1];
						continue;
					} else if (value[0].equals("map")) {
						levelMapPath = value[1];
						continue;
					} else if (value[0].equals("delay")) {
						levelInitDelay = Float.parseFloat(value[1].replace(',',
								'.'));
						continue;
					} else if (value[0].equals("spawn")) {
						spawnAreaList.add(new Rectangle(parseFloat(value[1]),
								parseFloat(value[2]), parseFloat(value[3]),
								parseFloat(value[4])));
						continue;
					} else if (value[0].equals("wave")) {
						inWaveTag = true;
						waveList.add(new Wave(parseFloat(value[1]),
								parseFloat(value[2]), parseFloat(value[3])));
						continue;
					}
				} else {
					// if you reach this point you have read something you
					// werent prepared for
					System.err.println("Levels corrupted or has errors. Read: "
							+ curLine + " without previous 'LEVEL'");
					buffReader.close();
					return false;
				}

				if (inWaveTag) {
					if (curLine.equals("waveend")) {
						inWaveTag = false;
						continue;
					}

					// split the two values
					String[] value = curLine.split(":");
					if (value[0].equals("enemy")) {
						waveList.get(waveList.size() - 1).addMultiple(
								parseInt(value[1]));
					}
				} else {
					// if you reach this point you have read something you
					// werent prepared for
					System.err.println("Levels corrupted or has errors. Read: "
							+ curLine + " without previous 'LEVEL'");
					buffReader.close();
					return false;
				}
			}

			buffReader.close();

		} catch (IOException ex) {
			System.err
					.println("Loading levels file failed: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} catch (NumberFormatException ex) {
			System.err.println("Only use numbers as delay!");
			return false;
		}

		return true;
	}
	
	public boolean loadLevelsFromFile() {

		try {
			FileHandle fileHandle = Gdx.files
					.internal("configs/levels.properties");
			if (fileHandle.exists() == false) {
				System.err.println("File not found: " + fileHandle.path());
				return false;
			}
			BufferedReader buffReader = new BufferedReader(fileHandle.reader());

			String curLine = buffReader.readLine();

			if (curLine == null || !curLine.equals("D3T")) {
				System.err.println("Levels file empty or corrupted");
				buffReader.close();
				return false;
			}

			boolean inLevelTag = false;
			boolean inWaveTag = false;
			String levelName = "No name";
			String levelMapPath = "No path";
			float levelInitDelay = 0;
			ArrayList<Wave> waveList = new ArrayList<>();
			ArrayList<Rectangle> spawnAreaList = new ArrayList<>();

			while (curLine != null) {
				curLine = buffReader.readLine();

				if (curLine == null) {
					break;
				}

				if (curLine.isEmpty() || curLine.startsWith("#")
						|| curLine.equals("D3T"))
					continue;

				if (curLine.equals("LEVEL")) {
					inLevelTag = true;
					continue;
				}

				if (inLevelTag) {
					if (curLine.equals("ENDLEVEL")) {

						Level curLevel = new Level(levelName,
								new TmxMapLoader().load("tilemap/"
										+ levelMapPath), levelList.size(),
								levelInitDelay);
						curLevel.setSpawnAreaList(spawnAreaList);
						for (Wave curWave : waveList){
							curWave.spawn();
							curLevel.addWave(curWave);
						}

						levelList.add(curLevel);

						inLevelTag = false;
						levelName = "No name";
						levelMapPath = "No path";
						levelInitDelay = 0;

						continue;
					}

					// split the two values
					String[] value = curLine.split(":");

					if (value[0].equals("name")) {
						levelName = value[1];
						continue;
					} else if (value[0].equals("map")) {
						levelMapPath = value[1];
						continue;
					} else if (value[0].equals("delay")) {
						levelInitDelay = Float.parseFloat(value[1].replace(',',
								'.'));
						continue;
					} else if (value[0].equals("spawn")) {
						spawnAreaList.add(new Rectangle(parseFloat(value[1]),
								parseFloat(value[2]), parseFloat(value[3]),
								parseFloat(value[4])));
						continue;
					} else if (value[0].equals("wave")) {
						inWaveTag = true;
						waveList.add(new Wave(parseFloat(value[1]),
								parseFloat(value[2]), parseFloat(value[3])));
						continue;
					}
				} else {
					// if you reach this point you have read something you
					// werent prepared for
					System.err.println("Levels corrupted or has errors. Read: "
							+ curLine + " without previous 'LEVEL'");
					buffReader.close();
					return false;
				}

				if (inWaveTag) {
					if (curLine.equals("waveend")) {
						inWaveTag = false;
						continue;
					}

					// split the two values
					String[] value = curLine.split(":");
					if (value[0].equals("enemy")) {
						waveList.get(waveList.size() - 1).addMultiple(
								parseInt(value[1]));
					}
				} else {
					// if you reach this point you have read something you
					// werent prepared for
					System.err.println("Levels corrupted or has errors. Read: "
							+ curLine + " without previous 'LEVEL'");
					buffReader.close();
					return false;
				}
			}

			buffReader.close();

		} catch (IOException ex) {
			System.err
					.println("Loading levels file failed: " + ex.getMessage());
			ex.printStackTrace();
			return false;
		} catch (NumberFormatException ex) {
			System.err.println("Only use numbers as delay!");
			return false;
		}

		return true;
	}

	/**
	 * Returns the nth level in the loaded level list
	 * 
	 * @param n
	 * @return
	 */
	public Level getLevel(int n) {
		return levelList.get(n);
	}

	public Level getCurrentLevel() {
		return levelList.get(currentLevel);
	}

	public Level nextLevel() {
		currentLevel++;
		return levelList.get(currentLevel);
	}

	public boolean hasNextLevel() {
		if (levelList.size() - currentLevel >= 2)
			return true;
		else
			return false;
	}
	
	public void setCurrentLevel(int level){
		currentLevel = level;
	}

	public int getNumberOfLevels() {
		return levelList.size();
	}

	public void addLevel(Level level) {
		levelList.add(level);
	}

	public int getGold(){
		return getCurrentLevel().getGold();
	}
}
