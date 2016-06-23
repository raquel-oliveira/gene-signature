package br.ufrn.imd.genesigno.clustering;

import java.util.ArrayList;
import java.util.PriorityQueue;

import br.ufrn.imd.genesigno.clustering.linkage.LinkageCriteria;
import br.ufrn.imd.genesigno.clustering.metric.Metric;
import br.ufrn.imd.genesigno.core.OperationNotReadyException;
import br.ufrn.imd.genesigno.model.Gene;
import br.ufrn.imd.genesigno.model.Sample;
import br.ufrn.imd.genesigno.model.Study;
import br.ufrn.imd.genesigno.signature.GeneticSignature;


public class Clustering {
	
	private static Clustering instance;
	
	private Study study;
	private Metric metric;
	private LinkageCriteria linkageCriteria;
	private double[][] dist_matrix;
	private BinaryForest bforest; 
	private GeneticSignature signature;
	private ArrayList<Sample> samples;
	private PriorityQueue<IndexDistance> heapCluster;
	
	/**
	 * Constructor
	 */
	private Clustering() {
		heapCluster = new PriorityQueue<Clustering.IndexDistance>();
		metric = Metric.EUCLIDEAN;
		linkageCriteria = LinkageCriteria.COMPLETE;
	}

	public static Clustering getInstance() { 
		if(instance == null) instance = new Clustering();
		return instance;
	}
	
	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		if (metric == null)
			throw new RuntimeException("argument 'metric' must not be null");
		
		if(this.metric != metric)
			dist_matrix = null;
		
		this.metric = metric;
	}
	
	public LinkageCriteria getLinkageCriteria() {
		return linkageCriteria;
	}
	
	public void setLinkageCriteria(LinkageCriteria linkageCriteria) {
		if(linkageCriteria == null)
			throw new RuntimeException("argument 'linkageCriteria' must not be null");
		this.linkageCriteria = linkageCriteria;
	}

	public GeneticSignature getSignature() {
		return signature;
	}
	
	public boolean hasSignature() {
		return (signature != null);
	}

	public void setSignature(GeneticSignature signature) {
		if (signature != null && signature.getGeneCount() == 0)
			throw new RuntimeException("Signature is empty");
		this.signature = signature;
		dist_matrix = null;
	}

	public Study getStudy() {
		return study;
	}

	public boolean hasStudy() {
		return (study != null);
	}
	
	public void setStudy(Study study) {
		this.study = study;
		dist_matrix = null;
	}

	public DistanceMatrix getDistanceMatrix() {
		if (dist_matrix == null)
			return null;
		return new DistanceMatrix(study.getSamples(), dist_matrix);
	}	

	public boolean hasDistanceMatrix() {
		return (this.dist_matrix != null);
	}
	
	public void generateDistanceMatrix() {
		if (!hasStudy()) {
			throw new OperationNotReadyException("Can not generate distance matrix without study.");
		}
		if (!hasSignature()) {
			throw new OperationNotReadyException("Can not generate distance matrix without genetic signature.");
		}
		
		samples = new ArrayList<Sample>(study.getSamples());
		Gene[] genes = signature.getGenes();
		int n = study.getSampleCount() * 2;
		int g = signature.getGenes().length;
		
		
		Datapoint[] datapoints = new Datapoint[n/2];		
		for (int i = 0; i < n/2; ++i) {
			double[] coord = new double[g];
			for (int j = 0; j < g; ++j) {
				coord[j] = study.expressionOf(samples.get(i), genes[j]);
			}
			datapoints[i] = new Datapoint(samples.get(i), coord);
			
		}

		dist_matrix = new double[n][n];
		for (int i = 0; i < n/2; ++i) {
			for (int j = i; j < n/2; ++j) {
				dist_matrix[i][j] = dist_matrix[j][i] 
						= metric.getDistance(datapoints[i], datapoints[j]); 
			}
		}
	}
	
	public Dendogram cluster(){
		return clusterAndIgnore(-1);
	}
	
	public Dendogram clusterAndIgnore(int ignore_index) {
		if(!hasDistanceMatrix()) {
			throw new OperationNotReadyException("a distance matrix is required");
		}
		
		heapCluster = new PriorityQueue<Clustering.IndexDistance>();
		int n = 2 * samples.size();
		
		bforest = new BinaryForest(n-1);
		for(int i = 0; i < n/2; ++i) {
			bforest.create(samples.get(i));
			for(int j = i+1; j < n/2; ++j) {
				heapCluster.add(new IndexDistance(dist_matrix[i][j], i, j));
			}
		}
		if (ignore_index >= 0)
			bforest.ignore(ignore_index);
		
		while (!heapCluster.isEmpty() && !bforest.isTree()) {
			IndexDistance dist = heapCluster.poll();
			int i1 = dist.indexLeft;
			int i2 = dist.indexRight;
			
			if (bforest.isRoot(i1) && bforest.isRoot(i2)) {
				int j = bforest.union(i1, i2, dist_matrix[Math.min(i1, i2)][Math.max(i1, i2)]);		
				for (int k = 0; k < j; ++k) {
					if (bforest.isRoot(k)) {
						dist_matrix[k][j] = linkageCriteria
								.combineDistance(bforest.get(i1).getPointCount(),
										dist_matrix[Math.min(k, i1)][Math.max(k, i1)],
										bforest.get(i2).getPointCount(),
										dist_matrix[Math.min(k, i2)][Math.max(k, i2)]);
						heapCluster.add(new IndexDistance(dist_matrix[k][j], k, j));
					}
				}
			}
		}
		return new Dendogram(bforest.getLast());
	}
	
	class IndexDistance implements Comparable<IndexDistance> {
		double distance;
		int indexLeft;
		int indexRight;
		public IndexDistance(double d, int l, int r) {
			this.distance   = d;
			this.indexLeft  = l;
			this.indexRight = r;
		}
		@Override
		public int compareTo(IndexDistance indexDistance) {
			if(this.distance < indexDistance.distance) return -1;
			if(this.distance > indexDistance.distance) return 1;
			return 0;
		}		
	}
}