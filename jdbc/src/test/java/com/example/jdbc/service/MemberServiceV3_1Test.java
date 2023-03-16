package com.example.jdbc.service;

import static com.example.jdbc.connection.ConnectionConset.PASSWORD;
import static com.example.jdbc.connection.ConnectionConset.URL;
import static com.example.jdbc.connection.ConnectionConset.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV1;
import com.example.jdbc.repository.MemberRepositoryV2;
import com.example.jdbc.repository.MemberRepositoryV3;
import com.zaxxer.hikari.HikariDataSource;

/**
 * <pre>
 * com.example.jdbc.service
 * MemberServiceV1Test.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오전 10:38:14
 * @desc    : 트랜젝션 매니저
 * @version : x.x
 */
class MemberServiceV3_1Test {
	MemberRepositoryV3 repository;
	MemberServiceV3_1 memberServiceV3_1;
	
	String MEMBER_A;
	String MEMBER_B;
	String MEMBER_C;
	
	@BeforeEach
	void beforeEach() {
		//DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		HikariDataSource dataSource = new HikariDataSource();
		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource); 
		dataSource.setJdbcUrl(URL);
		dataSource.setUsername(USERNAME);
		dataSource.setPassword(PASSWORD);
		repository = new MemberRepositoryV3(dataSource); 
		memberServiceV3_1 = new MemberServiceV3_1(transactionManager, repository);
	}
	
	@AfterEach
	void afterEach() throws SQLException {
		repository.delete(MEMBER_A);
		repository.delete(MEMBER_B);
		repository.delete(MEMBER_C);
	}
	
	@Test
	void transf_test_success() throws SQLException {
		//given
		MEMBER_A = "hi1";
		MEMBER_B = "hi2";
		Member A = new Member(MEMBER_A, 10000);
		Member B = new Member(MEMBER_B, 10000);
		repository.save(A);
		repository.save(B);
		//when
		memberServiceV3_1.accountTransfer(MEMBER_A, MEMBER_B, 1000);
		
		Member fromMember = repository.findById(MEMBER_A);
		Member toMember = repository.findById(MEMBER_B);
		//then
		assertThat(fromMember.getMoney()).isEqualTo(9000);
		assertThat(toMember.getMoney()).isEqualTo(11000);
		
	}
	
	@Test
	void transf_test_ex() throws SQLException {
		//given
		MEMBER_A = "memberA";
		MEMBER_B = "ex";
		Member A = new Member(MEMBER_A, 10000);
		Member B = new Member(MEMBER_B, 10000);
		repository.save(A);
		repository.save(B);
	
		//when
		assertThatThrownBy(() -> memberServiceV3_1.accountTransfer(MEMBER_A, MEMBER_B, 1000)).isInstanceOf(IllegalStateException.class);
		
		//then
		Member fromMember = repository.findById(MEMBER_A);
		Member toMember = repository.findById(MEMBER_B);
		assertThat(fromMember.getMoney()).isEqualTo(10000);
		assertThat(toMember.getMoney()).isEqualTo(10000);
		
	}

}
