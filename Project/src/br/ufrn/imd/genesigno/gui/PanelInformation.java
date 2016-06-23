package br.ufrn.imd.genesigno.gui;

import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JRadioButton;

public class PanelInformation extends JPanel {

	private static final long serialVersionUID = 1L;

	private FilesPanel filesOptions;
	private SignaturePanel signatureOptions;
	private ClusteringPanel clusterOptions;

	/**
	 * Getters and setters
	 */
	public FilesPanel getFilesOptions() {
		return filesOptions;
	}

	public void setFilesOptions(FilesPanel filesOptions) {
		this.filesOptions = filesOptions;
	}

	public SignaturePanel getSignatureOptions() {
		return signatureOptions;
	}

	public void setSignatureOptions(SignaturePanel signatureOptions) {
		this.signatureOptions = signatureOptions;
	}

	public ClusteringPanel getClusterOptions() {
		return clusterOptions;
	}

	public void setClusterOptions(ClusteringPanel clusterOptions) {
		this.clusterOptions = clusterOptions;
	}

	/**
	 * 
	 */
	public PanelInformation() {	
		this.setPreferredSize(new Dimension(480, 679));
		this.setBounds(100, 100, 480, 582);
		this.setLayout(null); //absolute layout
		
		filesOptions = new FilesPanel();
		filesOptions.setBounds(20, 0, 450, 234);
		this.add(filesOptions);
		
		signatureOptions = new SignaturePanel();
		signatureOptions.setSize(450, 191);
		signatureOptions.setLocation(20, 234);
		this.add(signatureOptions);
		signatureOptions.setVisible(false);
		
		clusterOptions = new ClusteringPanel();
		clusterOptions.setSize(450, 241);
		clusterOptions.setLocation(20, 431);
		this.add(clusterOptions);
		clusterOptions.setVisible(false);
		
		adjustComponents(this);
	}
	
	/**
	 * Standardize definitions of the components.
	 * @param panel
	 */
	private void adjustComponents(JPanel panel) {
		Component[] components = panel.getComponents();
		
		for (Component component : components) {
			if (component instanceof JPanel){
				adjustComponents((JPanel) component);
			}
			else if(component instanceof JButton){
				JButton btn = (JButton) component;
				btn.setSize(170, 23);
			}
			else if (component instanceof JRadioButton){
				JRadioButton rbutton = (JRadioButton) component;
				rbutton.setSize(170, 23);
				
			}
		}
	}
}
