package com.hazel.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.hazel.blog.config.auth.PrincipalDetail;

// auth -> 인증이 안된 사용자들이 출입할 수 있는 경로 /auth/** 허용 -> 인증이 필요없는 곳에는 auth를 붙일거야
// 그냥 주소가 /이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {		
		return "user/joinForm";
	}
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
		return "user/updateForm";
	}
}
