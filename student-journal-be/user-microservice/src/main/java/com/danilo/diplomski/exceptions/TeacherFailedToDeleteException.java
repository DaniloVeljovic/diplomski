package com.danilo.diplomski.exceptions;

public class TeacherFailedToDeleteException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2549862859471862698L;

	public TeacherFailedToDeleteException(String message)
	{
		super(message);
	}
}
