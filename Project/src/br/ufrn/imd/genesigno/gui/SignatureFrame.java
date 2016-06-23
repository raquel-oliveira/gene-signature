package br.ufrn.imd.genesigno.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import br.ufrn.imd.genesigno.core.Mediator;

/**
 * Represents the gene signature's view with the options after the gene signature is created.
 *
 */
public class SignatureFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static SignatureFrame open_frame;
	private JPanel contentPane;
	private JPanel canvas;

	/**
	 * Creates the frame.
	 */
	public SignatureFrame() {
		if (open_frame != null)
			open_frame.dispose();
		open_frame = this;
		
		initializeSignature();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeSignature() {
		this.setTitle("Gene Signature");
		this.setBounds(100, 100, 542, 498);
		this.setMinimumSize(new Dimension(453,453));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		this.contentPane.setLayout(new BorderLayout(0, 0));
	
		GeneSignaturePanel geneOptions= new GeneSignaturePanel();
		this.getContentPane().add(geneOptions, BorderLayout.SOUTH);
		
		this.canvas = new JPanel();
		JTextArea fileArea = new JTextArea(10,50);
		fileArea.setText(Mediator.getInstance().getSignature().toString());
		fileArea.setForeground(Color.BLACK);
		fileArea.setFocusable(false);
		this.canvas.add(fileArea);
		JScrollPane scrollPane = new JScrollPane(canvas);
		this.contentPane.add(scrollPane, BorderLayout.CENTER);
	}
}

class GeneSignaturePanel extends JPanel{
	
	/**
	 * Panel with Gene signature options
	 * @author Raquel 
	 */
	private static final long serialVersionUID = 1L;
	

	/**
	 * Create the panel.
	 */
	public GeneSignaturePanel() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(518, 66));
		
		JButton btnSaveGene = new JButton("Save Gene Siganture");
		btnSaveGene.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().saveGeneticSignature();
			}
		});
		btnSaveGene.setBounds(10, 11, 249, 44);
		this.add(btnSaveGene);
		
		JLabel lblGenes = new JLabel(" genes");
		lblGenes.setBounds(320, 26, 60, 14);
		add(lblGenes);
		
		JLabel lblnGenes = new JLabel();
		if (Mediator.getInstance().hasStudy()){ 
			lblnGenes.setText(String.format(Locale.US, "%d", Mediator.getInstance().getSignature().getGeneCount()));
		}
		lblnGenes.setBounds(301, 26, 80, 14);
		add(lblnGenes);
	}
}