package com.hazel.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hazel.blog.config.auth.PrincipalDetail;
import com.hazel.blog.dto.ResponseDto;
import com.hazel.blog.model.RoleType;
import com.hazel.blog.model.User;
import com.hazel.blog.sevice.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService UserService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email
		System.out.println("UsreApiController: save 호출됨");
		UserService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바 오브젝트를 Json으로 변환시켜서 리턴(Jackson)
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){ //RequestBody 어노테이션이 있어야 JSON형식을 받을 수 있어
		UserService.회원수정(user);
		
		// 여기서는 트랜잭션이 종료되기 때문에 DB의 값은 변경이 되었지만
		// 세션값은 변경되지 않은 상태이기 때문에 로그아웃을 하고 다시 들어가야지만 변경된것을 확인할 수 있다.
		// 우리가 직접 세션값을 변경해줄거야!
		// 세션 등록
		Authentication authentication =
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
				
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
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