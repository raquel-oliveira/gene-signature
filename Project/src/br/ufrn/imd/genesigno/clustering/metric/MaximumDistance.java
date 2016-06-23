package br.ufrn.imd.genesigno.clustering.metric;

import br.ufrn.imd.genesigno.clustering.Datapoint;

public class MaximumDistance implements Metric {

	@Override
	public double getDistance(Datapoint p, Datapoint q) {
		double[] c1 = p.getCoordinates();
		double[] c2 = q.getCoordinates();
		
		if (c1.length != c2.length) {
			// Error
		}
		
		double d = 0;
		for (int i = 0; i < c1.length; ++i) {
			d = Math.max(d, Math.abs(c1[i] - c2[i]));
		}
		return d;
	}

	@Override
	public String getName() {
		return "Maximum Distance";
	}

}
