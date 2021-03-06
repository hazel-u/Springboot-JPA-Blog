package com.hazel.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 사용자가 요청 -> 응답 (HTML 파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

	private static final String TAG="HttpControllerTest : ";
		
	@GetMapping("/http/lombok")
	public String lombokTest() {
		//Member m=new Member(1,"ssar","1234","email"); 그냥 생성자로 했을때
		// Builder로 했을 때 : id를 데베가 알아서 증가하게 두고 싶어서 보내지 않을때, 생성자를 다시 만들 필요가 없게됨, 생성자의 순서를 안지켜도 돼
		Member m=Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG +"getter : "+m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG +"setter : "+m.getUsername());
		return "lombok test 완료";
	}
			
	// 인터넷 브라우저 요청은 무조건 get요청밖에 할 수 없다.
	// http://localhost:8080/http/get (select)
	@GetMapping("/http/get")
	public String getTest(Member m) {	
		
		return "get 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword() + ", "+m.getEmail();
	}

	// http://localhost:8080/http/post (insert)
	@PostMapping("/http/post")
	public String postTest(@RequestBody Member m) { 
		// x-www-form-urlencoded 사용(Body) :Member m 
		// raw(text/plain) 사용(Body) : @RequestBody String text(text)
		// raw(application/json) 사용 (Body) : @RequestBody Member m  -> MessageConverter(스프링부트) 얘가 json파일을 자동으로 mapping해서 m에 넣어준다.
		return "post 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword() + ", "+m.getEmail();
	}

	// http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청 : "+m.getId()+", "+m.getUsername()+", "+m.getPassword() + ", "+m.getEmail();
	}

	// http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
