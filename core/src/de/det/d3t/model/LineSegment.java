package de.det.d3t.model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class LineSegment extends Entity{
	private float connectionHp = 1000;
	private Connection con;

	public LineSegment(Texture texture, float x1, float y1, float x2, float y2) {
		super(texture);
		con = new Connection(x1, y1, x2, y2, texture, 1, 2, 0.1f);
	}
	
	@Override
	protected void setStage(Stage stage) {
		stage.addActor(con);
		super.setStage(stage);
	}
	
	@Override
	public boolean remove() {
		con.remove();
		return super.remove();
	}

	@Override
	public void act(float delta) {
		
	}

	public Connection getCon() {
		return con;
	}

}
