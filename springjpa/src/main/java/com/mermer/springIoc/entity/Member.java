package com.mermer.springIoc.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <pre>
 * com.mermer.springIoc.entity
 * Member.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 20. 오후 4:14:39
 * @desc    : 
 * @version : x.x
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String username;
	private int age;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_id")
	private Team team;

	/**
	 * @param username
	 * @param age
	 * @param team
	 */
	public Member(String username, int age, Team team) {
		this.username = username;
		this.age = age;
		if(team != null){
			changeTeam(team);
		}
	}
	
	public Member(String username, int age) {
		this(username, age, null);
	}
	
	public Member(String username) {
		this(username, 0);
	}

	/**
	 * <pre>
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : changeTeam
	 * @date : 2023. 3. 20.
	 * @author : minco
	 * @history :
	 * ----------------------------------------------------------------------------------
	 * 변경일                        작성자                              변경내역
	 * -------------- -------------- ----------------------------------------------------
	 * 2023. 3. 20.  minco       최초작성
	 * ----------------------------------------------------------------------------------
	 */
	public void changeTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}
	
	
	
}
