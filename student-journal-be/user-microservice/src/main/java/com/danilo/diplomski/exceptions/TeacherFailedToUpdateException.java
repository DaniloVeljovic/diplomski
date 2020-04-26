package com.danilo.diplomski.exceptions;

public class TeacherFailedToUpdateException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2549862859471862698L;

	public TeacherFailedToUpdateException(String message)
	{
		super(message);
	}
}
