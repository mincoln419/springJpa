package com.example.jdbc.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import com.example.jdbc.domain.Member;

/**
 * <pre>
 * com.example.jdbc.repository
 * MemberRepositoryV0Test.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 15. 오후 4:15:01
 * @desc    : 
 * @version : x.x
 */
class MemberRepositoryV0Test {

	MemberRepositoryV0 repository = new MemberRepositoryV0();
	
	@Test
	void crud() throws SQLException {
		Member member = new Member("meremr", 3000);
		repository.save(member);
	}

}
