package de.det.d3t.model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class LineSegment extends Entity{
	private float connectionHp = 1000;
	private Connection con;
	private boolean removed;

	public LineSegment(Texture texture, float x1, float y1, float x2, float y2) {
		super(texture);
		con = new Connection(x1, y1, x2, y2, texture, 2, 1, 0.02f);
	}
	
	@Override
	protected void setStage(Stage stage) {
		if (stage != null)
			stage.addActor(con);
		super.setStage(stage);
	}
	
	@Override
	public boolean remove() {
		removed = true;
		con.remove();
		return super.remove();
	}

	@Override
	public void act(float delta) {
		
	}

	public Connection getCon() {
		return con;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {}

	public void setHp(float hp){
		connectionHp = hp;
		if(hp <= 0){
			remove();
		}
	}
	public float getHp(){
		return connectionHp;
	}

	public boolean isRemoved() {
		return removed;
	}
	
}
