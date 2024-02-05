package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dto.BoardDTO;
import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService service;
	


	
	//목록화면
	@GetMapping("/list")
	public void list(@RequestParam(name="page", defaultValue="0")int page, Model model){
		//게시물 목록 조회
		Page<BoardDTO> list = service.getList(page);
		//화면에 결과 데이터 전달
		model.addAttribute("list",list);//모델은 컨트롤러에서 뷰로 데이터를 전달해주는 새와 같음
		
		System.out.println("전체 페이지 수: " + list.getTotalPages());
		System.out.println("전체 게시물 수: " + list.getTotalElements());
		System.out.println("현재 페이지 번호: " + (list.getNumber()+1));
		System.out.println("페이지에 표시할 게시물 수: " + list.getNumberOfElements());
	}
	
	//등록화면
	@GetMapping("/register")
	public void register() {
		
	}
	
	//등록처리
	@PostMapping("/register")
	public String registerPost(BoardDTO dto, RedirectAttributes redirectAttributes) {
		
		//게시물 등록하고 새로운 게시물 번호 반환
		int no = service.register(dto);
		
		//목록화면에 새로운 게시물 번호 전달
		redirectAttributes.addFlashAttribute("msg",no);//addFlash 는 화면에 데이터를 전달 
		
		//목록화면으로 이동. html경로아님. url주소를 작성할것
		return "redirect:/board/list";
	}
	
	//상세화면
	@GetMapping("/read")
	public void read(@RequestParam(name = "no")int no, Model model) {
		BoardDTO dto = service.read(no);
		model.addAttribute("dto",dto);
	}
	
	
	@GetMapping("/modify")
	public void modify(@RequestParam(name = "no")int no, Model model) {//model 객체 -> 컨트롤러에서 화면단으로 데이터를 전달하는 역할
		BoardDTO dto = service.read(no);
		model.addAttribute("dto", dto);
	}
	
	@PostMapping("/modify")
	public String modifyPost(BoardDTO dto, RedirectAttributes redirectAttributes) {
		//리다이렉트 객체는 model 객체와 같이 전달자와 같은 역할
		//차이점은 리다이렉트는 다른 페이지로 이동할 때 이용한다.
		
		//게시물 수정
		service.modify(dto);
		
		//리다이렉트 주소에 파라미터 추가(?no=1)
		redirectAttributes.addAttribute("no",dto.getNo()); 
		//addAttribute는 파라미터 추가 함수 이 함수를 이용하여  
		// board/read?no=dto.getNo()와 같이 파라미터를 추가해줌.
		//링크에 파라미터를 추가해준다고 생각하면 됨.
		
		//상세화면으로 이동
		return "redirect:/board/read";
	}
	
	@PostMapping("/remove")
	public String removePost(@RequestParam(name = "no")int no) {
		service.remove(no);
		return "redirect:/board/list";
	}
	
}
