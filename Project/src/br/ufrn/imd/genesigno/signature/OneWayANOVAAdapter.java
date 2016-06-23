package br.ufrn.imd.genesigno.signature;

import java.util.List;
import org.apache.commons.math3.stat.inference.*;

public class OneWayANOVAAdapter implements ManyGroupsStatisticalTest {

	private OneWayAnova test;
	
	public OneWayANOVAAdapter() {
		test = new OneWayAnova();
	}
	
	@Override
	public double pValue(List<double[]> sets) {
		return test.anovaPValue(sets);
	}

	@Override
	public String getName() {
		return "One-way ANOVA";
	}

}
