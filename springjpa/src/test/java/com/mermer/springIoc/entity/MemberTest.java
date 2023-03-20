package com.mermer.springIoc.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * com.mermer.springIoc.entity
 * MemberTest.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 20. 오후 4:26:01
 * @desc    : 
 * @version : x.x
 */
@SpringBootTest
@Transactional
@Slf4j
class MemberTest {

	@Autowired
	EntityManager em;
	
	@Test
	void test_entity() {
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		em.persist(teamA);
		em.persist(teamB);
		
		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 20, teamA);
		Member member3 = new Member("member3", 30, teamB);
		Member member4 = new Member("member4", 40, teamB);
		
		em.persist(member1);
		em.persist(member2);
		em.persist(member3);
		em.persist(member4);
		
		//초기화
		em.flush();
		em.clear();
		
		List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
		
		for(Member member : members) {
			log.debug("member =" + member);
			log.debug("-> =" + member.getTeam());
		}
	}

}
