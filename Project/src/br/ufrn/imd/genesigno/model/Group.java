package br.ufrn.imd.genesigno.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a study group, e.g. the control group. 
 */
public class Group {
	
	int index;
	private String label;
	private ArrayList<Sample> samples;
	
	/**
	 * Constructor
	 * @param index The group index
	 * @param label The group name
	 */
	public Group(int index, String label) {
		this.index = index;
		this.label = label;
		samples = new ArrayList<Sample>();
	}

	/**
	 * @return Group's index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * @return Group's name
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Add sample to the group
	 * @param sample The new sample
	 */
	void addSample(Sample sample) {
		samples.add(sample);
	}
	
	/**
	 * @return The amount of samples in the group.
	 */
	public int getSampleCount() {
		return samples.size();
	}
	
	/**
	 * @return A list with all the samples
	 */
	public List<Sample> getSamples() {
		return samples; //unsafe
	}
}
