package br.ufrn.imd.genesigno.signature;

import org.apache.commons.math3.stat.inference.MannWhitneyUTest;

/**
 * Tests whether the gene expressions obtained originate
 * from populations with the same distribution.
 *
 */
public class UTestAdapter implements TwoGroupStatisticalTest {

	private MannWhitneyUTest test;
	
	public UTestAdapter() {
		test = new MannWhitneyUTest();
	}
	
	@Override
	public double pValue(double[] set1, double[] set2) {
		return test.mannWhitneyUTest(set1, set2);
	}

	@Override
	public String getName() {
		return "Mann–Whitney U test";
	}
}
