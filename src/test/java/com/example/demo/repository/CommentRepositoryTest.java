package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Member;

@SpringBootTest
public class CommentRepositoryTest {

	@Autowired
	CommentRepository repo;
	

	
	@Test
	public void 댓긋등록() {
		Member member = new Member("user", "1234","eric");
		Board board = new Board(9,"제목", "내용",member);
		Comment comment = Comment.builder()
							.commentNo(0)
							.board(board)
							.content("text")
							.writer(member)
							.build();
		
		repo.save(comment);
		
		

	}
	
	@Test
	public void 댓글목록조회() {
		List<Comment> list = repo.findAll();
		for(Comment comment : list) {
			
			System.out.println(comment);
		}
	}
	
	@Test
	public void 댓글단건조회() {
		Optional<Comment> result = repo.findById(0);
		if(result.isPresent()) {
			Comment comment = result.get();
			System.out.println(comment);
		}
	}
	
	@Test
	public void 댓글삭제() {
		repo.deleteAll();
	}
	
	
	
	@Test
	public void 게시물별_댓글목록조회() {
		Board board = Board.builder().no(10).build();
		List<Comment> list = repo.findByBoard(board);
		for(Comment comment : list) {
			System.out.println(comment);
		}
	}
	
	@Test
	public void 게시물별_댓글일괄삭제() {
		Board board = Board.builder().no(10).build();
		repo.deleteByBoard(board);
	}
	
	
	
}
