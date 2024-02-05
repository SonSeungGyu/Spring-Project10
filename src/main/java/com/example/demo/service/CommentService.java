package com.example.demo.service;

import java.util.List;

import com.example.demo.dto.CommentDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Member;

public interface CommentService {
	
	//댓글 등록 메소드
	int register(CommentDTO dto);
	
	//게시물을 기준으로 댓글 목록을 조회하는 메소드
	List<CommentDTO> getListByBoardNo(int boardNo);
	
	//댓글 삭제 메소드
	boolean remove(int no);
	
	
	
	default Comment dtoToEntity(CommentDTO dto) {
		Member member = Member.builder()
								.id(dto.getWriter())
								.build();
		
		Board board = Board.builder()
							.no(dto.getBoardNo())
							.build();
		
		Comment entity = Comment.builder()
								.commentNo(dto.getCommentNo())
								.board(board)
								.content(dto.getContent())
								.writer(member)
								.build();
		return entity;
		
	}
	
	default CommentDTO entityToDto(Comment entity) {
		CommentDTO dto = CommentDTO.builder()
									.commentNo(entity.getCommentNo())
									.boardNo(entity.getBoard().getNo())
									.content(entity.getContent())
									.writer(entity.getWriter().getId())
									.regDate(entity.getRegDate())
									.modDate(entity.getModDate())
									.build();
		
		return dto;
	}
}
