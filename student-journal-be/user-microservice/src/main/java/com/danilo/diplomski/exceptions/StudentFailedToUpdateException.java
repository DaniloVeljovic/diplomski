package com.danilo.diplomski.exceptions;

public class StudentFailedToUpdateException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2549862859471862698L;

	public StudentFailedToUpdateException(String message)
	{
		super(message);
	}
}
