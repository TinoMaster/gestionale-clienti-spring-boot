package com.oscar.gestionaleclienti.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ClienteNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleClienteNotFoundException(ClienteNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "Cliente non trovato");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(FatturaNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleFatturaNotFoundException(FatturaNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "Fattura non trovata");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(RigaFatturaNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlerRigaFatturaNotFoundException(RigaFatturaNotFoundException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "Riga Fattura non trovata");
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "Argomento non valido");
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handlerIllegalArgumentException(IllegalArgumentException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "Argomento non valido");
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
		System.out.println(ex.getClass());
		ErrorResponse errorResponse = new ErrorResponse("An unexpected error occurred", ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
