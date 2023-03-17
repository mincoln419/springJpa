package com.example.jdbc.repository.ex;

/**
 * <pre>
 * com.example.jdbc.repository.ex
 * MyDuplicationException.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오후 6:23:09
 * @desc    : 
 * @version : x.x
 */
public class MyDuplicationException extends RuntimeException {

	private static final long serialVersionUID = -8858388182891575802L;

	public MyDuplicationException() {
		super();
	}

	public MyDuplicationException(Exception e) {
		super(e);
	}
	
	public MyDuplicationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MyDuplicationException(Throwable cause) {
		super(cause);
	}

}
