package com.scmm.hospitalclinicovet.controller.dto;

// Clase que representa una respuesta de error del API
public class ErrorResponse {
	private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
