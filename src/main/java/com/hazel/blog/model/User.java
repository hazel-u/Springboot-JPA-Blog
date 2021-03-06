package com.hazel.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity // User 클래스가 MySQL에 자동으로 테이블이 생성된다.
//@DynamicInsert(insert할때 null인 필드를 제외시켜줌)
public class User {
	
	@Id // primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) // 프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable=false, length=30, unique = true)
	private String username; // 아이디
	
	@Column(nullable=false, length=100) // 나중에 암호화해서 데이터베이스에 비밀번호를 넣을거임
	private String password;
	
	@Column(nullable=false, length=50)
	private String email;
	
	//@ColumnDefault("'user'") // 얘는 defalut값이라 뭔 값이 들어오는 순간 이게 의미 없어짐 -> insert할때 role을 빼는 방법을 찾아야해 -> @DynamicInsert를 사용해보자
	// -> 계속 이방법을 사용하며 덕지덕지 붙일 수는 없어!
	//private String role; //Enum을 쓰는게 좋다. (admin, user, manager등등) string타입으로 하면 이상한 스트링이 들어갈 수 있다. enum으로 도메인을 설정할 수 있다.
	
	// DB는 RoleType이라는게 없다.
	@Enumerated(EnumType.STRING) // 이걸 붙여서 해당 enum이 string이라는 것을 알려줘
	private RoleType role;
	
	@CreationTimestamp // 시간이 자동으로 입력됨
	private Timestamp createDate;
}
