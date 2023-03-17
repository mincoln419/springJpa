package com.example.jdbc.exception.translator;

import static com.example.jdbc.connection.ConnectionConset.PASSWORD;
import static com.example.jdbc.connection.ConnectionConset.URL;
import static com.example.jdbc.connection.ConnectionConset.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.jdbc.repository.MemberRepositoryV3;
import com.example.jdbc.service.MemberServiceV3_2;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.example.jdbc.exception.translator
 * SpringExceptionTranslatorTest.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 17. 오전 10:47:18
 * @desc    : 
 * @version : x.x
 */
@Slf4j
public class SpringExceptionTranslatorTest {
	
	DataSource dataSource;
	
	@BeforeEach
	void beforeEach() {
		dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		
	}
	
	@Test
	void sqlExceptionErrorCode() {
		String sql = "select bad grammer";
		
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.execute();
			
		}catch(SQLException e) {
			assertThat(e.getErrorCode()).isEqualTo(42122);
			log.debug("errorCode={}", e.getErrorCode());
			log.debug("error", e);
		}
	}
	
	
	@Test
	void exceptionTranslator() {
		String sql = "select bad grammer";
		try {
			Connection con = dataSource.getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.executeQuery();
		}catch(SQLException e) {
			assertThat(e.getErrorCode()).isEqualTo(42122);
			log.debug("errorCode={}", e.getErrorCode());
			log.debug("error", e);
			SQLErrorCodeSQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
			DataAccessException resultEx = translator.translate("select", sql, e);
			log.info("resultEx", resultEx);
			assertThat(resultEx.getClass()).isEqualTo(BadSqlGrammarException.class);
		}
	}
}
