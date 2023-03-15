package com.example.jdbc.connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.example.jdbc.connection
 * DBConnectionUtilTest.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 15. 오후 3:41:52
 * @desc    : 
 * @version : x.x
 */
@Slf4j
class DBConnectionUtilTest {

	@Test
	void connection() {
		Connection connection = DBConnectionUtil.getConnection();
		assertThat(connection).isNotNull();
		
	}

}
