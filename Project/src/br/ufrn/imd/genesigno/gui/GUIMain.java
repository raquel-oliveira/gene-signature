package br.ufrn.imd.genesigno.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;

public class GUIMain {

	private JFrame Mainframe;
	public JFrame getMainframe() {
		return Mainframe;
	}

	private PanelInformation panelInformation;
	
	private static GUIMain instance;

	public static GUIMain getInstance() {
		if (instance == null)
			instance = new GUIMain(); 
		return instance;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new MetalLookAndFeel());
					GUIMain window = GUIMain.getInstance();
					window.Mainframe.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUIMain() {
		initialize();
	}

	/**
	 *
	 */
	private void initialize() {
		Mainframe = new JFrame();
		Mainframe.setBounds(100, 100, 480, 237);
		Mainframe.addWindowListener(new WindowAdapter() {
			 public void windowClosing(WindowEvent e)
			 {
				 System.exit(0);
			 }
		});
		Mainframe.getContentPane().setLayout(new BorderLayout(0, 0));
		Mainframe.setTitle("Gene Signature Analysis");
		Mainframe.setResizable(false);
		
		this.panelInformation = new PanelInformation(); 
		Mainframe.getContentPane().add(panelInformation, BorderLayout.CENTER);
		refresh();
	}
	
	/**
	 * Getters and setters
	 */
	public PanelInformation getPanelInformation() {
		return panelInformation;
	}

	public void setPanelInformation(PanelInformation panelInformation) {
		this.panelInformation = panelInformation;
	}
	
	/**
	 * Refresh all the panels of Panel information according to chosen option of the user
	 */
	public void refresh(){
		this.getPanelInformation().getFilesOptions().refreshFilesPanel();
		this.getPanelInformation().getSignatureOptions().refreshSignaturePanel();
		this.getPanelInformation().getClusterOptions().refreshClusteringPanel();	
	}
}