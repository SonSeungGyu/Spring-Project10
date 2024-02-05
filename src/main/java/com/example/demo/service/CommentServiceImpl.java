package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentRepository repo;
	
	@Override
	public int register(CommentDTO dto) {
		Comment entity = dtoToEntity(dto);
		repo.save(entity);
		
		return entity.getCommentNo();
	}

	@Override
	public List<CommentDTO> getListByBoardNo(int boardNo) {
		//게시물 객체 생성
		Board board = Board.builder()
							.no(boardNo)
							.build();
		//게시물 번호를 기준으로 댓글 목록 조회
		List<Comment> entityList = repo.findByBoard(board);
		
		List<CommentDTO> dtoList = new ArrayList<>();
		
		//엔티티 리스트를 dto 리스트로 변환
		for(Comment entity : entityList) {
			CommentDTO dto = entityToDto(entity);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public boolean remove(int no) {
		
		//해당 댓글이 있는지 확인
		Optional<Comment> comment = repo.findById(no);
		
		//없다면 false 반환
		if(comment.isEmpty()) {
			return false;
		}
		//있다면 댓글 삭제 후 true 반환
		repo.deleteById(no);
		return true;
	}
	
	
	
}
