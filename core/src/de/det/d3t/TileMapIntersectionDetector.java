package de.det.d3t;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class TileMapIntersectionDetector {
	TiledMapTileLayer mapLayer;

	public TileMapIntersectionDetector(TiledMapTileLayer mapLayer) {
		this.mapLayer = mapLayer;
	}
	
	public boolean hasIntersectAt(float x, float y){
		Cell c = mapLayer.getCell((int) (x / (Settings.viewportWidth / mapLayer.getWidth())), (int) (y / (Settings.viewportHeight / mapLayer.getHeight())));
		return c != null;
	}
}
