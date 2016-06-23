package br.ufrn.imd.genesigno.io;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

import br.ufrn.imd.genesigno.clustering.Dendogram;
import br.ufrn.imd.genesigno.core.Mediator;
import br.ufrn.imd.genesigno.signature.GeneticSignature;

/**
 * Save output files: dendogram image, distance matrix and gene signature
 *
 */
public class OutputFilesWriter {
	
	/**
	 * Saves the dendogram Image into a png image.
	 * @param img dendogram Image created is the Mediator when distance Matrix is created
	 * @throws IOException
	 */
	public static void saveDendogramImage(BufferedImage img) throws IOException{
		JFileChooser saveImage = new JFileChooser();
		saveImage.setFileFilter(new PngSaveFilter());
		int result = saveImage.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION){
			File outputFile = saveImage.getSelectedFile();
			BufferedImage image = img;
			ImageIO.write(image, "png", outputFile);
		}
	}
	
	/**
	 * Saves the distance Matrix into a file.
	 * @throws IOException
	 */
	
	public static void saveDistanceMatrix() throws IOException{
		JFileChooser saveMatrix = new JFileChooser();
		int result = saveMatrix.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION){
			File outputFile = saveMatrix.getSelectedFile();
			FileWriter writer = new FileWriter(outputFile);
			writer.write(Mediator.getInstance().getDistanceMatrix().toString());
			writer.close();	
		}
	}
	
	/**
	 * Saves the gene signature into a file.
	 * @throws IOException
	 */
	public static void saveGeneSignature() throws IOException{
		JFileChooser saveGene = new JFileChooser();
		int result = saveGene.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION){
			File outputFile = saveGene.getSelectedFile();
			FileWriter writer = new FileWriter(outputFile);
			GeneticSignature geneticSignature = Mediator.getInstance().getSignature();
			writer.write(geneticSignature.toString());
			writer.close();	
		}
	}

	/**
	 * Saves the dendogram textual representation into a file.
	 * @throws IOException
	 */
	public static void saveDendogramTree(Dendogram dendogram) throws IOException {
		JFileChooser saveDendogram = new JFileChooser();
		int result = saveDendogram.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File outputFile = saveDendogram.getSelectedFile();
			FileWriter writer = new FileWriter(outputFile);
			writer.write(dendogram.toString());
			writer.close();	
		}
	}
		
}
