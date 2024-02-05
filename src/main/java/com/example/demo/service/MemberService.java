package com.example.demo.service;

import org.springframework.data.domain.Page;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;

public interface MemberService {
	//회원 목록 조회
	Page<MemberDTO> getList(int pageNumber);
	
	//회원등록
	boolean register(MemberDTO dto);
	
	//회원 단건 조회
	MemberDTO read(String id);
	
	

	
	
	default MemberDTO entityToDto(Member entity) {
		
		MemberDTO dto = MemberDTO.builder()
						.id(entity.getId())
						.password(entity.getPassword())
						.name(entity.getName())
						.regDate(entity.getRegDate())
						.modDate(entity.getModDate())
						.role(entity.getRole())//등급추가
						.build();
		
		return dto; 
	}
	
	default Member dtoToEntity(MemberDTO dto) {
		Member entity = Member.builder()
				.id(dto.getId())
				.password(dto.getPassword())
				.name(dto.getName())
				.role(dto.getRole())//등급추가
				.build();
			
		return entity;
	}
}
