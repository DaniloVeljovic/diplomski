package com.danilo.diplomski.exceptions;

public class StudentNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2549862859471862698L;

	public StudentNotFoundException(String message)
	{
		super(message);
	}
}
