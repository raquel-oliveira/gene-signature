package br.ufrn.imd.genesigno.clustering.metric;

import br.ufrn.imd.genesigno.clustering.Datapoint;

public class EuclideanDistance implements Metric {

	@Override
	public double getDistance(Datapoint p, Datapoint q) {
		
		double[] c1 = p.getCoordinates();
		double[] c2 = q.getCoordinates();
		
		if (c1.length != c2.length) {
			// Error
		}
		
		double d = 0;
		double diff = 0;
		for (int i = 0; i < c1.length; ++i) {
			diff = c1[i] - c2[i];
			d += diff*diff;
		}
		return Math.sqrt(d);
	}

	@Override
	public String getName() {
		return "Euclidean Distance";
	}

}
