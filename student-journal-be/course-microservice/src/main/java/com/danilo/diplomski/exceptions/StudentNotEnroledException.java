package com.danilo.diplomski.exceptions;

public class StudentNotEnroledException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6515383113683793349L;

	public StudentNotEnroledException(String msg)
	{
		super(msg);
	}
}
