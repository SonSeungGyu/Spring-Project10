package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.MemberDTO;
import com.example.demo.service.MemberService;

@Controller
//@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	MemberService service;
	
	@GetMapping("/member/list")
	public void list(@RequestParam(name = "page", defaultValue = "0")int page, Model model){
		Page<MemberDTO> list = service.getList(page);
		model.addAttribute("list", list);
		
//		System.out.println("전체 페이지 수: " + list.getTotalPages());
//		System.out.println("전체 게시물 수: " + list.getTotalElements());
//		System.out.println("현재 페이지 번호: " + (list.getNumber()+1));
//		System.out.println("페이지에 표시할 게시물 수: " + list.getNumberOfElements());
	}
	
	
	@GetMapping("/register")
	public String register() {
		return "member/register";
	}
	
	@PostMapping("/register")
	//RedirectAttributes는 컨트롤러에서 모델로 객체를 보내주는 역할(model 과 같은 목적)
	public String registerPost(MemberDTO dto, RedirectAttributes redirectAttributes) {
		boolean isSuccess = service.register(dto);
		if(isSuccess) {
			return "redirect:/"; //성공하면 목록화면으로 이동
		}else {
			redirectAttributes.addFlashAttribute("msg", "아이디가 중복되어 등록에 실패했습니다.");
			return "redirect:/register"; //실패하면 다시 회원가입폼으로 이동
		}
	}
	
	@GetMapping("/member/read")
	public void read(@RequestParam(name = "id")String id,@RequestParam(defaultValue="0", name="page")int page, Model model) {
		MemberDTO dto = service.read(id);
		model.addAttribute("dto",dto);
		model.addAttribute("page", page);
	}
	
	
	
	
	
}
