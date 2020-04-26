package com.danilo.diplomski.exceptions;

public class NotebookNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7073405775938110225L;
	
	public NotebookNotFoundException(String msg)
	{
		super(msg);
	}

}
