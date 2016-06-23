package br.ufrn.imd.genesigno.model;

/**
 * Represents a sample in the study.
 */
public class Sample {
	private int index;
	private String label;
	private Group group;
	
	/**
	 * Constructor
	 * @param index The sample index
	 * @param label The sample name
	 */
	public Sample(int index, String label) {
		this.index = index;
		this.label = label;
		this.group = null;
	}
	
	/**
	 * @return Sample's index
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * @return Sample's name
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * @return Sample's group
	 */
	public Group getGroup() {
		return group;
	}
	
	/**
	 * Changes the sample group
	 * @param group The new group
	 */
	void setGroup(Group group) {
		this.group = group;
	}
}
