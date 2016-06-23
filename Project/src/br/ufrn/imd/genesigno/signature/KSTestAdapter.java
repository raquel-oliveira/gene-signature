package br.ufrn.imd.genesigno.signature;

import org.apache.commons.math3.stat.inference.*;

public class KSTestAdapter implements TwoGroupStatisticalTest {

	private KolmogorovSmirnovTest test;
	
	public KSTestAdapter() {
		test = new KolmogorovSmirnovTest();
	}
	
	@Override
	public double pValue(double[] set1, double[] set2) {
		return test.kolmogorovSmirnovTest(set1, set2);
	}

	@Override
	public String getName() {
		return "Kolmogorov-Smirnov (K-S) test";
	}

}
