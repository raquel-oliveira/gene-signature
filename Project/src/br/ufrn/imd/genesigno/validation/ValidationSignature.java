package br.ufrn.imd.genesigno.validation;

/**
 * Validation signature to validate leave-one-out trees.
 */
public class ValidationSignature {
	private byte[] side;
	
	/**
	 * Constructor
	 * @param side Side of the nodes. side[i] must be 0 or 1 
	 * if node i was in the left or right, respectively.
	 */
	public ValidationSignature(byte[] side) {
		this.side = side.clone();
	}
	
	public byte[] getNodeSide() {
		return side;
	}
}
