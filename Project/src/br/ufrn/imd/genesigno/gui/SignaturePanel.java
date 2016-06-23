package br.ufrn.imd.genesigno.gui;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import br.ufrn.imd.genesigno.core.Mediator;
import br.ufrn.imd.genesigno.signature.SignificanceFilter;
import br.ufrn.imd.genesigno.signature.TwoGroupStatisticalTest;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Locale;

import javax.swing.JSeparator;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SignaturePanel extends JPanel {
	
	/**
	 * Panel with content of choices and fuctions to make a gene signature after the study is created
	 */
	private static final long serialVersionUID = 1L;

	private SignatureFrame signatureFrame;
	
	private JTextField txtCorteCriteria;
		
	private JRadioButton rdbtnTestU;
	private JRadioButton rdbtnTestT;
	private JRadioButton rdbtnTestSk;
	private JRadioButton rdbtnTestOne;
	private ButtonGroup rgrpTest2plus;
	
	private JButton btnGeneSignature;

	/**
	 * Create the panel.
	 */
	public SignaturePanel() {
		this.setPreferredSize(new Dimension(450, 199));
		this.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(15, 35, 203, 2);
		add(separator);
		
		JLabel lblGeneSignature = new JLabel("Gene Signature:");
		lblGeneSignature.setFont(new Font("Source Sans Pro Semibold", Font.BOLD | Font.ITALIC, 19));
		lblGeneSignature.setBounds(15, 10, 198, 26);
		add(lblGeneSignature);
				
		JLabel lblCorteCriteria = new JLabel("p-value threshold");
		lblCorteCriteria.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblCorteCriteria.setBounds(15, 134, 142, 14);
		this.add(lblCorteCriteria);
		
		txtCorteCriteria = new JTextField();
		txtCorteCriteria.setBounds(165, 132, 43, 20);
		this.add(txtCorteCriteria);
		txtCorteCriteria.setColumns(10);
		txtCorteCriteria.setToolTipText("p-value");
		txtCorteCriteria.setEnabled(false);
		txtCorteCriteria.setText(String.format(Locale.US, "%.2f",Mediator.getInstance().getPvalue()));
		txtCorteCriteria.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				updateFilter();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				updateFilter();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				updateFilter();
			}
			
			public void updateFilter() {
				try {
					double p = Double.parseDouble(txtCorteCriteria.getText());
					Mediator.getInstance().setPvalue(p);
				}
				catch (RuntimeException e) {
					JOptionPane.showMessageDialog(null,
					          "Error: Please enter number between 0 and 1", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
					String pstr = String.format("%.2f", Mediator.getInstance().getPvalue()); 
					txtCorteCriteria.setText(pstr);
				}
			}
		});
		
		JLabel lblStatisticalTest = new JLabel("Choose Statistical test:");
		lblStatisticalTest.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblStatisticalTest.setBounds(12, 47, 179, 14);
		this.add(lblStatisticalTest);
		
		this.rdbtnTestU = new JRadioButton("Mann-Whitney U");
		this.rdbtnTestU.setBounds(15, 69, 193, 23);
		this.add(rdbtnTestU);
		this.rdbtnTestU.setEnabled(false);
		this.rdbtnTestU.setSelected(true);//default
		this.rdbtnTestU.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setSelectedTest(SignificanceFilter.UTEST);
			}
		});
		
		this.rdbtnTestT = new JRadioButton("Student's t");
		this.rdbtnTestT.setBounds(15, 95, 142, 23);
		this.add(rdbtnTestT);
		this.rdbtnTestT.setEnabled(false);
		this.rdbtnTestT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setSelectedTest(SignificanceFilter.TTEST);
			}
		});
		
		this.rdbtnTestSk = new JRadioButton("Kolmogorov-Smirnov");
		this.rdbtnTestSk.setBounds(210, 69, 216, 23);
		this.add(rdbtnTestSk);
		this.rdbtnTestSk.setEnabled(false);
		this.rdbtnTestSk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().setSelectedTest(SignificanceFilter.KSTEST);
			}
		});
		
		this.rdbtnTestOne = new JRadioButton("One-Way ANOVA");
		this.rdbtnTestOne.setBounds(210, 95, 168, 23);
		this.add(rdbtnTestOne);
		this.rdbtnTestOne.setEnabled(false);
		
		this.rgrpTest2plus = new ButtonGroup();
		this.rgrpTest2plus.add(rdbtnTestSk);
		this.rgrpTest2plus.add(rdbtnTestT);
		this.rgrpTest2plus.add(rdbtnTestU);
		this.rgrpTest2plus.add(rdbtnTestOne);
	
		this.btnGeneSignature = new JButton("Gene Signature");
		this.btnGeneSignature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Mediator.getInstance().generateSignature();
				}
				catch (RuntimeException e) {
					JOptionPane.showMessageDialog(null,
					          "Error: Empty Signature", "Error Message",
					          JOptionPane.ERROR_MESSAGE);
					return;
				}
				GUIMain.getInstance().refresh();
				signatureFrame = new SignatureFrame();
				signatureFrame.setVisible(true);
			}
		});
		this.btnGeneSignature.setEnabled(false);
		this.btnGeneSignature.setBounds(142, 164, 168, 23);
		this.add(btnGeneSignature);
	}
	
	/**
	 * Refresh the chosen information by the user to the Mediator
	 */
	public void refreshSignaturePanel(){
		if (Mediator.getInstance().hasStudy()){
			GUIMain.getInstance().getMainframe().setBounds(100, 100, 480, 455);
			btnGeneSignature.setEnabled(true);
			GUIMain.getInstance().getPanelInformation().getSignatureOptions().setVisible(true);
			txtCorteCriteria.setEnabled(true);
			
			if (!Mediator.getInstance().getStudy().isTwoGroup()){
				
				rdbtnTestSk.setSelected(false);
				rdbtnTestSk.setEnabled(false);
				rdbtnTestT.setSelected(false);
				rdbtnTestT.setEnabled(false);
				rdbtnTestU.setSelected(false);
				rdbtnTestU.setEnabled(false);
				//rdbtnTestOne.setVisible(true);
				rdbtnTestOne.setEnabled(true);
				rdbtnTestOne.setSelected(true);
				
			} else {
				rdbtnTestOne.setSelected(false);
				rdbtnTestOne.setEnabled(false);
				rdbtnTestSk.setEnabled(true);
				rdbtnTestT.setEnabled(true);
				rdbtnTestU.setEnabled(true);
				
				TwoGroupStatisticalTest test = Mediator.getInstance().getSelectedTest(); 
				if (test == SignificanceFilter.KSTEST) {
					rdbtnTestSk.setSelected(true);
				}
				else if (test == SignificanceFilter.TTEST) {
					rdbtnTestT.setSelected(true);
				}
				else if (test == SignificanceFilter.UTEST) {
					rdbtnTestU.setSelected(true);
				}
			}
		}
		else {
			this.setVisible(false);
		}
	}
	
	/**
	 * generate a gene signature.
	 */
	public void generateSignature(){
		GUIMain.getInstance().refresh();
		Mediator.getInstance().generateSignature();
	}
}
