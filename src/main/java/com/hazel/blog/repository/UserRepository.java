package com.hazel.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hazel.blog.model.User;

// DAO (Data Access Object)
// 자동으로 bean으로 등록이 된다. (@Repository 생략 가능)
public interface UserRepository extends JpaRepository<User, Integer>{ // 해당 JpaRepository는 user테이블을 관리하는 레파지토리고, user테이블의 PK는 integer야
	// SELECT *  FROM user WHERE username =?;
	Optional<User> findByUsername(String username);
	

}



// 전통적인 로그인 방식(security 없음)
// 로그인을 위한 함수
//JPA Naming 쿼리
// SELECT * FROM user WHERE username=? AND password=?; 이런 쿼리가 자동으로 날라가
//User findByUsernameAndPassword(String username, String password);

// 위와 동일한 기능을 함, 위가 간단하니 위를 쓴다.
//@Query(value="SELECT * FROM user WHERE username=? AND password=?", nativeQuery=true)
//User login(String username, String password);