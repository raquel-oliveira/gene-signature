package br.ufrn.imd.genesigno.clustering;

import br.ufrn.imd.genesigno.model.Sample;

/**
 * Represents the coordinates of a sample.
 *
 */

public class Datapoint {
	private Sample sample;
	private double[] coordinates;
	
	Datapoint(Sample sample, double[] coordinates) {
		this.sample = sample;
		this.coordinates = coordinates;
	}
	
	public Sample getSample() {
		return sample;
	}
	
	public void setSample(Sample sample) {
		this.sample = sample;
	}
	
	public double[] getCoordinates() {
		return coordinates;
	}
	
	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}
	
	
}
