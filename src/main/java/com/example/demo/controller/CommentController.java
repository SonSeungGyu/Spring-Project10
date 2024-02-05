package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dto.CommentDTO;
import com.example.demo.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	CommentService service;
	
	//게시물별 댓글 목록 조회
	@ResponseBody//데이터를 보낼 때 json 타입으로 보낼 수 있도록 해주는 어노테이션
	@GetMapping("/list")
	public List<CommentDTO> list(@RequestParam(name = "boardNo")int boardNo){
		//게시물 기준으로 댓글 목록 조회
		List<CommentDTO> commentList = service.getListByBoardNo(boardNo);
		//댓글 목록 변환
		return commentList;
	}
	
	
	@ResponseBody
	@PostMapping("/register")
	public HashMap<String,Boolean> register(CommentDTO dto){
		//맵 객체 생성
		HashMap<String,Boolean> map = new HashMap<>();
		//임시 아이디
		String id = "user";
		dto.setWriter(id);
		//새로운 댓글 등록
		service.register(dto);
		//처리결과 반환
		map.put("success", true);
		return map;
	}
	
	
	@ResponseBody
	@GetMapping("/remove")
	public HashMap<String, Boolean> remove(@RequestParam(name = "commentNo")int commentNo){
		HashMap<String,Boolean> map = new HashMap<>();
		boolean result = service.remove(commentNo);
		map.put("success", result);
		
		return map;
	}
	
	
	
	
}
