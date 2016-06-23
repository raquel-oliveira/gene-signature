package br.ufrn.imd.genesigno.signature;

import java.util.List;

public interface ManyGroupsStatisticalTest {
	double pValue(List<double[]> sets);
	String getName();
}
