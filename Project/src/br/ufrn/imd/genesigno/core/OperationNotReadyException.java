package br.ufrn.imd.genesigno.core;

public class OperationNotReadyException extends RuntimeException {
	
	private static final long serialVersionUID = -1295469397198320881L;

	public OperationNotReadyException(String message) {
		super(message);
	}
}
