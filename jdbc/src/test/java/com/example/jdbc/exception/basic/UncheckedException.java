package com.example.jdbc.exception.basic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import com.example.jdbc.exception.basic.CheckedTest.MyCheckedException;
import com.example.jdbc.exception.basic.CheckedTest.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.example.jdbc.exception.basic
 * UncheckedException.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오후 4:56:50
 * @desc    : 
 * @version : x.x
 */
@Slf4j
public class UncheckedException {

	/*
	 * Runtime exception 상속받으면 unchecked Exception 발생
	 * */
	static class MyUncheckedException extends RuntimeException{
		
		public MyUncheckedException(String message) {
			super(message);
		}
		
	}
	
	/*
	 * UnChecked Exception은 예외를 잡거나 던지지 않아도 된다. 예외를 잡지 않으면 자동으로 밖으로 던진다.
	 * */
	static class Service{
		Repository repository = new Repository();
		
		/*
		 * 예외를 잡아서 처리하는 코드
		 * */
		public void callCatch() {
			try {
				repository.call();
			} catch (MyUncheckedException e) {
				log.info("error message = {}", e.getMessage(), e);
			}
		}


		/*
		 * 예외를 던지는 코드
		 * */
		public void callThrow(){
			repository.call();
		}
	}

	static class Repository{
		public void call() {
			throw new MyUncheckedException("ex");
		}
	}
	
	@Test
	void checked_catch() {
		Service service  = new Service();
		service.callCatch();
		
	}
	
	@Test
	void checked_throw() {
		Service service  = new Service();
		assertThatThrownBy(() -> service.callThrow()).isInstanceOf(MyUncheckedException.class) ;
	}
}
