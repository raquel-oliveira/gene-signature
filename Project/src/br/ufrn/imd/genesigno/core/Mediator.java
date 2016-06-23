package br.ufrn.imd.genesigno.core;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import br.ufrn.imd.genesigno.clustering.Clustering;
import br.ufrn.imd.genesigno.clustering.Dendogram;
import br.ufrn.imd.genesigno.clustering.DistanceMatrix;
import br.ufrn.imd.genesigno.clustering.linkage.LinkageCriteria;
import br.ufrn.imd.genesigno.clustering.metric.Metric;
import br.ufrn.imd.genesigno.io.InputFilesParser;
import br.ufrn.imd.genesigno.io.OutputFilesWriter;
import br.ufrn.imd.genesigno.model.Study;
import br.ufrn.imd.genesigno.signature.GeneticSignature;
import br.ufrn.imd.genesigno.signature.SignificanceFilter;
import br.ufrn.imd.genesigno.signature.TwoGroupStatisticalTest;
import br.ufrn.imd.genesigno.validation.Validator;

/**
 *
 */

public class Mediator {
	private InputFilesParser parser;
	private Study study;
	private SignificanceFilter filter;
	private GeneticSignature signature;
	private Clustering cluster;
	private Dendogram dendogram;
	
	private static Mediator instance;
	
	public static Mediator getInstance() {
		if (instance == null)
			instance = new Mediator();
		return instance;
	}
	
	private Mediator() {
		this.parser = InputFilesParser.getInstance();
		this.filter = new SignificanceFilter();
		this.filter.setPvalue(0.05);
		this.filter.setSelectedTest(SignificanceFilter.UTEST);
		this.cluster = Clustering.getInstance();
		this.dendogram = null;		
	}
	
	public String getSamplesInputFile() {
		return parser.getSamplesFilename();
	}
	
	public void setSamplesInputFile(String filename) {
		parser.setSamplesFilename(filename);
	}
	
	public String getValuesInputFile() {
		return parser.getValuesFileName();
	}
	
	public void setValuesInputFile(String filename) {
		parser.setValuesFileName(filename);
	}
	
	public void generateStudy() throws FileNotFoundException {
		try {
			study = parser.generateStudy();
		}
		catch (RuntimeException e1) {
			signature = null;
			cluster.setSignature(null);
			cluster.setStudy(null);
			dendogram = null;
			
			throw e1;
		}
		signature = null;
		cluster.setSignature(null);
		cluster.setStudy(null);
		dendogram = null;
	}
	
	public boolean hasStudy() {
		return (study != null);
	}
	
	public Study getStudy(){
		return study;
	}
	
	public double getPvalue() {
		return filter.getPvalue();
	}
	
	public void setPvalue(double pvalue) {
		this.filter.setPvalue(pvalue);
	}
	
	public TwoGroupStatisticalTest getSelectedTest() {
		return filter.getSelectedTest();
	}
	
	public void setSelectedTest(TwoGroupStatisticalTest selectedTest) {
		this.filter.setSelectedTest(selectedTest);
	}
	public void generateSignature() {
		signature = filter.findSignature(study);
		cluster.setStudy(study);
		cluster.setSignature(signature);
	}
	
	public boolean hasSignature() {
		return (signature != null);
	}
	
	public GeneticSignature getSignature() {
		return signature;
	}
	
	public void saveGeneticSignature(){
		try {
			OutputFilesWriter.saveGeneSignature();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "File save failed: "+e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Metric getMetric() {
		return cluster.getMetric();
	}

	public void setMetric(Metric metric) {
		cluster.setMetric(metric);
	}
	
	public LinkageCriteria getLinkage(){
		return cluster.getLinkageCriteria();
	}
	
	public void setLinkage(LinkageCriteria linkage){
		cluster.setLinkageCriteria(linkage);
	}
	
	public boolean hasDistanceMatrix() {
		return cluster.hasDistanceMatrix();
	}
	
	public void generateDistanceMatrix() {
		cluster.generateDistanceMatrix();
	}
	
	public DistanceMatrix getDistanceMatrix() {
		return cluster.getDistanceMatrix();
	}
	
	public void saveDistanceMatrix(){
		try {
			OutputFilesWriter.saveDistanceMatrix();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(null, "Save matrix failed: "+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public Dendogram generateCluster(){
		dendogram = cluster.cluster(); 
		return dendogram;
	}
	
	public Dendogram getDendogram(){
		return dendogram;
	}
	
	public BufferedImage getDendogramImage(){
		return dendogram.draw();
	}
	
	public boolean validate(){
		return Validator.crossValidate();
	}

	public Dendogram generateDendogramAndIgnore(int i) {
		return cluster.clusterAndIgnore(i);
	}
}
