package com.hazel.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.hazel.blog.model.User;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝틀르
// 스프링 시큐리티의 고유한 세션 저장소에 저장을 해준다. -> 그때 저장되는게 UserDetails 타입의 PrincipalDetail
public class PrincipalDetail implements UserDetails{
	
	private User user; //객체를 품고 있음 (컴포지션)(extends해서 들고오는 상속과는 다른거임)

	public PrincipalDetail(User user) {
		this.user=user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	
	// 계정이 만료되지않았는지 리턴한다. (true : 만료 안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있지 않은지 리턴한다. (true : 안잠겨있음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴한다. (true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정 활성화가 되어있는지 리턴한다. (true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	// 계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야하는데, 우리는 한개라서 필요없음)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>();
		
//		collectors.add(new GrantedAuthority() {
//			
//			@Override
//			public String getAuthority() {
//				return "ROLE_"+user.getRole(); // ROLE_은 스프링에서 Role을 받을때의 규칙! 꼭 넣어줘야해
//			}
//		});
		
		// 위를 lambda로 적기
		collectors.add(()->{return "ROLE_"+user.getRole();});
		
		return collectors;
	}
}
