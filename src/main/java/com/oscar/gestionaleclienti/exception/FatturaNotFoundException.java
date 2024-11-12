package com.oscar.gestionaleclienti.exception;

public class FatturaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FatturaNotFoundException(String message) {
		super(message);
	}

}
