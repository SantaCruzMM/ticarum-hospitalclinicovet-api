package com.scmm.hospitalclinicovet.exception;

//Clase que representa una excepción de datos de entrada inválidos o incompletos
@SuppressWarnings("serial")
public class InputException extends RuntimeException {
	
	public InputException() {
		super();
	}
	
	public InputException(String mensaje) {
		super(mensaje);
	}
}
