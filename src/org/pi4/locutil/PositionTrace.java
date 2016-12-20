package org.pi4.locutil;

public class PositionTrace {
	private GeoPosition position;
	private double[] signals;
	
	
	public PositionTrace(GeoPosition position, double[] signals) {
		super();
		this.position = position;
		this.signals = signals;
	}
	
	public GeoPosition getPosition() {
		return position;
	}
	public void setPosition(GeoPosition position) {
		this.position = position;
	}
	public double[] getSignals() {
		return signals;
	}
	public void setSignals(double[] signals) {
		this.signals = signals;
	}
	public void setSignal(double signal, int index){
		signals[index] = signal;
	}
	
	
}
