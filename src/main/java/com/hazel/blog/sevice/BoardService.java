package com.hazel.blog.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazel.blog.dto.ReplySaveRequestDto;
import com.hazel.blog.model.Board;
import com.hazel.blog.model.Reply;
import com.hazel.blog.model.RoleType;
import com.hazel.blog.model.User;
import com.hazel.blog.repository.BoardRepository;
import com.hazel.blog.repository.ReplyRepository;
import com.hazel.blog.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;

	@Transactional
	public void 글쓰기(Board board, User user) { // title, content
		board.setCount(0); // 조회수
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly=true)
	public Page<Board> 글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly=true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board=boardRepository.findById(id) 
				.orElseThrow(() -> {
					return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시(Service가 종료될때) 트랜잭션이 종료됩니다. 이때 더티체킹이 일어나면서 자동 flush (commit)
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
}

// 전통적인 로그인 방식(security 없음)
//@Transactional(readOnly = true)// select할때 트랜잭션 시작, 서비스 종료시 트랜잭선 종료(정합성 유지 가능)
//public User 로그인(User user) {
//	return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//}