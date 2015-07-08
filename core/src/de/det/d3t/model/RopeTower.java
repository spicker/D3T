package de.det.d3t.model;

import java.util.ArrayList;

import de.det.d3t.TextureFactory;

public class RopeTower extends Tower {
	
	private RopeTower connectedTower;
	private LineSegment rope;

	public RopeTower(float x, float y, float scale) {
		super(x, y, scale);
		RopeTowerAgency.add(this);
	}

	public boolean isConnected() {
		return (connectedTower != null && rope != null
				&& connectedTower.getRope() != null && connectedTower.getRope() == this.rope);
	}

	public void connect(RopeTower otherTower) {
		if (isConnected())
			return;

		this.connectedTower = otherTower;
		otherTower.setConnectedTower(this);

		this.rope = new LineSegment(
				TextureFactory.getTexture("connectionAnim"), this.getCenterX(),
				this.getCenterY(), otherTower.getCenterX(),
				otherTower.getCenterY());
		otherTower.setRope(this.rope);
		
		this.rope.setStage(this.getStage());
	}
	
	@Override
	public boolean remove() {
		RopeTowerAgency.remove(this);
		disconnect();
		return super.remove();
	}
	
	public void disconnect() {
		if (this.rope != null) {
			this.rope.remove();
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
					return;
				}
			}
		}
		
		public static void remove(RopeTower ropeTower) {
			list.remove(ropeTower);
		}
		
	}

}
