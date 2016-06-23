package br.ufrn.imd.genesigno.signature;

import java.util.ArrayList;
import java.util.List;

import br.ufrn.imd.genesigno.model.Group;
import br.ufrn.imd.genesigno.model.Study;
import br.ufrn.imd.genesigno.model.Gene;

/**
 * 
 *
 */
public class SignificanceFilter {
	
	private double pvalue = 0.05;
	private TwoGroupStatisticalTest selectedTwoGroupTest;
	private ManyGroupsStatisticalTest selectedManyGroupsTest;
	
	public static final TwoGroupStatisticalTest UTEST = new UTestAdapter();
	public static final TwoGroupStatisticalTest TTEST = new TTestAdapter();
	public static final TwoGroupStatisticalTest KSTEST = new KSTestAdapter();
	public static final ManyGroupsStatisticalTest ANOVA = new OneWayANOVAAdapter();
	
	public SignificanceFilter() {
		selectedManyGroupsTest = ANOVA;
	}
	
	public double getPvalue() {
		return pvalue;
	}
	
	public void setPvalue(double pvalue) {
		if (pvalue < 0 || pvalue > 1)
			throw new RuntimeException("Invalid p-value");
		this.pvalue = pvalue;
	}
	
	public TwoGroupStatisticalTest getSelectedTest() {
		return selectedTwoGroupTest;
	}
	
	public void setSelectedTest(TwoGroupStatisticalTest selectedTest) {
		this.selectedTwoGroupTest = selectedTest;
	}
	
	public GeneticSignature findSignature(Study study) {
		
		ArrayList<Gene> relevant = new ArrayList<Gene>();
		if (study.isTwoGroup()) {
			Group g1 = study.getGroups().get(0);
			Group g2 = study.getGroups().get(1);
			
			for (Gene g : study.getGenes()) {
				double p = selectedTwoGroupTest.pValue(g.expressionOf(g1), g.expressionOf(g2));  
				if (p <= pvalue) {
					relevant.add(g);
				}
			}
		}
		else {
			List<Group> groups = study.getGroups();
			for (Gene g : study.getGenes()) {
				ArrayList<double[]> exp = new ArrayList<double[]>();
				for (Group gp : groups) {
					exp.add(g.expressionOf(gp));
				}
				double p = selectedManyGroupsTest.pValue(exp);  
				if (p <= pvalue) {
					relevant.add(g);
				}
			}
		}
		return new GeneticSignature(relevant);
	}
}
