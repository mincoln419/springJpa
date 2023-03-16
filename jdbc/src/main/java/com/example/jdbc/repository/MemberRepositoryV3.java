package com.example.jdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import com.example.jdbc.connection.DBConnectionUtil;
import com.example.jdbc.domain.Member;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.example.jdbc.repository
 * MemberRepositoryV0.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 15. 오후 4:02:08
 * @desc    : 트랜젝션 매니저
 * @version : x.x
 */
@Slf4j
@AllArgsConstructor
public class MemberRepositoryV3 {
	
	private final DataSource dataSource;
	
	public Member save(Member member) throws SQLException {
		String sql = "insert into member(member_id, money) values (?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, member.getMemeberId());
			pstmt.setInt(2, member.getMoney());
			
			int result = pstmt.executeUpdate();
			
			return member;
		} catch (SQLException e) {
			log.debug(e.getMessage());
			throw e;
		}finally {
			close(con, pstmt, null);
		}
	}
	
	public Member findById(String memberId) throws SQLException {
		String sql = "select * from member where member_id = ?";
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;
		con = getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			result = pstmt.executeQuery();
			
			if(result.next()) {
				Member member = new Member();
				member.setMemeberId(result.getString("member_id"));
				member.setMoney(result.getInt("money"));
				return member;
			}else {
				throw new NoSuchElementException("member not found memberId=" + memberId);
			}
			
		} catch (SQLException e) {
			log.debug(e.getMessage());
			throw e;
		}finally {
			close(con, pstmt, result);
		}
	}

	public Member update(String memberId, int money) throws SQLException {
		String sql = "update member set money=? where member_id=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, money);
			pstmt.setString(2, memberId);
			
			int result = pstmt.executeUpdate();
			log.info("resultSize : {}", result);
			return new Member(memberId, money);
		} catch (SQLException e) {
			log.debug(e.getMessage());
			throw e;
		}finally {
			close(con, pstmt, null);
		}
	}

	
	public int delete(String memberId) throws SQLException {
		String sql = "delete from member where member_id=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		con = getConnection();
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberId);
			
			int result = pstmt.executeUpdate();
			log.info("resultSize : {}", result);
			return result;
		} catch (SQLException e) {
			log.debug(e.getMessage());
			throw e;
		}finally {
			close(con, pstmt, null);
			JdbcUtils.closeConnection(con);
		}
	}
	

	private void close(Connection con, Statement stmt, ResultSet rs) {
		
		JdbcUtils.closeResultSet(rs);
		JdbcUtils.closeStatement(stmt);
		
		//주의 - 트랜젝션 동기화를 사용하려면 DatasourceUtils를 사용
		DataSourceUtils.releaseConnection(con, dataSource);
	}
	
	private Connection getConnection() throws SQLException {
		Connection con = DataSourceUtils.getConnection(dataSource);
		log.debug("get connection={} , class={}", con, con.getClass() );
		return con;
	}
}
