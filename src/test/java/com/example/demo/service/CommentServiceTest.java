package com.example.demo.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dto.CommentDTO;

@SpringBootTest
public class CommentServiceTest {
	
	@Autowired
	CommentService service;
	
	@Test
	public void 등록() {
		//service.register(new CommentDTO(0,10,"내용","user1",null,null));
		CommentDTO dto = CommentDTO.builder() //빌더는 관계에 관여되거나 기본키만 포함해도 객체 생성 가능
									//.content("댓글")
									.boardNo(10)
									.writer("user1")
									.build();
		service.register(dto);
			
	}
	
	@Test
	public void 게시물별댓글목록() {
		List<CommentDTO> list = service.getListByBoardNo(10);
		for (CommentDTO commentDTO : list) {
			System.out.println(commentDTO);
		}
	}
	
	@Test
	public void 댓글삭제() {
		service.remove(1);
	}
	
}
