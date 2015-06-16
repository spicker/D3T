package de.det.d3t.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import de.det.d3t.model.Level;

public class LevelController {

	private ArrayList<Level> levelList = new ArrayList<>();

	public boolean loadLevelsFromFile() {

		try {
			FileHandle fileHandle = Gdx.files.internal("configs/levels.properties");
			if(fileHandle.exists() == false){
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
			String levelName = "No name";
			String levelMapPath = "No path";
			float levelInitDelay = 0;
			

			while (curLine != null) {
				curLine = buffReader.readLine();
				
				if(curLine == null){
					break;
				}
				
				if(curLine.isEmpty())
					continue;
				
				if(curLine.equals("LEVEL")){
					inLevelTag = true;
					continue;
				}
				
				if(inLevelTag){
					if(curLine.equals("ENDLEVEL")){
						
						levelList.add(new Level(levelName, new TmxMapLoader().load("tilemap/" + levelMapPath), levelList.size(), levelInitDelay));
						
						inLevelTag = false;
						levelName = "No name";
						levelMapPath = "No path";
						levelInitDelay = 0;
						
						continue;
					}
					
					//split the two values
					String[] value = curLine.split(":");
					
					if(value[0].equals("name"))
						levelName = value[1];
					else if(value[0].equals("map"))
						levelMapPath = value[1];
					else if(value[0].equals("delay"))
						levelInitDelay = Float.parseFloat(value[1].replace(',', '.'));
				}
				else{
				//if you reach this point you have read something you werent prepared for
				System.err.println("Levels corrupted or has errors. Read: " + curLine + " without previous 'LEVEL'");
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
		}
		catch (NumberFormatException ex){
			System.err.println("Only use numbers as delay!");
			return false;
		}

		return true;
	}

}
