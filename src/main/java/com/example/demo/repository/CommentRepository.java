package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;

import jakarta.transaction.Transactional;

@Transactional //트랜적션 처리를 해주어야 반영이 됨(일반 조회는 필요 없음)
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	List<Comment> findByBoard(Board board);
	
	void deleteByBoard(Board board);
}
