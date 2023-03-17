package com.example.jdbc.exception.translator;

import static com.example.jdbc.connection.ConnectionConset.PASSWORD;
import static com.example.jdbc.connection.ConnectionConset.URL;
import static com.example.jdbc.connection.ConnectionConset.USERNAME;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV1;
import com.example.jdbc.repository.MemberRepositoryV3;
import com.example.jdbc.repository.ex.MyDbException;
import com.example.jdbc.repository.ex.MyDuplicationException;
import com.example.jdbc.service.MemberServiceV1;
import com.example.jdbc.service.MemberServiceV3_2;
import com.zaxxer.hikari.HikariDataSource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.example.jdbc.connection.ConnectionConset.*;

/**
 * <pre>
 * com.example.jdbc.exception.translator
 * ExTranslatorV1Test.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오후 6:19:22
 * @desc    : 
 * @version : x.x
 */
@Slf4j
public class ExTranslatorV1Test {
	
	Repository repository;
	
	Service service;
	
	@BeforeEach
	void beforeEach() {
		//DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
		DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);                                                                         
		repository = new Repository(dataSource); 
		service = new Service(repository);
	}
	

	@RequiredArgsConstructor
	static class Repository{
		
		private final DataSource dataSource;
		
		public Member save(Member member) {
			String sql = "insert into member(member_id, money) values (?, ?)";
			Connection con = null;
			PreparedStatement pstmt = null;
			con = getConnection();
			try {
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, member.getMemeberId());
				pstmt.setInt(2, member.getMoney());
				
				pstmt.executeUpdate();
				
				return member;
			} catch (SQLException e) {
				log.debug(e.getMessage());
				
				if(e.getErrorCode() == 23505) {
					throw new MyDuplicationException(e);
				}
				
				throw new MyDbException(e);
			}finally {
				close(con, pstmt, null);
			}
		}
		private Connection getConnection() {
			Connection con = DataSourceUtils.getConnection(dataSource);
			log.debug("get connection={} , class={}", con, con.getClass() );
			return con;
		}
		
		private void close(Connection con, Statement stmt, ResultSet rs) {
			
			JdbcUtils.closeResultSet(rs);
			JdbcUtils.closeStatement(stmt);
			
			//주의 - 트랜젝션 동기화를 사용하려면 DatasourceUtils를 사용
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}
	
	@RequiredArgsConstructor
	static class Service{
		private final Repository repository;
		
		public void create(String memberId, int money) {
			try {
				repository.save(new Member(memberId, money));
				log.info("savedId = {}", memberId);
			}catch(MyDuplicationException e) {
				log.info("키 중복, 복구 시도");
				String retryId = generatedNewId(memberId);
				log.info("retryId = {}", retryId);
				repository.save(new Member(retryId, money));
			}catch(MyDbException e) {
				log.info("데이터 접근 계층 예외" , e);
				throw e;
			}
		}
		
		private String generatedNewId(String memberId) {
			return memberId + new Random().nextInt();
		}
	}
	
	
	@Test
	public void duplication_error() {
		service.create("member1", 1000);
		service.create("member1", 1000);
	}
	
}
