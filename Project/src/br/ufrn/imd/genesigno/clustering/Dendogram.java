/**
 * 
 */
package br.ufrn.imd.genesigno.clustering;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import br.ufrn.imd.genesigno.model.Group;
import br.ufrn.imd.genesigno.model.Sample;
import br.ufrn.imd.genesigno.validation.ValidationSignature;

public class Dendogram {
	
	private Node root;
	private int sample_count;
	private int node_index;
	private ArrayList<Group> groups;
	private int groupless_count;
	private double max_height;
	
	public class Node {
		double height;
		Node left;
		Node right;
		Sample sample; 
		double min_index;
		double max_index;
		Group group;
	
		Node(Node left, Node right, double height) {
			this.left = left;
			this.right = right;
			this.sample = null;
			this.height = height;
			this.min_index = (left.min_index + left.max_index) / 2.0; 
			this.max_index = (right.min_index + right.max_index) / 2.0;
			max_height = Math.max(height, max_height);
			
			if (this.left.group == this.right.group) {
				this.group = this.left.group;
			}
			if (this.group == null) {
				groupless_count += 1;
			}
		}
		
		Node(Sample sample, int index, double height) {
			this.left = null;
			this.right = null;
			this.height = height;
			this.sample = sample;
			this.min_index = index;
			this.max_index = index;
			this.group = sample.getGroup();
			if (!groups.contains(this.group)) {
				groups.add(this.group);
			}
		}
		
		void addPreorder(ArrayList<Node> list) {
			if (left != null) {
				left.addPreorder(list);
				right.addPreorder(list);
			}
			list.add(this);
		}

		public void makeParentheses(StringBuilder sb) {
			sb.append("(");
			if (sample == null) {
				left.makeParentheses(sb);
				right.makeParentheses(sb);
			}
			else {
				sb.append(sample.getLabel());
			}
			sb.append(")");
		}
	}
	
	private Node makeNode(DatapointCluster cluster) {
		if (cluster.getSample() != null) {
			node_index += 1;
			return new Node(cluster.getSample(), node_index - 1, 0);
		}
		else {
			return new Node(makeNode(cluster.getLeftCluster()),
					makeNode(cluster.getRightCluster()), cluster.getDistance());
		}
	}
	
	public Dendogram() {
		this.groups = new ArrayList<Group>();
	}
	
	public Dendogram(DatapointCluster datapointCluster) {
		this.groups = new ArrayList<Group>();
		max_height = 0;
		node_index = 0;
		this.root = makeNode(datapointCluster);
		sample_count = node_index;
	}
	
	public boolean validate(){
		return (groupless_count == groups.size() - 1);	
	}
	
	public BufferedImage draw() {
		
		int hmargin = 20;
		int vmargin = 20;
		int hspace = 30;
		int vspace = 30;
		int hlabelspace = 100;
		
		int hinternal = hspace * (sample_count - 1);
		int vinternal = hinternal/2;
		
		ArrayList<Node> nodes = new ArrayList<Dendogram.Node>();
		root.addPreorder(nodes);
		
		int width = hinternal + 2*hmargin + hlabelspace;
		int height = vinternal + vspace + 2*vmargin;
		
		double norm_factor = vinternal / max_height;
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = img.createGraphics();
		
		g2.setColor(Color.WHITE);
		g2.fillRect(0, 0, width, height);
		g2.setColor(Color.BLACK);
		
		Color[] colors = new Color[6];
		colors[0] = Color.BLUE;
		colors[1] = Color.RED;
		colors[2] = Color.GREEN.darker();
		colors[3] = Color.CYAN.darker();
		colors[4] = Color.ORANGE;
		colors[5] = Color.PINK.darker();
				
		for (Node n : nodes) {
			int minx = (int)(hmargin + hspace * n.min_index);
			int maxx = (int)(hmargin + hspace * n.max_index);
			//int y = height - vmargin - vspace*(n.height);
			int y = (int)(height - 2*vmargin - n.height*norm_factor);
			
			if (n.group == null) {
				g2.setColor(Color.BLACK);
			}
			else {
				g2.setColor(colors[n.group.getIndex() % 6]);
			}
			g2.drawLine(minx, y, maxx, y);
			
			if (n.left == null) {
				AffineTransform orig = g2.getTransform();
				g2.rotate(-Math.PI/2);
				g2.drawString(n.sample.getLabel(), -(y+vspace), minx);
				g2.setTransform(orig);
			}
			else {
				int yleft = (int)(height - 2*vmargin - n.left.height*norm_factor);
				int yright = (int)(height - 2*vmargin - n.right.height*norm_factor);
				g2.drawLine(minx, y, minx, yleft);
				g2.drawLine(maxx, y, maxx, yright);
			}
			g2.setColor(Color.BLACK);
		}
		
		int i = 0;
		for (Group g : groups) {
			int x = hmargin + hspace * (sample_count - 1) + 10;
			int y = vmargin + vspace * i; 
			g2.setColor(colors[g.getIndex() % 6]);
			g2.fillRect(x, y, 10, 10);
			g2.drawString(g.getLabel(), x+20, y+10);
			i += 1;
		}
		
		return img;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		root.makeParentheses(sb);
		return sb.toString();
	}

	public boolean validateOver(ValidationSignature sig) {
		
		ArrayList<Node> left_nodes = new ArrayList<Node>();
		ArrayList<Node> right_nodes = new ArrayList<Node>();
		root.left.addPreorder(left_nodes);
		root.right.addPreorder(right_nodes);
		
		byte[] side = sig.getNodeSide();
		
		boolean first = true;
		byte par = '\0';
		
		for (Node nd : left_nodes) {
			if (nd.sample != null) {
				if (first) {
					par = side[nd.sample.getIndex()];
					first = false;
				}
				else if (par != side[nd.sample.getIndex()])
					return false;
			}
		}
		for (Node nd : right_nodes) {
			if (nd.sample != null) {
				if (par == side[nd.sample.getIndex()])
					return false;
			}
		}
		return true;
	}

	public ValidationSignature getValidationSignature() {
		ArrayList<Node> left_nodes = new ArrayList<Node>();
		ArrayList<Node> right_nodes = new ArrayList<Node>();
		root.left.addPreorder(left_nodes);
		root.right.addPreorder(right_nodes);
		
		byte[] side = new byte[sample_count];
		
		for (Node n : left_nodes) {
			if (n.sample != null) side[(int) n.min_index] = 'L';
		}
		for (Node n : right_nodes) {
			if (n.sample != null) side[(int) n.min_index] = 'R';
		}
		
		return new ValidationSignature(side);
	}
}
