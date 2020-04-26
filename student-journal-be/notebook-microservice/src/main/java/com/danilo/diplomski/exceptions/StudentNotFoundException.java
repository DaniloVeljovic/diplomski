package com.danilo.diplomski.exceptions;

public class StudentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7746969250603100679L;
	
	public StudentNotFoundException(String msg)
	{
		super(msg);
	}

}
