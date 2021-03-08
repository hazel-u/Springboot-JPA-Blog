package com.hazel.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.hazel.blog.config.auth.PrincipalDetail;
import com.hazel.blog.sevice.BoardService;

@Controller // 일반 컨트롤러 어노테이션은 리턴할때 viewResolver가 작동 , 일반 controller는 html 페이지를 만들어서 사용자에게 응답해주는 controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	// 컨트롤러에서 세션을 어떻게 찾는지?
	@GetMapping({"","/"})
	public String index(Model model, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("boards",boardService.글목록(pageable)); // index 라는 페이지로 boards가 날라가
		// /WEB-INF/views/index.jsp
		return "index"; // viewResolver 작동 -> index페이지로 model의 정보를 들고 이동해
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board",boardService.글상세보기(id));
		return "board/detail";		
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	// USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
