package com.example.jdbc.exception.basic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.example.jdbc.exception.basic
 * CheckedTest.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오후 4:37:29
 * @desc    : 
 * @version : x.x
 */
@Slf4j
public class CheckedTest {
	
	/*
	 * exception을 상속받은 예외는 checked 예외가 된다.
	 * */
	static class MyCheckedException extends Exception{
		
		public MyCheckedException(String message) {
			super(message);
		}
	}
	
	/*
	 * Checked Exception은 try-catch 또는 throw를 통해 처리하지 않으면 컴파일 오류 발생
	 * */
	static class Service{
		Repository repository = new Repository();
		
		/*
		 * 예외를 잡아서 처리하는 코드
		 * */
		public void callCatch() {
			try {
				repository.call();
			} catch (MyCheckedException e) {
				log.info("error message = {}", e.getMessage(), e);
			}
		}


		public void callThrow() throws MyCheckedException {
			repository.call();
		}
	}

	static class Repository{
		public void call() throws MyCheckedException {
			throw new MyCheckedException("ex");
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
		assertThatThrownBy(() -> service.callThrow()).isInstanceOf(MyCheckedException.class) ;
	}
}
