package com.example.demo.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.dto.MemberDTO;

@SpringBootTest
public class MemberSerivceTest {
	
	@Autowired
	MemberService service;
	
	@Autowired
	PasswordEncoder pe;
	
	@Test
	public void 회원목록조회() {
		Page<MemberDTO> page = service.getList(1);
		List<MemberDTO> list= page.getContent();
		for (MemberDTO memberDTO : list) {
			System.out.println(memberDTO);
		}
	}
	
	@Test
	public void 회원등록() {
		MemberDTO dto = MemberDTO.builder()
						.id("user99")
						.password("1234")
						.name("또치")
						.build();
		
		boolean result = service.register(dto);
		if(result == true) {
			
			System.out.println("성공");
		}else {
			System.out.println("실패");
		}
		String password = dto.getPassword();
		String enPw = pe.encode(password);
		System.out.println("enPw " + enPw);
		boolean matchResult = pe.matches(password, enPw);
		System.out.println("결과" + matchResult);
		
		
			
	}
	
	@Test
	public void 회원상세조회() {
		MemberDTO dto = service.read("user");
		
		System.out.println(dto);
	}
}
