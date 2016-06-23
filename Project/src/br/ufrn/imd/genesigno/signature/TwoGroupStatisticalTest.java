package br.ufrn.imd.genesigno.signature;

public interface TwoGroupStatisticalTest {
	double pValue(double[] set1, double[] set2);
	String getName();
}
