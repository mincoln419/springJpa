package com.example.jdbc.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.ex.MyDbException;

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
 * @desc    : jdbc template 사용
 * 
 * @version : x.x
 */
@Slf4j
public class MemberRepositoryV5 implements MemberRepository{
	
	private final JdbcTemplate template;
	
	/**
	 * @param dataSource
	 * @param exceptionTranslator
	 */
	public MemberRepositoryV5(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}

	public Member save(Member member) {
		String sql = "insert into member(member_id, money) values (?, ?)";
		template.update(sql, member.getMemeberId(), member.getMoney());
		return member;
	}
	
	public Member findById(String memberId) {
		String sql = "select * from member where member_id = ?";
		return template.queryForObject(sql, memberRowMapper(), memberId);

	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : memberRowMapper
	 * @date : 2023. 3. 17.
	 * @author : minco
	 * @history :
	 * ----------------------------------------------------------------------------------
	 * 변경일                        작성자                              변경내역
	 * -------------- -------------- ----------------------------------------------------
	 * 2023. 3. 17.  minco       최초작성
	 * ----------------------------------------------------------------------------------
	 */
	private RowMapper<Member> memberRowMapper() {
		return (rs, rowNum) -> {
			Member member = new Member();
			member.setMemeberId(rs.getString("member_id"));
			member.setMoney(rs.getInt("money"));
			return member;
		};
	}

	public Member update(String memberId, int money) {
		String sql = "update member set money=? where member_id=?";
		template.update(sql, money, memberId);
		return new Member(memberId, money);
	}

	
	public int delete(String memberId) {
		String sql = "delete from member where member_id=?";
		return  template.update(sql, memberId);
	}
	
}
