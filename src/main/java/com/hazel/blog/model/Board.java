package com.hazel.blog.model;


import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, length=100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨 -> 글자수 어마어마

	private int count; // 조회수
	
	@ManyToOne(fetch = FetchType.EAGER) // Board=many , user=one 한명의 유저는 여러개의 게시글을 쓸 수 있다. EAGER는 불러올때 상세내용까지 다 불러와, LAZY는 조인해서 불러오긴 하는데 상세내용은 필요할 때 써
	@JoinColumn(name="userId")
	private User user; // DB는 오브젝트를 저장할 수없어서 FK를 사용, 하지만 자바는 오브젝트를 저장할 수 있어. -> 충돌 -> JPA의 ORM사용!(FK가 된다.)

	//@JoinColumn(name="replyId") 이거는 필요없어 FK가 필요없어
	@OneToMany(mappedBy = "board", fetch=FetchType.EAGER) // mappedBy = 연관관계의 주인이 아니다.(난 FK가 아니에요) DB에 컬럼을 만들지 마세요.
	private List<Reply> reply; // 답변은 여러개가 될 수 있다.
	
	@CreationTimestamp
	private Timestamp createDate;
}
