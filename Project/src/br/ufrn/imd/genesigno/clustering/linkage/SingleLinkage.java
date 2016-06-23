package br.ufrn.imd.genesigno.clustering.linkage;

/**
 * The distance between groups A and B is the minimum distance
 * between every point a in A and every point b in B.
 */
public class SingleLinkage implements LinkageCriteria {

	@Override
	public double combineDistance(int n1, double d1, int n2, double d2) {
		return Math.min(d1, d2);
	}

	@Override
	public String getName() {
		return "Single Linkage";
	}

}