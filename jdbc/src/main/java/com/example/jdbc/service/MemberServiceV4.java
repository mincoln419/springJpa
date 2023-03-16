package com.example.jdbc.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.example.jdbc.service
 * MemberServicev1.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오전 10:32:50
 * @desc    : 트랜잭션 - @Transactional AOP - SQL Exception 제거, MemberRepository 인터페이스 사용
 * @version : x.x
 */
@Slf4j
@Service
public class MemberServiceV4 {

	private final MemberRepository memberRepository;
	
	
	/**
	 * @param transactionTemplate
	 * @param memberRepository
	 */
	public MemberServiceV4( MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional
	public void accountTransfer(String fromId, String toId, int money) {
		bizProcess(fromId, toId, money);
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
	private void bizProcess(String fromId, String toId, int money) {
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
