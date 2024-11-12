package com.oscar.gestionaleclienti.exception;

import java.time.LocalDateTime;

public class ErrorResponse {
	private LocalDateTime timestamp;
	private String message;
	private String details;

	public ErrorResponse(String message, String datails) {
		this.message = message;
		this.details = datails;
		this.timestamp = LocalDateTime.now();
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

}
