package com.example.jdbc.service;

import java.sql.SQLException;

import com.example.jdbc.domain.Member;
import com.example.jdbc.repository.MemberRepositoryV1;

import lombok.RequiredArgsConstructor;

/**
 * <pre>
 * com.example.jdbc.service
 * MemberServicev1.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오전 10:32:50
 * @desc    : 
 * @version : x.x
 */
@RequiredArgsConstructor
public class MemberServiceV1 {

	private final MemberRepositoryV1 memberRepository;
	
	public void accountTransfer(String fromId, String toId, int money) throws SQLException {
		
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
