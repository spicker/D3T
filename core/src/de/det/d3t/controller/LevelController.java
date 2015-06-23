package de.det.d3t.controller;

import java.io.BufferedReader;

import static java.lang.Integer.parseInt;
import static java.lang.Float.parseFloat;

import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import de.det.d3t.model.Enemy;
import de.det.d3t.model.Level;
import de.det.d3t.model.Wave;

public class LevelController {

	private ArrayList<Level> levelList = new ArrayList<>();

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

			while (curLine != null) {
				curLine = buffReader.readLine();

				if (curLine == null) {
					break;
				}

				if (curLine.isEmpty())
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
						for (Wave curWave : waveList)
							curLevel.addWave(curWave);

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
								parseInt(value[1]),
								new Enemy(parseInt(value[2]),
										parseInt(value[3]), parseInt(value[4]),
										true));
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

}
