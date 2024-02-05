package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;

@Service//서비스 클래스로 지정
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardRepository repository;
	
	//게시물 등록
	@Override
	public int register(BoardDTO dto) {
		Board entity = dtoToEntity(dto);

		repository.save(entity);

		int newNo = entity.getNo();

		System.out.println(entity);

		return newNo;
	}

	//게시물 목록조회
	@Override
	public Page<BoardDTO> getList(int pageNum) {
		//페이지 번호를 인덱스로 변경
		int pageNumber = (pageNum == 0) ? 0 : pageNum -1;//컨트롤러에서 디폴트가 0이기 때문에 0이 넘어오면 0이 되도록
		//페이지번호, 개수, 정렬방식을 입력하여 페이지 정보 생성
		Pageable pageable = PageRequest.of(pageNumber, 10,Sort.by("no").descending());
		//게시물 목록 조회
		Page<Board> entityPage = repository.findAll(pageable);
		//스트림을 사용하여 엔티티 리스트를 DTO리스트로 변환
		//Page 는 Stream 을 상속받고 있음.
		Page<BoardDTO> dtoPage = entityPage.map(entity -> entityToDto(entity));
		return dtoPage;
	}
	
	//게시물 상세조회
	@Override
	public BoardDTO read(int no) {
		Optional<Board> result = repository.findById(no);
		if(result.isPresent()) {
			Board board = result.get();
			BoardDTO boardDTO = entityToDto(board);
			return boardDTO;
		}
		return null;
	}
	
	//게시물 수정
	@Override
	public void modify(BoardDTO dto) {
		//파라미터로 받은 dto는 DTO 타입이므로 프라이머리키인 no로 접근후
		//이 no는 레파지토리의 엔티티 no와 같음을 이용하여 엔티티 타입의 변수에 할당.
		Optional<Board> result = repository.findById(dto.getNo());
		
		if(result.isPresent()) {
			Board entity = result.get();
			
			//기존 엔티티에서 제목과 내용만 변경
			entity.setTitle(dto.getTitle());
			entity.setContent(dto.getContent());
			
			//다시 저장
			repository.save(entity);
		}
	}
	
	//게시물 삭제
	@Override
	public int remove(int no) {
		
		Optional<Board> result = repository.findById(no);
		if(result.isPresent()) {
			repository.deleteById(no);
			return 1;//성공
		}else {
			return 0;//실패
		}
	}



}
