package br.ufrn.imd.genesigno.clustering;

import java.text.DecimalFormat;
import java.util.Collection;

import br.ufrn.imd.genesigno.model.Sample;

public class DistanceMatrix {
	private double[][] matrix;
	private Sample[] samples;
	
	public DistanceMatrix(Collection<Sample> samples, double[][] matrix) {
		this.samples = samples.toArray(new Sample[samples.size()]);
		
		int n = samples.size();
		this.matrix = new double[n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				this.matrix[i][j] = matrix[i][j];
	}
	
	public double[][] getMatrix() {
		return matrix;
	}

	public Sample[] getSamples() {
		return samples;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		DecimalFormat df = new DecimalFormat("0000.00");
		int n = matrix.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sb.append(df.format(matrix[i][j]));
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
