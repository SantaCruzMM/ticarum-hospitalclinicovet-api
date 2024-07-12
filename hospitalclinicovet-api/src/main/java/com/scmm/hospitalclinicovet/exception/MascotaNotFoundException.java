package com.scmm.hospitalclinicovet.exception;

// Clase que representa una excepción de mascota no encontrada
@SuppressWarnings("serial")
public class MascotaNotFoundException extends RuntimeException {
	
	public MascotaNotFoundException() {
		super();
	}
	
	public MascotaNotFoundException(Long id) {
		super("No se ha encontrado la mascota con ID: " + id);
	}
}
