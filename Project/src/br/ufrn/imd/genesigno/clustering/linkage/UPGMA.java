package br.ufrn.imd.genesigno.clustering.linkage;

/**
 * The distance between groups A and B is the mean distance
 * of the distances between every point a in A and every point b in B.
 */
public class UPGMA implements LinkageCriteria {

	@Override
	public double combineDistance(int n1, double d1, int n2, double d2) {
		return (n1*d1 + n2*d2)/(n1+n2);
	}

	@Override
	public String getName() {
		return "UPGMA";
	}
}
