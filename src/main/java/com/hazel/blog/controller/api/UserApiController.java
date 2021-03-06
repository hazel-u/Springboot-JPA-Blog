package com.hazel.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hazel.blog.dto.ResponseDto;
import com.hazel.blog.model.RoleType;
import com.hazel.blog.model.User;
import com.hazel.blog.sevice.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService UserService;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email
		System.out.println("UsreApiController: save 호출됨");
		UserService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 Json으로 변환시켜서 리턴(Jackson)
	}
}








//전통적인 로그인 방식(security 없음)
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
//		System.out.println("UserApiController : login 호출됨");
//		User principal = UserService.로그인(user); //principal(접근 주체)
//		
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);		
//	}