package com.example.jdbc.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <pre>
 * com.example.jdbc.domain
 * Member.java
 * </pre>
 *
 * @author  : minco
 * @date    : 2023. 3. 15. 오후 4:01:03
 * @desc    : 
 * @version : x.x
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	private String memeberId;
	private int money;
}
