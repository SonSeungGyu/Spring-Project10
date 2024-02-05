package com.example.demo.etc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTest {
	
	@Autowired
	PasswordEncoder pe;
	
	@Test
	public void 암호화테스트() {
		String password = "1234";
		String enPw = pe.encode(password);
		System.out.println("enPw" + enPw);
		
		//비밀번호가 1234가 맞는지 확인 (문자열과 해시코드 비교)
		boolean matchResult = pe.matches("123", enPw);
		System.out.println("확인결과:" + matchResult);
	}
}
