package br.ufrn.imd.genesigno.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import br.ufrn.imd.genesigno.clustering.Dendogram;
import br.ufrn.imd.genesigno.core.Mediator;
import br.ufrn.imd.genesigno.io.OutputFilesWriter;

import java.awt.FlowLayout;

public class DendogramFrame extends JFrame {

	/**
	 * Represents the dendogram's view with the options after the dendogram is created.
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Dendogram dendogram;
	private BufferedImage dendogramImage;
	private JPanel canvas;
	

	/**
	 * Create the frame.
	 */
	public DendogramFrame() {
		this.setTitle("Dendogram");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 660, 384);
		setMinimumSize(new Dimension(300, 300));//choose better
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		dendogram = Mediator.getInstance().getDendogram();
		dendogramImage = dendogram.draw();
		
		DendogramPanel dendogramOptions= new DendogramPanel(dendogram, dendogramImage);
		this.getContentPane().add(dendogramOptions, BorderLayout.SOUTH);
		dendogramOptions.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

	    this.canvas = new JPanel() {
            private static final long serialVersionUID = 1L;
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(dendogramImage, 0, 0, null);
            }
        };

		canvas.setPreferredSize(new Dimension(dendogramImage.getWidth(), dendogramImage.getHeight()));
		JScrollPane scrollPane = new JScrollPane(canvas);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		// comment the next line to allow editing on WindowBuilder
		this.setBounds(100, 100, Math.min(dendogramImage.getWidth()+30, 800), Math.min(dendogramImage.getHeight()+90, 600));
	}
}

class DendogramPanel extends JPanel {
	/**
	 * Represents the panel with options only available after the clustering is done.
	 * @author: Raquel
	 */
	private static final long serialVersionUID = 1L;

	private JButton btnSaveDendogram;
	private JButton btnValidate;
	private JButton btnSaveTree;
	private BufferedImage bufferImage;
	private Dendogram dendogram;
	
	/**
	 * Create the panel.
	 */
	public DendogramPanel(Dendogram dend, BufferedImage image) {
		this.setLayout(new BorderLayout(0, 0));
		this.setPreferredSize(new Dimension(576, 51));
		this.bufferImage = image;
		this.dendogram = dend;
		
		btnValidate = new JButton("Validate");
		btnValidate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().validate();
				if (Mediator.getInstance().validate()) {
					JOptionPane.showMessageDialog(null, "Dendogram's tree is stable.", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Dendogram's tree is unstable.", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.add(btnValidate, BorderLayout.EAST);
		
		btnSaveDendogram = new JButton("Save Dendogram");
		btnSaveDendogram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					OutputFilesWriter.saveDendogramImage(bufferImage);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Save image failed: "+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSaveDendogram.setBounds(10, 11, 170, 23);
		btnSaveDendogram.setSize(170, 23);
		this.add(btnSaveDendogram);
		
		btnSaveTree = new JButton("Save Tree");
		btnSaveTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					OutputFilesWriter.saveDendogramTree(dendogram);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Save tree failed: "+e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		add(btnSaveTree);
	}
}