package com.danilo.diplomski.exceptions;

public class TeacherNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1108362073918016370L;

	public TeacherNotFoundException(String message)
	{
		super(message);
	}

}
