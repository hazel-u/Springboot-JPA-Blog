package com.hazel.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hazel.blog.model.RoleType;
import com.hazel.blog.model.User;
import com.hazel.blog.repository.UserRepository;

// html파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

	@Autowired //DummycontrollerTest가 메모리에 뜰때 userRepository도 같이 뜨게된다. -> 의존성 주입(DI)
	private UserRepository userRepository;
	
	// ------------------------------------------ delete --------------------------------------------
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch(EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id : "+id;
	}
	
	// ------------------------------------------ update -----------------------------------------------
	// email, password key 수정
	@Transactional // 함수 종료시에 자동 commit
	@PutMapping("/dummy/user/{id}") // GetMapping이랑 주소가 같아도 얘는 Put이니 알아서 함
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // Json 데이터를 요청 -> 스프링이 Java Object(Message Conver의 Jackson라이브러리)로 변환해서 받아줘
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		// save함수로 하는 법
		// save함수는 id를 전달하지 않으면 insert를 해주고
		//								전달할때 해당 id에 대한 데이터가 있으면 update를 해주고
		// 																					없으면 insert를 한다.
//		User user=userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("수정에 실패하였습니다.");
//		});
//		user.setPassword(requestUser.getPassword());
//		user.setEmail(requestUser.getEmail());
//		userRepository.save(user); 
		
		
		// Transactional 어노테이션으로 하는 법 = 더티 체킹
		User user=userRepository.findById(id).orElseThrow(()->{ // 여기서 영속성컨텍스트 내의 1차캐시에 영속화
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword()); // user 오브젝트 변경
		user.setEmail(requestUser.getEmail());
		
		return user;
	} // 함수가 끝남과 동시에 commit (user오브젝트에 변경된 점이 있으면 update, 없으면 안함 => 이거를 더티체킹 이라고 함)
	
	
	
	//------------------------------------------ select -----------------------------------------------
	// http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// 한 페이지당 2건의 데이터를 return
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUsers = userRepository.findAll(pageable);
		
		List<User> users=pagingUsers.getContent();
		return users;
	}
	
	// {id} 주소로 파라미터를 전달 받을 수 있다.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/4를 찾을때 내가 데베에서 못찾아오게 되면 user가 null이 될 것아냐?
		// 그럼 return할때 null이 되잖아-> 그럼 프로그램에 문제가 생길 수 있어
		// 그래서 optional로 너의 user객체를 감싸서 가져올게! 그럼 너가 null인지 아닌지 판단해서 return 해
		// get() -> 난 null이 return될리가 없어! -> 위험
		// orElseGet() -> null이면 너가 객체 하나 만들어서 넣어줘, 그럼 비어있지 않을테니까!
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				//TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		
		// 요청 : 웹브라우저(html만 이해할 수 있어)
		// user 객체 = 자바 오브젝트
		// 변환 (웹 브라우저가 이해할 수 있는 데이터로 변환해야돼) -> json (Gson같은 라이브러리를 써서 자바오브젝트를 json으로 변경해서 던져줫엇어)
		// 하지만 스프링부트의 MessageConverter가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user오브젝트를 json으로 변환해서 브라우저에게 던져줌
		return user;
	}
	
	
	// -------------------------------------------------- insert --------------------------------------------
	// http://localhost:8000/blog/dummy/join (요청)
	// http의 Body에 username, password, email데이터를 가지고 요청하게 되면 join함수에 적힌 변수의 명이 DB 컬럼 명과 같으면 쏙쏛 들어가
	//public String join(String username, String password, String email) { // key=value 형태를 받음 ( 약속된 규칙 ) -> x-www-form-urlencoded (spring이 함수의 파라미터로 파싱해서 집어넣음)
	@PostMapping("/dummy/join")
	public String join(User user) { // object로 받아서 처리가능
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		
		user.setRole(RoleType.USER);
		userRepository.save(user); // 회원가입 완료
		return "회원가입이 완료되었습니다.";
	}
}
