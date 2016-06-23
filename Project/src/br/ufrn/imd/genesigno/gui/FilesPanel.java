package br.ufrn.imd.genesigno.gui;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

import javax.swing.JTextField;

import br.ufrn.imd.genesigno.core.Mediator;
import br.ufrn.imd.genesigno.io.InputFilesParser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/* Panel with content of choices and fuctions to input files and make study
*/

public class FilesPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private File file;
	
	private JTextField txtSampleFileName;
	private JTextField txtValuesFileName;
	
	private JLabel lblnGroups;
	private JLabel lblnSample;
	private JLabel lblnGenes;
	private JLabel lblGroups;
	private JLabel lblSample;
	private JLabel lblGenes;
	
	private JButton btnOpenValues;
	private JButton btnShowValues;
	private JButton btnOpenSample;
	private JButton btnShowSample;
	private JButton btnStudy;
	
	/**
	 * Create the panel.
	 */
	public FilesPanel() {
		JSeparator separator = new JSeparator();
		separator.setBounds(15, 35, 203, 2);
		this.add(separator);
		
		JLabel lblInput = new JLabel("Input:");
		lblInput.setFont(new Font("Source Sans Pro Semibold", Font.BOLD | Font.ITALIC, 19));
		lblInput.setBounds(15, 11, 198, 26);
		this.add(lblInput);
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(450, 234));
	
		JLabel lblAmostraFile = new JLabel("Groups File:");
		lblAmostraFile.setBounds(10, 52, 93, 14);
		this.add(lblAmostraFile);
		
		this.btnOpenSample = new JButton("Select Groups File");
		this.btnOpenSample.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile();
				if (file != null) {
					InputFilesParser.getInstance().setSamplesFilename(file.getAbsolutePath());
					txtSampleFileName.setText(InputFilesParser.getInstance().getSamplesFilename());
					GUIMain.getInstance().refresh();
				}
			}
		});
		this.btnOpenSample.setBounds(15, 78, 231, 23);
		this.add(btnOpenSample);
		
		JLabel lblSampleFileName = new JLabel("");
		lblSampleFileName.setBounds(115, 52, 309, 14);
		this.add(lblSampleFileName);
	
		txtSampleFileName = new JTextField();
		txtSampleFileName.setEditable(false);
		txtSampleFileName.setBounds(113, 50, 311, 20);
		this.add(txtSampleFileName);
		txtSampleFileName.setColumns(10);
		
		this.btnShowSample = new JButton("Show sample file");
		this.btnShowSample.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readFile(InputFilesParser.getInstance().getSamplesFilename());
			}
		});
		this.btnShowSample.setBounds(252, 78, 172, 23);
		this.btnShowSample.setToolTipText("Show selected file");
		add(btnShowSample);
		this.btnShowSample.setEnabled(false);
		
		JLabel lblValuesFile = new JLabel("Values File:");
		lblValuesFile.setBounds(10, 119, 93, 14);
		this.add(lblValuesFile);
		
		this.btnOpenValues = new JButton("Select Values File");
		this.btnOpenValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseFile();
				if (file != null) {
					InputFilesParser.getInstance().setValuesFileName(file.getAbsolutePath());
					txtValuesFileName.setText(InputFilesParser.getInstance().getValuesFileName());
					GUIMain.getInstance().refresh();
				}
			}
		});
		btnOpenValues.setBounds(15, 145, 231, 23);
		this.add(btnOpenValues);
		
		JLabel lblValuesFileName = new JLabel("");
		lblValuesFileName.setBounds(115, 113, 309, 14);
		this.add(lblValuesFileName);
		
		txtValuesFileName = new JTextField();
		txtValuesFileName.setEditable(false);
		txtValuesFileName.setColumns(10);
		txtValuesFileName.setBounds(115, 113, 309, 20);
		this.add(txtValuesFileName);
		
		this.btnShowValues = new JButton("Show values file");
		this.btnShowValues.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readFile(InputFilesParser.getInstance().getValuesFileName());
			}
		});
		this.btnShowValues.setBounds(252, 145, 172, 23);
		add(btnShowValues);
		this.btnShowValues.setEnabled(false);
		
		this.btnStudy = new JButton("Generate Study");
		this.btnStudy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Mediator.getInstance().generateStudy();
					
				} catch (FileNotFoundException e1) {
					
					JOptionPane.showMessageDialog(null, "Error opening the file.", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (RuntimeException e1) {
					
					JOptionPane.showMessageDialog(null, "Error parsing the files", "Error", JOptionPane.ERROR_MESSAGE);
				}
				GUIMain.getInstance().refresh();
			}
		});
		this.btnStudy.setEnabled(false);
		this.btnStudy.setBounds(157, 179, 167, 23);
		this.add(btnStudy);
		
		lblGroups = new JLabel("Groups");
		lblGroups.setBounds(321, 179, 103, 14);
		add(lblGroups);
		lblGroups.setVisible(false);
		
		lblnGroups = new JLabel();
		lblnGroups.setBounds(252, 179, 60, 14);
		add(lblnGroups);
		
		lblSample = new JLabel("Samples");
		lblSample.setBounds(321, 197, 103, 14);
		add(lblSample);
		lblSample.setVisible(false);
		
		lblnSample = new JLabel();
		lblnSample.setBounds(252, 197, 60, 14);
		add(lblnSample);
		
		lblGenes = new JLabel("Genes");
		lblGenes.setBounds(321, 215, 103, 14);
		add(lblGenes);
		lblGenes.setVisible(false);
		
		lblnGenes = new JLabel();
		lblnGenes.setBounds(252, 215, 60, 14);
		add(lblnGenes);
		
	}
	
	/**
	 * Refresh the chosen information by the user to the Mediator
	 */
	public void refreshFilesPanel(){
		Mediator m = Mediator.getInstance();
		if (m.getValuesInputFile() != null && m.getSamplesInputFile() != null){
			this.btnStudy.setEnabled(true);
		}
		else {
			this.btnStudy.setEnabled(false);
		}
		if (m.getValuesInputFile() != null){
			this.btnShowValues.setEnabled(true);
		}
		if (m.getSamplesInputFile() != null){
			this.btnShowSample.setEnabled(true);
		}
		if (m.hasStudy()){
			btnStudy.setBounds(56, 193, 167, 23);
			lblGenes.setVisible(true);
			lblnGenes.setText(String.format(Locale.US, "%d",Mediator.getInstance().getStudy().getGeneCount()));
			lblGroups.setVisible(true);
			lblnGroups.setText(String.format(Locale.US, "%d",Mediator.getInstance().getStudy().getGroupCount()));
			lblSample.setVisible(true);
			lblnSample.setText(String.format(Locale.US, "%d",Mediator.getInstance().getStudy().getSampleCount()));
		}
	}
	
	/**
	 * FileChooser to choose a file
	 */
	private void chooseFile(){
		JFileChooser fileSample = new JFileChooser();
		int returnVal = fileSample.showOpenDialog(null);
	    if (returnVal == JFileChooser.APPROVE_OPTION) {
	         file = fileSample.getSelectedFile();
	    }
	    else {
	    	file = null;
	    }
	}
	
	/**
	 * Open a window with the content of the file
	 * @param directoryName file's path
	 */
	
	private void readFile(String directoryName){
        BufferedReader br;
        try {
        	br = new BufferedReader(new FileReader(directoryName));
        	String lines;
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
            	sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            lines = sb.toString();
            br.close();
            
            JTextArea fileArea = new JTextArea(30,50);
    		fileArea.setText(lines);
    		fileArea.setForeground(Color.BLACK);
    		fileArea.setFocusable(false);
    		
    		JScrollPane showFilePane = new JScrollPane(fileArea);
    		
    		JOptionPane.showMessageDialog(btnShowSample, showFilePane, directoryName, JOptionPane.NO_OPTION);
            } catch (IOException e) {
            	e.printStackTrace();
				JOptionPane.showMessageDialog(null, "bugou", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
  }
}
