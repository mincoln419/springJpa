package com.example.jdbc.repository.ex;

/**
 * <pre>
 * com.example.jdbc.repository.ex
 * MyDbException.java
 * </pre>
 *
 * @author : minco
 * @date : 2023. 3. 16. 오후 5:58:43
 * @desc :
 * @version : x.x
 */
public class MyDbException extends RuntimeException {

	private static final long serialVersionUID = -7643794234376373156L;

	public MyDbException() {
		super();
	}

	public MyDbException(Exception e) {
		super(e);
	}
	
	public MyDbException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public MyDbException(Throwable cause) {
		super(cause);
	}
}
