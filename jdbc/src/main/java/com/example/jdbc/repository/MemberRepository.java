package com.example.jdbc.repository;

import com.example.jdbc.domain.Member;

/**
 * <pre>
 * com.example.jdbc.repository
 * MemberRepositoryEx.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 16. 오후 5:53:42
 * @desc    : 
 * @version : x.x
 */
public interface MemberRepository {
	
	public Member save(Member member);
	public Member findById(String memberId);
	public Member update(String memberId, int money);
	public int delete(String memberId);
	

}
