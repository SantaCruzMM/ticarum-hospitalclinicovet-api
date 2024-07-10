package com.scmm.hospitalclinicovet.exception;

@SuppressWarnings("serial")
public class InputException extends RuntimeException {
	
	public InputException() {
		super();
	}
	
	public InputException(String mensaje) {
		super(mensaje);
	}
}
