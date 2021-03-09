package com.hazel.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@Entity
public class Reply {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false, length=200)
	private String content; 
	
	@ManyToOne // 여러개의 답변은 하나의 게시글에 달 수 있어
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne // 여러개의 답변을 하나의 유저가 쓸 수 있어
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;

	@Override
	public String toString() {
		return "Reply [id=" + id + ", content=" + content + ", board=" + board + ", user=" + user + ", createDate="
				+ createDate + "]";
	}
	
}
