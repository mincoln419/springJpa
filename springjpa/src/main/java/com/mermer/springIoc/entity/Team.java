package com.mermer.springIoc.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * com.mermer.springIoc.entity
 * Team.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 20. 오후 4:15:48
 * @desc    : 
 * @version : x.x
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "name"})
public class Team {

	@Id @GeneratedValue
	@Column(name = "team_id")
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "team")
	private List<Member> members = new ArrayList<>();

	/**
	 * @param name
	 */
	public Team(String name) {
		this.name = name;
	}
	
	
}
