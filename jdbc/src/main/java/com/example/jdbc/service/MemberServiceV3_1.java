package com.example.jdbc.service;

import java.sql.SQLException;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV3;
import com.zaxxer.hikari.HikariDataSource;

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
 * @desc    : 트랜잭션 - 트랜잭션 매니저
 * @version : x.x
 */
@RequiredArgsConstructor
@Slf4j
public class MemberServiceV3_1 {

	private final PlatformTransactionManager transactionManager;
	private final MemberRepositoryV3 memberRepository;
	
	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			//비즈니스로직 수행
			Member fromMember =  memberRepository.findById(fromId);
			Member toMember =  memberRepository.findById(toId);
			
			memberRepository.update(fromId, fromMember.getMoney() - money);
			validator(toMember);
			memberRepository.update(toId, toMember.getMoney() + money);
			
			transactionManager.commit(status);
		}catch(Exception e) {
			transactionManager.rollback(status);
			throw new IllegalStateException(e);
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
