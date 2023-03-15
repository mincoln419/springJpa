package com.example.jdbc.connection;

import static com.example.jdbc.connection.ConnectionConset.PASSWORD;
import static com.example.jdbc.connection.ConnectionConset.URL;
import static com.example.jdbc.connection.ConnectionConset.USERNAME;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.example.jdbc.connection
 * ConnectionTest.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 15. 오후 5:39:00
 * @desc    : 
 * @version : x.x
 */
@Slf4j
public class ConnectionTest {

	
	@Test
	void driverManager() throws SQLException {
		Connection connection1 = DriverManager.getConnection(URL,USERNAME,PASSWORD);
		Connection connection2 = DriverManager.getConnection(URL,USERNAME,PASSWORD);
	}
	
	@Test
	void dataSource() throws SQLException {
		DataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		useDataSource(dataSource);
	}
	
	private void useDataSource(DataSource dataSource) throws SQLException {
		Connection connection1 = dataSource.getConnection();
		Connection connection2 = dataSource.getConnection();
		log.info("connection={}, class={}", connection1, connection1.getClass());
		log.info("connection={}, class={}", connection2, connection2.getClass());
	}
}
