package br.ufrn.imd.genesigno.clustering;

import br.ufrn.imd.genesigno.model.Sample;

public class BinaryForest {
	
	private int nclusters;
	private int next;
	private DatapointCluster[] clusters;
	private boolean[] isroot;
	private int rootcount;
	
	public BinaryForest(int capacity) {
		nclusters = capacity;
		next = 0;
		clusters = new DatapointCluster[nclusters];
		rootcount = 0;
		isroot = new boolean[nclusters];
	}
	
	void create(Sample sample) {
		DatapointCluster cluster = new DatapointCluster(sample);
		if (next >= nclusters)
			throw new RuntimeException("Bad cluster allocation");
		clusters[next] = cluster;
		isroot[next] = true;
		rootcount += 1;
		next += 1;
	}
	
	void ignore(int i) {
		isroot[i] = false;
		rootcount -= 1;
	}
	
	public DatapointCluster get(int i) {
		return clusters[i];
	}
	
	int union(int i, int j, double distance) {
		clusters[next] = new DatapointCluster(get(i), get(j), distance);
		isroot[next] = true;
		isroot[i] = false;
		isroot[j] = false;
		rootcount -= 1;
		next += 1;
		return next - 1;
	}
	
	boolean isRoot(int i) {
		return isroot[i];
	}
	
	boolean isTree() {
		return (rootcount == 1);
	}

	public DatapointCluster getLast() {
		return clusters[next-1];
	}
}
