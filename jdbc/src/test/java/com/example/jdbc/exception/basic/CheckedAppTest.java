package com.example.jdbc.exception.basic;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.net.ConnectException;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.example.jdbc.exception.basic.UnCheckedAppTest.Controller;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.example.jdbc.exception.basic
 * CheckedAppTest.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오후 5:11:36
 * @desc    : 
 * @version : x.x
 */
@Slf4j
public class CheckedAppTest {
	
	static class RuntimeSqlException extends RuntimeException{
		private static final long serialVersionUID = 8684936731050520971L;
		public RuntimeSqlException(String message) {
			super(message);
		}
	}
	
	static class RuntimeConnectionException extends RuntimeException{
		private static final long serialVersionUID = 2217413399456712499L;
		public RuntimeConnectionException(String message) {
			super(message);
		}
	}

	static class Controller{
		Service service = new Service();
		
		public void request() {
			service.logic();
		}
	}
	
	/*
	 * Checked Exception은 try-catch 또는 throw를 통해 처리하지 않으면 컴파일 오류 발생
	 * */
	static class Service{
		Repository repository = new Repository();
		NetworkClient networkClient = new NetworkClient(); 
		/*
		 * 예외를 잡아서 처리하는 코드
		 * */
		public void logic(){
			networkClient.call();
			repository.call();			
		}

	}

	static class Repository{
		public void call() {
			throw new RuntimeSqlException("쿼리 syntax 에러");
		}
	}
	
	static class NetworkClient {
		public void call() {
			throw new RuntimeConnectionException("연결실패");
		}
	}
	
	@Test
	void checked() {
		Controller controller = new Controller();
		assertThatThrownBy(() -> controller.request()).isInstanceOf(RuntimeConnectionException.class);
	}
	
	
	@Test
	void printEx() {
		Controller controller = new Controller();
		try {
			controller.request();	
		}catch(Exception e) {
			log.info("ex",e.getMessage(), e);
		}
		
	}
	
}
