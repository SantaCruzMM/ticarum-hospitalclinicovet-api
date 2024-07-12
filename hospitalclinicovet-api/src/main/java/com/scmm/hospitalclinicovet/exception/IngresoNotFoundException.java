package com.scmm.hospitalclinicovet.exception;

// Clase que representa una excepción de Ingreso no encontrado
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