package de.det.d3t.model;
import com.badlogic.gdx.graphics.Texture;


public class LineSegment extends Entity{
	private float connectionHp = 1000;
	private Connection con;

	protected LineSegment(Texture texture, float x1, float y1, float x2, float y2) {
		super(texture);
		con = new Connection(x1, y1, x2, y2, texture, 1, 1, 100);
	}

	@Override
	public void act(float delta) {
		
	}

	public Connection getCon() {
		return con;
	}

}
