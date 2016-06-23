package br.ufrn.imd.genesigno.signature;

import java.util.Collection;
import br.ufrn.imd.genesigno.model.Gene;

public class GeneticSignature {
	
	private Gene[] genes;
	public GeneticSignature(Collection<Gene> genes) {
		this.genes = genes.toArray(new Gene[genes.size()]);
	}
	
	public Gene[] getGenes() {
		return genes; //unsafe
	}
	
	public int getGeneCount() {
		return genes.length; //unsafe
	}
	
	public boolean isEmpty() {
		return (genes.length == 0);
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		for (Gene g: genes){
			sb.append(g.getLabel());
			sb.append('\n');
		}
		return sb.toString();
	}
}
