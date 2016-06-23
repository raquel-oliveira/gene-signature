package br.ufrn.imd.genesigno.model;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Represents a test case. Each study has samples, groups and genes.
 */
public class Study {
	private ArrayList<Sample> samples;
	private ArrayList<Gene> genes;
	private TreeMap<String, Group> groups;
	
	public Study() {
		samples = new ArrayList<Sample>();
		genes = new ArrayList<Gene>();
		groups = new TreeMap<String, Group>(); 
	}
	
	public void addSample(String label, String group_label) {
		if (getGeneCount() != 0) {
			// can't add samples anymore
		}
		
		int index = getSampleCount();
		Sample newSample = new Sample(index, label);
		samples.add(newSample);
		
		Group group = groups.get(group_label);
		if (group == null) {
			int group_index = getGroupCount();
			group = new Group(group_index, group_label);
			groups.put(group_label, group);
		}
		group.addSample(newSample);
		newSample.setGroup(group);
	}
	
	public void addGene(String label, double[] values) {
		int n = getSampleCount();
		if (n == 0) {
			// can't add genes yet
		}
		else if (values.length != n) {
			// incompatible format
		}
		
		int index = getGeneCount();
		Gene newGene = new Gene(index, label, values);
		genes.add(newGene);
	}
	
	public List<Sample> getSamples() {
		return samples; //unsafe
	}
	
	public int getSampleCount() {
		return samples.size();
	}
	
	public List<Gene> getGenes() {
		return genes; //unsafe
	}
	
	public int getGeneCount() {
		return genes.size();
	}
	
	public List<Group> getGroups() {
		return new ArrayList<Group>(groups.values());
	}
	
	public int getGroupCount() {
		return groups.size();
	}
	
	public double expressionOf(Sample s, Gene g) {
		return g.expressionOf(s);
	}
	
	public boolean isTwoGroup() {
		return (getGroupCount() == 2);
	}
}
