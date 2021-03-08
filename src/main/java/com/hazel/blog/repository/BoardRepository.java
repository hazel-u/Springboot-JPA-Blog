package com.hazel.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hazel.blog.model.Board;
import com.hazel.blog.model.User;

public interface BoardRepository extends JpaRepository<Board, Integer>{ 

}
