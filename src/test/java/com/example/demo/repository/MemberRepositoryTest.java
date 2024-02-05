package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Member;

@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	MemberRepository repo;
	
	@Autowired
	BoardRepository boardRepository;
	
	@Test
	public void 회원등록() {
		Member member = Member.builder()
						.id("user1")
						.password("1234")
						.name("둘리")
						.build();
		repo.save(member);
	}
	
	@Test
	public void 회원일괄등록() {
		List<Member> list = new ArrayList<>();
		for(int i=0; i<30;i++) {
			list.add(new Member("user"+i,"1234","둘리"));
		}
		repo.saveAll(list);
	}
	
	@Test
	public void 모든회원삭제() {
		repo.deleteAll();
	}
	//게시물이 없는 회원은 삭제해도 문제가 없지만
	//게시물이 있는 경우에는 삭제할 수 없음
	
	//데이터생성: 부모->자식
	//데이터삭제: 자식->부모
	
	@Test
	public void 게시물을작성한회원삭제() {
		Member member = Member
							.builder()
							.id("user1")
							.build();
		boardRepository.deleteWriter(member);
		repo.deleteById("user1");
	}
	
}
