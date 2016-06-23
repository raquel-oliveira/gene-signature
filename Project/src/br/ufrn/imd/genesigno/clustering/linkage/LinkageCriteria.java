package br.ufrn.imd.genesigno.clustering.linkage;

/**
 * A linkage criteria for the clustering algorithm.
 * 
 * Assumes that the distance matrix can be updated
 * in each step of the clustering without loss of
 * information.
 */
public interface LinkageCriteria {
	
	public static final LinkageCriteria SINGLE = new SingleLinkage();
	public static final LinkageCriteria COMPLETE = new CompleteLinkage();
	public static final LinkageCriteria UPGMA = new UPGMA();
	
	/**
	 * Finds the combined distance after the union of two clusters.
	 * 
	 * Let (r), (s), (k) be clusters in the algorithm. After the union of (r)
	 * and (s) into (r, s), the new distance is ginven by
	 *  d[(k), (r, s)] = combineDistance(|r|, d[(k), (r)], |s|, d[(k), (s)]). 
	 * @param n1 The amount of points in the first cluster
	 * @param d1 The distance from the first cluster
	 * @param n2 The amount of points in the second cluster
	 * @param d2 The distance from the second cluster
	 * @return The combined distance
	 */
	double combineDistance(int n1, double d1, int n2, double d2);
	
	/**
	 * @return Criteria's name
	 */
	String getName();
}
