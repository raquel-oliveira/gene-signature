package br.ufrn.imd.genesigno.clustering;

import br.ufrn.imd.genesigno.model.Sample;


public class DatapointCluster {	
	private double distance;
	private Sample sample;
	private DatapointCluster leftCluster;
	private DatapointCluster rightCluster;
	private int npoints;
	
	public DatapointCluster(Sample sample) {
		this.sample = sample;
		this.npoints = 1;
		this.distance = 0;
	}
	public DatapointCluster(DatapointCluster left, DatapointCluster right, double distance) {
		this.leftCluster = left;
		this.rightCluster = right;
		this.npoints = left.getPointCount() + right.getPointCount();
		this.distance = distance;
	}
	
	public int getPointCount() {
		return npoints;
	}
	public Sample getSample() {
		return sample;
	}
	public DatapointCluster getLeftCluster() {
		return leftCluster;
	}
	public DatapointCluster getRightCluster() {
		return rightCluster;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

}
