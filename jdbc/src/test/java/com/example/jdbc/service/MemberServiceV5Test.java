package com.example.jdbc.service;

import static com.example.jdbc.connection.ConnectionConset.PASSWORD;
import static com.example.jdbc.connection.ConnectionConset.URL;
import static com.example.jdbc.connection.ConnectionConset.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepository;
import com.example.jdbc.repository.MemberRepositoryV3;
import com.example.jdbc.repository.MemberRepositoryV4_1;
import com.example.jdbc.repository.MemberRepositoryV4_2;
import com.example.jdbc.repository.MemberRepositoryV5;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

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
@SpringBootTest
@Slf4j
class MemberServiceV5Test {

	@Autowired
	MemberRepository repository;
	
	@Autowired
	MemberServiceV4 memberServiceV4;
	
	String MEMBER_A;
	String MEMBER_B;
	String MEMBER_C;
	

	@TestConfiguration
	static class TestConfig{
		
		@Bean
		DataSource dataSource() {
			return new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		}
		
		@Bean
		PlatformTransactionManager transactionManager() {
			return new DataSourceTransactionManager(dataSource());
		}
		
		@Bean
		MemberRepository memberRepository() {
			return new MemberRepositoryV5(dataSource());
		}
		
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
		memberServiceV4.accountTransfer(MEMBER_A, MEMBER_B, 1000);
		
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
		assertThatThrownBy(() -> memberServiceV4.accountTransfer(MEMBER_A, MEMBER_B, 1000)).isInstanceOf(IllegalStateException.class);
		
		//then
		Member fromMember = repository.findById(MEMBER_A);
		Member toMember = repository.findById(MEMBER_B);
		assertThat(fromMember.getMoney()).isEqualTo(10000);
		assertThat(toMember.getMoney()).isEqualTo(10000);
		
	}
	
	@Test
	void appCheck() {
		log.info("memberService class={}", memberServiceV4.getClass());
		log.info("memberRepository class={}", repository.getClass());
		
		assertThat(AopUtils.isAopProxy(memberServiceV4)).isTrue();
		assertThat(AopUtils.isAopProxy(repository)).isFalse();//AOP 관련 소스가 없기 때문에 proxy 객체로 만들지 않음
	}

}
