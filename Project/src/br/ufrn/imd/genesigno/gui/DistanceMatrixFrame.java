package br.ufrn.imd.genesigno.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

import br.ufrn.imd.genesigno.clustering.DistanceMatrix;
import br.ufrn.imd.genesigno.core.Mediator;

public class DistanceMatrixFrame extends JFrame {
	
	/**
	 * Represents the view with the options after the distance matrix is created.
	 */
	private static final long serialVersionUID = 1L;
	private static DistanceMatrixFrame open_frame;
	
	private JPanel contentPane;
	private JPanel canvas;
	private DistanceMatrixPanel matrixOptions;

	/**
	 * Create the frame.
	 */
	public DistanceMatrixFrame() {
		
		if (open_frame != null)
			open_frame.dispose();
		open_frame = this;
		
		this.setTitle("Distance Matrix");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(100, 100, 450, 300);
		
		this.contentPane = new JPanel();
		this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.contentPane.setLayout(new BorderLayout(0, 0));
		this.setContentPane(contentPane);
		
		matrixOptions = new DistanceMatrixPanel();
		this.getContentPane().add(matrixOptions, BorderLayout.SOUTH);
		
		this.canvas = new JPanel();
		this.canvas.setLayout(new BorderLayout(0, 0));
		
		DistanceMatrixTable dmt = new DistanceMatrixTable(Mediator.getInstance().getDistanceMatrix());
		this.canvas.add(dmt);

		JScrollPane scrollPane = new JScrollPane(canvas);
		this.contentPane.add(scrollPane, BorderLayout.CENTER);
	}

}

class DistanceMatrixPanel extends JPanel {

	/** Panel with Distance Matrix's options
	 * @author Raquel
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */
	public DistanceMatrixPanel() {
		this.setPreferredSize(new Dimension(375, 40));
		this.setLayout(new BorderLayout(0, 0));
		
		JButton btnSaveDistanceMatrix = new JButton("Save Distance Matrix");
		btnSaveDistanceMatrix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Mediator.getInstance().saveDistanceMatrix();
			}
		});
		btnSaveDistanceMatrix.setSize(170, 23);
		add(btnSaveDistanceMatrix, BorderLayout.CENTER);
	}
}

/**
 * 
 * @author Lucas
 *
 */

class DistanceMatrixTable extends JTable {

	Color emphasis_color = new Color(204, 255, 255);
	
    public DistanceMatrixTable(DistanceMatrix dm) {
        super(new DistanceMatrixTableModel(dm));

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);

        setDefaultRenderer(Object.class, renderer);

        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setRowSelectionAllowed(false);
        setColumnSelectionAllowed(false);
        setRowHeight(16);

        TableColumnModel columnModel = getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            TableColumn column = columnModel.getColumn(i);
            column.setPreferredWidth(60);
        }
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component rendererComponent = super.prepareRenderer(renderer, row, column);

        if (!isCellSelected(row, column)) {
            if (row == 0 || column == 0) {
                rendererComponent.setBackground(emphasis_color);
            } else {
                rendererComponent.setBackground(Color.WHITE);
            }
        }

        return rendererComponent;
    }
}

/**
 * 
 * @author Lucas
 *
 */

class DistanceMatrixTableModel extends AbstractTableModel {

	private DistanceMatrix matrix;
	
	public DistanceMatrixTableModel(DistanceMatrix dm) {
		matrix = dm;
	}
	
    public int getRowCount() {
        return matrix.getMatrix().length;
    }

    public int getColumnCount() {
        return matrix.getMatrix().length;
    }

    public String getColumnName(int column) {
        return null;
    }

    public Class getColumnClass(int column) {
        return Object.class;
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public Object getValueAt(int row, int column) {
        if (row == 0) {
            if (column == 0) {
                return "";
            } else {
                return matrix.getSamples()[column-1].getLabel();
            }
        } else {
            if (column == 0) {
            	return matrix.getSamples()[row-1].getLabel();
            } else {
                return String.format("%.2f", matrix.getMatrix()[row-1][column-1]);
            }
        }
    }
}