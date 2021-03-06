package com.hazel.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
	@Getter
	@Setter
	@Data // Getter + Setter = Data
	@AllArgsConstructor // 생성자 만드는거, 요새는 final(불변성 유지)로 많이씀, 만약 password를 중간에 바꾸는 걸로 하고싶다면 final을 붙이면 안돼
	@RequiredArgsConstructor // final붙은거에 대한 constructor(생성자)를 만들어준다.
	@NoArgsConstructor // 빈 생성자 만드는 것
*/

@Data
@NoArgsConstructor
public class Member {
	
	// JAVA에서 변수는 다 private으로 만든다.
	// 객체지향에서는 메서드를 통해서 변수 값이 변경되도록 해야 객체지향의 의도와 맞다.
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
