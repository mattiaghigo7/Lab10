package it.polito.tdp.rivers.model;

public class SimulationResult {

	double avgC;
	int numOfDay;
	
	public SimulationResult(double avgC, int numOfDay) {
		super();
		this.avgC = avgC;
		this.numOfDay = numOfDay;
	}

	public double getAvgC() {
		return avgC;
	}

	public void setAvgC(double avgC) {
		this.avgC = avgC;
	}

	public int getNumOfDay() {
		return numOfDay;
	}

	public void setNumOfDay(int numOfDay) {
		this.numOfDay = numOfDay;
	}
	
	
}
