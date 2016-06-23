package br.ufrn.imd.genesigno.gui;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

import br.ufrn.imd.genesigno.clustering.linkage.LinkageCriteria;
import br.ufrn.imd.genesigno.clustering.metric.Metric;
import br.ufrn.imd.genesigno.core.Mediator;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Panel with content of choices and fuctions to make distance matrix and the cluster
 *
 */
public class ClusteringPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private DendogramFrame dendogramFrame;
	private DistanceMatrixFrame distanceMatrixFrame;
	
	private JRadioButton rdbtnEuclidian;
	private JRadioButton rdbtnSquareEuclidian;
	private JRadioButton rdbtnManhattan;
	private JRadioButton rdbtnMaximum;
	private ButtonGroup rgrpMetric;
	
	private JRadioButton rdbtnCompleteLinkage;
	private JRadioButton rdbtnSingleLinkage;
	private JRadioButton rdbtnUpgma;
	private ButtonGroup rgrpLinkage;
	
	private JButton btnCluster;
	private JButton btnGenerateMatrix;
	
	/** 
	 * Create the panel
	 */
	public ClusteringPanel() {
		this.setPreferredSize(new Dimension(450, 241));
		this.setLayout(null);
		
		JLabel lblMetric = new JLabel("Choose Metric:");
		lblMetric.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMetric.setBounds(10, 47, 122, 14);
		this.add(lblMetric);
		
		rdbtnEuclidian = new JRadioButton("Euclidian");
		rdbtnEuclidian.setBounds(10, 69, 89, 23);
		this.add(rdbtnEuclidian);
		rdbtnEuclidian.setSelected(true); //default
		rdbtnEuclidian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setMetric(Metric.EUCLIDEAN);
				GUIMain.getInstance().refresh();
			}
		});
		
		rdbtnSquareEuclidian = new JRadioButton("Square Euclidian");
		rdbtnSquareEuclidian.setBounds(241, 69, 170, 23);
		this.add(rdbtnSquareEuclidian);
		rdbtnSquareEuclidian.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setMetric(Metric.SQUARED_EUCLIDEAN);
				GUIMain.getInstance().refresh();
			}
		});
		
		rdbtnManhattan = new JRadioButton("Manhattan");
		rdbtnManhattan.setBounds(10, 95, 103, 23);
		this.add(rdbtnManhattan);
		rdbtnManhattan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setMetric(Metric.MANHATTAN);
				GUIMain.getInstance().refresh();
			}
		});
		
		rdbtnMaximum = new JRadioButton("Maximum");
		rdbtnMaximum.setBounds(241, 95, 103, 23);
		this.add(rdbtnMaximum);
		rdbtnMaximum.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setMetric(Metric.MAXIMUM);
				GUIMain.getInstance().refresh();
			}
		});
		
		rgrpMetric = new ButtonGroup();
		rgrpMetric.add(rdbtnEuclidian);
		rgrpMetric.add(rdbtnSquareEuclidian);
		rgrpMetric.add(rdbtnManhattan);
		rgrpMetric.add(rdbtnMaximum);
		
		JLabel lblLinkage = new JLabel("Choose Linkage Criteria:");
		lblLinkage.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblLinkage.setBounds(12, 127, 260, 16);
		this.add(lblLinkage);
		
		rdbtnCompleteLinkage = new JRadioButton("Complete Linkage");
		rdbtnCompleteLinkage.setBounds(11, 151, 207, 23);
		this.add(rdbtnCompleteLinkage);
		rdbtnCompleteLinkage.setSelected(true);
		rdbtnCompleteLinkage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setLinkage(LinkageCriteria.COMPLETE);
				GUIMain.getInstance().refresh();
			}
		});
		
		rdbtnSingleLinkage = new JRadioButton("Single Linkage");
		rdbtnSingleLinkage.setBounds(241, 150, 170, 23);
		this.add(rdbtnSingleLinkage);
		rdbtnSingleLinkage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setLinkage(LinkageCriteria.SINGLE);
				GUIMain.getInstance().refresh();
			}
		});
		
		rdbtnUpgma = new JRadioButton("UPGMA");
		rdbtnUpgma.setBounds(10, 178, 130, 23);
		this.add(rdbtnUpgma);
		rdbtnUpgma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setLinkage(LinkageCriteria.UPGMA);
				GUIMain.getInstance().refresh();
			}
		});
		
		rgrpLinkage = new ButtonGroup();
		rgrpLinkage.add(rdbtnCompleteLinkage);
		rgrpLinkage.add(rdbtnSingleLinkage);
		rgrpLinkage.add(rdbtnUpgma);
		
		btnCluster = new JButton("Cluster");
		btnCluster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().generateCluster();
				dendogramFrame = new DendogramFrame();
				dendogramFrame.setVisible(true);
				GUIMain.getInstance().refresh();
			}
		});
		btnCluster.setBounds(228, 209, 183, 23);
		this.add(btnCluster);
		btnCluster.setEnabled(false);
		
		btnGenerateMatrix = new JButton("Generate Matrix");
		btnGenerateMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				Mediator.getInstance().generateDistanceMatrix();
				GUIMain.getInstance().refresh();	
				distanceMatrixFrame = new DistanceMatrixFrame();
				GUIMain.getInstance().refresh();
				distanceMatrixFrame.setVisible(true);
				GUIMain.getInstance().refresh();
				
			}
		});
		btnGenerateMatrix.setToolTipText("Generate distance matrix");
		btnGenerateMatrix.setBounds(56, 209, 162, 23);
		this.add(btnGenerateMatrix);	
		
		JSeparator separator = new JSeparator();
		separator.setBounds(15, 35, 203, 2);
		this.add(separator);
		
		JLabel lblClustering = new JLabel("Clustering:");
		lblClustering.setFont(new Font("Source Sans Pro Semibold", Font.BOLD | Font.ITALIC, 19));
		lblClustering.setBounds(15, 11, 198, 26);
		add(lblClustering);
	}
	
	/**
	 * Refresh the chosen information by the user to the Mediator
	 */
	public void refreshClusteringPanel(){
		if(Mediator.getInstance().hasSignature()){
			GUIMain.getInstance().getMainframe().setBounds(100, 100, 480, 748);
			GUIMain.getInstance().getPanelInformation().getClusterOptions().setVisible(true);
			rdbtnEuclidian.setEnabled(true);
			rdbtnSquareEuclidian.setEnabled(true);
			rdbtnManhattan.setEnabled(true); 
			rdbtnMaximum.setEnabled(true);
			
			rdbtnSingleLinkage.setEnabled(true);
			rdbtnCompleteLinkage.setEnabled(true);
			rdbtnUpgma.setEnabled(true);
			btnGenerateMatrix.setEnabled(true);
		}
		
		if (Mediator.getInstance().hasSignature()) {
			btnGenerateMatrix.setEnabled(true);
		}
		else
		{
			btnGenerateMatrix.setEnabled(false);
		}
		
		if (Mediator.getInstance().hasDistanceMatrix()) {
			btnCluster.setEnabled(true);
		}
		else {
			btnCluster.setEnabled(false);
		}
		
	}
}


