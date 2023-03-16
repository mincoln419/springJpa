package com.example.jdbc.service;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.example.jdbc.service
 * MemberServicev1.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오전 10:32:50
 * @desc    : 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 * @version : x.x
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV2 {

	private final DataSource dataSource;
	private final MemberRepositoryV2 memberRepository;
	
	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		
		Connection con  = dataSource.getConnection();
		
		try {
			con.setAutoCommit(false);//트랜젝션 시작
			//비즈니스로직 수행
			Member fromMember =  memberRepository.findById(fromId);
			Member toMember =  memberRepository.findById(toId);
			
			memberRepository.update(fromId, fromMember.getMoney() - money, con);
			validator(toMember);
			memberRepository.update(toId, toMember.getMoney() + money, con);
			
			con.commit();//성공시 commit
			
		}catch(Exception e) {
			con.rollback();// 실패시 롤백
			throw new IllegalStateException(e);
		}finally {
			releaseConnection(con);
		}
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : releaseConnection
	 * @date : 2023. 3. 16.
	 * @author : minco
	 * @history :
	 * ----------------------------------------------------------------------------------
	 * 변경일                        작성자                              변경내역
	 * -------------- -------------- ----------------------------------------------------
	 * 2023. 3. 16.  minco       최초작성
	 * ----------------------------------------------------------------------------------
	 */
	private void releaseConnection(Connection con) {
		if(con != null) {
			try {
				con.setAutoCommit(true);
				con.close();
			}catch(Exception e) {
				log.info("err", e);
			}
		}
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : validator
	 * @date : 2023. 3. 16.
	 * @author : minco
	 * @history :
	 * ----------------------------------------------------------------------------------
	 * 변경일                        작성자                              변경내역
	 * -------------- -------------- ----------------------------------------------------
	 * 2023. 3. 16.  minco       최초작성
	 * ----------------------------------------------------------------------------------
	 */
	private void validator(Member toMember) {
		if(toMember.getMemeberId().equals("ex")) {
			throw new IllegalStateException("이체중 예외 발생");
		}
	}
}
