package br.ufrn.imd.genesigno.signature;

import org.apache.commons.math3.stat.inference.*;
/**
 * Tests whether the gene expressions obtained originate
 * from populations with different average expression. 
 * This test assumes that the expression
 * follows a normal distribution in both groups.
 *
 */

public class TTestAdapter implements TwoGroupStatisticalTest {

	private TTest test;
	
	public TTestAdapter() {
		test = new TTest();
	}
	
	@Override
	public double pValue(double[] set1, double[] set2) {
		return test.tTest(set1, set2);
	}

	@Override
	public String getName() {
		return "Student's t-test";
	}

}
