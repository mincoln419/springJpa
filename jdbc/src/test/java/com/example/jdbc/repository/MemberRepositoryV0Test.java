package com.example.jdbc.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@Rollback
class MemberRepositoryV0Test {

	MemberRepositoryV0 repository = new MemberRepositoryV0();
	
	@Test
	@Transactional
	void insert() throws SQLException {
		Member member = new Member("mermer9", 3000);
		repository.save(member);
	}
	
	@Test
	@Transactional(readOnly = true)
	void select() throws SQLException {
		assertThat( repository.findById("meremr")).isNotNull();
	}
	
	@Test
	@Transactional(readOnly = true)
	void update() throws SQLException {
		Member member = new Member("mermer9", 6000);
		Member result = repository.update(member);
		assertThat( result).isNotNull();
		assertThat( result.getMoney()).isEqualTo(6000);
	}
	
	@Test
	@Transactional(readOnly = true)
	void delete() throws SQLException {
		Member member = new Member("mermer9", 6000);
		Member result = repository.delete(member);
		assertThat( result).isNotNull();
		assertThatThrownBy(() -> repository.findById("mermer9")).isInstanceOf(NoSuchElementException.class);
	}
	
	@Test
	void crud() throws SQLException {
		Member member = new Member("mermer9", 3000);
		Member result = repository.save(member);
		assertThat( result).isNotNull();
		assertThat( repository.findById("meremr")).isNotNull();
		
		member = new Member("mermer9", 6000);
		result = repository.update(member);
		assertThat( result).isNotNull();
		assertThat( result.getMoney()).isEqualTo(6000);
		
		result = repository.delete(member);
		assertThat( result).isNotNull();
		assertThatThrownBy(() -> repository.findById("mermer9")).isInstanceOf(NoSuchElementException.class);
	}

}
