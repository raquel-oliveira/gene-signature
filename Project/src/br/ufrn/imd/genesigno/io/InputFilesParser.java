package br.ufrn.imd.genesigno.io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Locale;
import java.util.Scanner;

import br.ufrn.imd.genesigno.model.Study;

public class InputFilesParser {
	private static InputFilesParser instance = new InputFilesParser();
	private String samplesFilename;
	private String valuesFileName;
	
	private InputFilesParser(){ }	

	public static InputFilesParser getInstance() {
		return instance;
	}

	@SuppressWarnings("resource")
	public Study generateStudy() throws FileNotFoundException {
		Study study = new Study();
		
		Scanner scanner = new Scanner(new FileReader(samplesFilename));
		scanner.useLocale(Locale.US);
		while (scanner.hasNext()) {
			study.addSample(scanner.next(), scanner.next());
		}

		if (study.getGroupCount() < 2)
			throw new RuntimeException("Too few groups");
		
		int n = study.getSampleCount();
		double[] values = new double[n];
		scanner = new Scanner(new FileReader(valuesFileName));
		scanner.useLocale(Locale.US);
		String label;
		while (scanner.hasNext()) {
			label = scanner.next();
			int i;
			for (i = 0; scanner.hasNextDouble(); i++) {
				values[i] = scanner.nextDouble();
			}
			if (i != n)
				throw new RuntimeException("Wrong file format.");
				
			study.addGene(label, values);
		}
		
		return study;
	}

	/* Getters and Setters */
	public String getSamplesFilename() {
		return samplesFilename;
	}
	public void setSamplesFilename(String samplesFilename) {
		this.samplesFilename = samplesFilename;
	}
	public String getValuesFileName() {
		return valuesFileName;
	}
	public void setValuesFileName(String valuesFileName) {
		this.valuesFileName = valuesFileName;
	}
}
