package com.example.jdbc.service;

import java.sql.SQLException;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

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
 * @desc    : 트랜잭션 - 트랜잭션 템플릿
 * @version : x.x
 */
@Slf4j
public class MemberServiceV3_2 {

	private final TransactionTemplate transactionTemplate;
	private final MemberRepositoryV3 memberRepository;

	
	
	/**
	 * @param transactionTemplate
	 * @param memberRepository
	 */
	public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
		this.transactionTemplate = new TransactionTemplate(transactionManager);
		this.memberRepository = memberRepository;
	}

	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		
		transactionTemplate.executeWithoutResult((status)->{
			try {
			//	비즈니스로직 수행
				bizProcess(fromId, toId, money);
			}catch(Exception e) {
				throw new IllegalStateException(e);
			}
		});
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : bizProcess
	 * @date : 2023. 3. 16.
	 * @author : minco
	 * @history :
	 * ----------------------------------------------------------------------------------
	 * 변경일                        작성자                              변경내역
	 * -------------- -------------- ----------------------------------------------------
	 * 2023. 3. 16.  minco       최초작성
	 * ----------------------------------------------------------------------------------
	 */
	private void bizProcess(String fromId, String toId, int money) throws SQLException {
		Member fromMember =  memberRepository.findById(fromId);
		Member toMember =  memberRepository.findById(toId);
		
		memberRepository.update(fromId, fromMember.getMoney() - money);
		validator(toMember);
		memberRepository.update(toId, toMember.getMoney() + money);
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
