package com.scmm.hospitalclinicovet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scmm.hospitalclinicovet.controller.dto.ErrorResponse;
import com.scmm.hospitalclinicovet.exception.IngresoNotFoundException;
import com.scmm.hospitalclinicovet.exception.InputException;
import com.scmm.hospitalclinicovet.exception.MascotaNotFoundException;

// Clase para gestionar las excepciones generadas por la aplicación y devolver una respuesta adecuada al usuario del API
@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(MascotaNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleMascotaNotFoundException(MascotaNotFoundException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(IngresoNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleIngresoNotFoundException(IngresoNotFoundException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InputException.class)
    public ResponseEntity<ErrorResponse> handleInputException(InputException ex) {
    	return new ResponseEntity<ErrorResponse>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Manejo de otras excepciones genéricas
    // No se devuelve ex.getMessage() ya que se pueden exponer los fallos internos del servidor, pudiendo quedar comprometido
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse("Se ha producido un error en el servidor"),
        		HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
