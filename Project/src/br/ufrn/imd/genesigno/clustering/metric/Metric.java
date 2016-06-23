package br.ufrn.imd.genesigno.clustering.metric;

import br.ufrn.imd.genesigno.clustering.Datapoint;

/**
 * Represents a metric for the distance between two datapoints.
 * It is used to create the distance matrix.
 */
public interface Metric {
	
	public static final Metric EUCLIDEAN = new EuclideanDistance();
	public static final Metric SQUARED_EUCLIDEAN = new SquaredEuclideanDistance();
	public static final Metric MANHATTAN = new ManhattanDistance();
	public static final Metric MAXIMUM = new MaximumDistance();
	
	/**
	 * Finds the distance between two datapoints on the current metric. 
	 * @param p The first point
	 * @param q The second point
	 * @return The distance
	 */
	double getDistance(Datapoint p, Datapoint q);
	
	/**
	 * @return The name of the metric
	 */
	String getName();
}
