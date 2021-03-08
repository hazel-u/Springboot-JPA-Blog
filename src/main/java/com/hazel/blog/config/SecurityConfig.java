package com.hazel.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.hazel.blog.config.auth.PrincipalDetailService;

// Bean 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는것
@Configuration // 빈 등록 (IoC관리)
@EnableWebSecurity // Security 필터 추가 = 스프링 시큐리티는 이미 활성화 되어있는 상태, 이때 어떤 설정을 해당 파일에서 하겠다. 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled =  true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean // IoC가 된다. -> 이 함수가 return하는 것을 spring이 관리한다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인을 해줄때, password를 가로채기 하는데
	// 해당 password가 무엇으로 해쉬가 돼서 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests()
				.antMatchers("/", "/auth/**","/js/**", "/css/**", "/image/**") 
				.permitAll() // auth쪽으로 들어오는 사람은 누구나 들어올 수 있게 함
				.anyRequest()
				.authenticated() // auth쪽이 아니면 인증이 되어야해
			.and()
				.formLogin()
				.loginPage("/auth/loginForm") // 인증이 되지 않은 모든 페이지에 대한 요청은 loginForm으로 오게된다.
				.loginProcessingUrl("/auth/loginProc") // 스프링 시큐리티가 해당 주소로 오는 로그인을 가로채서 대신 로그인을 해준다.
				.defaultSuccessUrl("/"); // 정상적으로 요청이 완료가 되면 "/"로 이동한다.
				
	}
}
