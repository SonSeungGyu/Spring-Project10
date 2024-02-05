package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;

public interface BoardService {
	
	//게시물 등록
	int register(BoardDTO dto);
	
	//게시물 목록조회
	Page<BoardDTO> getList(int pageNum);
	
	//게시물 상세조회
	BoardDTO read(int no);
	
	//게시물 수정
	void modify(BoardDTO dto);
	
	//게시물 삭제
	int remove(int no);
	
	//dto를 엔티티로 변환하는 메소드
	//디폴트 접근제어자를 쓰면 인터페이스에서도 일반메소드를 이용할 수 있음.
	default Board dtoToEntity(BoardDTO dto) {
		
		//작성자 객체 생성
		Member member = Member
							.builder()
							.id(dto.getWriter())
							.build(); //builder 는 객체화 시켜준다는 것!
		
		Board entity = Board.builder()
						.no(dto.getNo())
						.title(dto.getTitle())
						.content(dto.getContent())
						.writer(member)
						.build();
		return entity;
	}
	
	//엔티티를 dto로 변환하는 메소드
	default BoardDTO entityToDto(Board entity) {
		
		
		
		BoardDTO dto = BoardDTO.builder()
						.no(entity.getNo())
						.title(entity.getTitle())
						.content(entity.getContent())
						.writer(entity.getWriter().getId()) // getWriter로 가져오는 값은 Member 타입의 객체이므로 다시 getId를 이용해 문자열을 반환시켜준다.
						.regDate(entity.getRegDate())
						.modDate(entity.getModDate())
						.build();
		return dto;
	}
}
