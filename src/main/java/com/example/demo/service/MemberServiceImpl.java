package com.example.demo.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDTO;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;


@Service
public class MemberServiceImpl implements MemberService{

	@Autowired
	MemberRepository repo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public Page<MemberDTO> getList(int pageNumber) {
		
		int pageIndex = (pageNumber == 0) ? 0 : pageNumber - 1;
		Sort sort = Sort.by("regDate").descending();
		Pageable pageable = PageRequest.of(pageIndex, 10, sort);
		Page<Member> entityPage = repo.findAll(pageable);
		Page<MemberDTO> dtoPage = entityPage.map(entity -> entityToDto(entity));
		
		return dtoPage;
		
	}

	@Override
	public boolean register(MemberDTO dto) {
		String id = dto.getId();
		
		MemberDTO getDto = read(id);
		if(getDto != null) {
			System.out.println("사용중인 아이디이빈다.");
			return false;
		}
		Member entity = dtoToEntity(dto);
		
		String enPw = passwordEncoder.encode(entity.getPassword());
		entity.setPassword(enPw);
		
		repo.save(entity);
		return true;
	}

	@Override
	public MemberDTO read(String id) {

		Optional<Member> result = repo.findById(id);
		if(result.isPresent()) {
			Member member = result.get();
			return entityToDto(member);
		}else {
			return null;
		}
	}

	


}
