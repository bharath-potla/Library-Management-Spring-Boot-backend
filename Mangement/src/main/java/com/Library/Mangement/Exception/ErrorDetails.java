package com.Library.Mangement.Exception;

import java.time.LocalDate;

public class ErrorDetails {
	public ErrorDetails(LocalDate timeStramp, String message, String details) {
		super();
		this.timeStramp = timeStramp;
		this.message = message;
		this.details = details;
	}
	private LocalDate timeStramp;
	private String message;
	private String details;
	public LocalDate getTimeStramp() {
		return timeStramp;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}
	
	
}
