package com.scmm.hospitalclinicovet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scmm.hospitalclinicovet.exception.IngresoNotFoundException;
import com.scmm.hospitalclinicovet.exception.InputException;
import com.scmm.hospitalclinicovet.exception.MascotaNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    @ExceptionHandler(MascotaNotFoundException.class)
    public ResponseEntity<String> handleMascotaNotFoundException(MascotaNotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(IngresoNotFoundException.class)
    public ResponseEntity<String> handleIngresoNotFoundException(IngresoNotFoundException ex) {
        return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InputException.class)
    public ResponseEntity<String> handleInputException(InputException ex) {
    	return new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Manejo de otras excepciones genéricas
    // No se devuelve ex.getMessage() ya que se pueden exponer los fallos internos del servidor, pudiendo quedar comprometido
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>("Se ha producido un error en el servidor", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
