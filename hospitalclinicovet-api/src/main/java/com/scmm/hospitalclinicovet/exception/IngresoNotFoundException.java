package com.scmm.hospitalclinicovet.exception;

@SuppressWarnings("serial")
public class IngresoNotFoundException extends RuntimeException {
	
	public IngresoNotFoundException() {
		super();
	}
	
	public IngresoNotFoundException(Long id) {
		super("No se ha encontrado el ingreso con ID: " + id);
	}
	
	public IngresoNotFoundException(String mensaje) {
		super(mensaje);
	}
}