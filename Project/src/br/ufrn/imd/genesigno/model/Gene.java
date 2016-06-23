package br.ufrn.imd.genesigno.model;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a Gene measurement 
 * of genetic expression for the gene in all samples
 */
public class Gene {
	
	private int index;
	private String label;
	private double[] values;
	
	/**
	 * Constructor
	 * 
	 * @param index The index for the gene
	 * @param label The name of the gene
	 * @param values An array containing the measured values.
	 * Must follow the sample index order.    
	 */
	public Gene(int index, String label, double[] values) {
		this.index = index;
		this.label = label;
		this.values = Arrays.copyOf(values, values.length);
	}
	
	/**
	 * @return The index of the gene
	 */
	int getIndex() {
		return index;
	}
	
	/**
	 * @return The label of the gene
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Gets the measured gene expression value for the sample.
	 * 
	 * @param sample A study sample
	 * @return The expression value
	 */
	public double expressionOf(Sample sample) {
		return values[sample.getIndex()];
	}
	
	/**
	 * Gets the measured gene expression value for the study group.
	 * 
	 * @param sample A study group
	 * @return An array with the expression values for the group.
	 * Order is determined by the group.
	 */
	public double[] expressionOf(Group group) {		
		int n = group.getSampleCount();
		double[] ans = new double[n];
		List<Sample> samples = group.getSamples(); 
		
		for (int i = 0; i < n; ++i) {
			ans[i] = expressionOf(samples.get(i));
		}			
		return ans;
	}
}
