package com.danilo.diplomski.exceptions;

public class StudentNotCreatedException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2549862859471862698L;

	public StudentNotCreatedException(String message)
	{
		super(message);
	}
}
