package com.hazel.blog.sevice;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazel.blog.model.RoleType;
import com.hazel.blog.model.User;
import com.hazel.blog.repository.UserRepository;

// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌 -> IoC를 해준다.
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional // 회원가입이라는 여러개의 transaction을 가진 service가 하나의 transaction으로 바뀌게 된다.
	public void 회원가입(User user) {
		String rawPassword=user.getPassword(); // 1234 원문
		String encPassword=encoder.encode(rawPassword); // 해쉬
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	

}



// 전통적인 로그인 방식(security 없음)
//@Transactional(readOnly = true)// select할때 트랜잭션 시작, 서비스 종료시 트랜잭선 종료(정합성 유지 가능)
//public User 로그인(User user) {
//	return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//}