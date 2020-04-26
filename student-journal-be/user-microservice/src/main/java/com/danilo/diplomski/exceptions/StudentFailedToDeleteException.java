package com.danilo.diplomski.exceptions;

public class StudentFailedToDeleteException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2549862859471862698L;

	public StudentFailedToDeleteException(String message)
	{
		super(message);
	}
}
