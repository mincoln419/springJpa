package com.example.jdbc.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.extern.slf4j.Slf4j;

import static com.example.jdbc.connection.ConnectionConset.*;

/**
 * <pre>
 * com.example.jdbc.connection
 * DBConnectionUtil.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 15. 오후 3:37:08
 * @desc    : 
 * @version : x.x
 */
@Slf4j
public class DBConnectionUtil {
	
	public static Connection getConnection() {
		
		try {
			Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
			log.debug("get connection={}, class={}", connection, connection.getClass());
			return connection;
		} catch (SQLException e) {
			log.debug(e.getMessage());
			throw new IllegalStateException(e);
		}
		
	}

}
