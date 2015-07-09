package de.det.d3t.model;

import java.util.ArrayList;

import de.det.d3t.Settings;
import de.det.d3t.TextureFactory;

public class RopeTower extends Tower {

	private RopeTower connectedTower;
	private LineSegment rope;

	private RotatingImage towerTop;

	public RopeTower(float x, float y, float scale, boolean addToAgency) {
		super(x, y, scale);
		if(addToAgency){
			RopeTowerAgency.add(this);
		}

		towerTop = new RotatingImage(TextureFactory.getTexture("ropeTowerTop"),
				this, 0f);
		towerTop.setBounds(x, y, TextureFactory.getTexture("towerBackground")
				.getWidth() * scale,
				TextureFactory.getTexture("towerBackground").getHeight()
						* scale);
	}

	public boolean isConnected() {
		return (connectedTower != null);
	}

	public void connect(RopeTower otherTower) {
		if (isConnected())
			return;

		this.connectedTower = otherTower;
		otherTower.setConnectedTower(this);

	}

	@Override
	public void act(float delta) {

		if (isActive() && isConnected() && rope == null) {

			this.rope = new LineSegment(
					TextureFactory.getTexture("ropeTexture"),
					this.getCenterX(), this.getCenterY(),
					connectedTower.getCenterX(), connectedTower.getCenterY());
			connectedTower.setRope(this.rope);

			addComponent(rope);
			addComponent(towerTop);
			
			connectedTower.addComponent(connectedTower.towerTop);
		}

		if (!isActive() && rope != null) {
			rope.remove();
			rope = null;
			if (isConnected()) {
				connectedTower.setRope(null);
				connectedTower.removeComponent(connectedTower.towerTop);
			}

			removeComponent(towerTop);
		}

		super.act(delta);
	}

	@Override
	public boolean remove() {
		RopeTowerAgency.remove(this);
		disconnect();
		return super.remove();
	}

	public void disconnect() {
		if (this.rope != null) {
			removeComponent(rope);
			this.rope = null;
		}
		if (this.connectedTower != null) {
			this.connectedTower.setRope(null);
			this.connectedTower.setConnectedTower(null);
			this.connectedTower = null;
		}
	}

	public LineSegment getRope() {
		return this.rope;
	}

	public RopeTower getConnectedTower() {
		return this.connectedTower;
	}

	protected void setRope(LineSegment rope) {
		this.rope = rope;
	}

	protected void setConnectedTower(RopeTower connectedTower) {
		this.connectedTower = connectedTower;
	}

	private static class RopeTowerAgency {

		private static ArrayList<RopeTower> list = new ArrayList<RopeTower>(0);

		public static void add(RopeTower ropeTower) {
			list.add(ropeTower);

			for (RopeTower otherTower : list) {
				if (!otherTower.isConnected() && otherTower != ropeTower) {
					otherTower.connect(ropeTower);
					list.remove(otherTower);
					list.remove(ropeTower);
					return;
				}
			}
		}

		public static void remove(RopeTower ropeTower) {
			list.remove(ropeTower);
		}

	}

}
