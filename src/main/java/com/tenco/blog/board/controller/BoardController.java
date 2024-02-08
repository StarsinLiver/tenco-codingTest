package com.tenco.blog.board.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tenco.blog.board.dto.BoardDto;
import com.tenco.blog.board.dto.PageRes;
import com.tenco.blog.board.entity.Board;
import com.tenco.blog.board.service.BoardService;
import com.tenco.blog.handler.exception.CustomRestfulException;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class BoardController {

	@Autowired
	BoardService boardService;

	/**
	 * 모든 게시물 찾아보기
	 * 
	 * @param model
	 * @param page
	 * @param size
	 */
	@GetMapping({ "/", "" })
	public String index(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "3") int size) {

		// 1. 페이징 처리가 될 Pageable 객체 생성
		Pageable pageable = PageRequest.of(page, size);
		// 2. 서비스 호출
		PageRes<Board> pageRes = boardService.findAll(pageable);

		model.addAttribute("boardList", pageRes.getContent());
		model.addAttribute("currentPage", pageRes.getNumber()); // 현재페이지 번호
		model.addAttribute("totalItems", pageRes.getTotalElements()); // 전체테이블 건수
		model.addAttribute("totalPages", pageRes.getTotalPages()); // 전체 페이지개수
		model.addAttribute("startPage", pageRes.getStartPage()); // 현재 시작 페이지 번호
		model.addAttribute("endPage", pageRes.getEndPage()); // 현재 끝 페이지번호
		return "index";
	}

	/**
	 * 게시물 작성 폼으로 이동
	 */
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}

	/**
	 * 게시물 업데이트 폼으로 이동
	 * 
	 * @param id
	 * @param model
	 */
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable(value = "id") int id, Model model) {

		// Model 에서 확인할 객체
		Optional<Board> optional = boardService.findById(id);
		model.addAttribute("board", optional.get());
		return "board/updateForm";
	}

	/**
	 * 게시물 저장
	 * 
	 * @param blogDto
	 * @return
	 */
	@PostMapping("/board/save")
	public String save(BoardDto blogDto) {
		// 1. 유효성 검사
		if (blogDto.getAuthor() == null || blogDto.getAuthor().isEmpty()) {
			throw new CustomRestfulException("작성자를 적어주세요", HttpStatus.BAD_REQUEST);
		}
		if (blogDto.getTitle() == null || blogDto.getTitle().isEmpty()) {
			throw new CustomRestfulException("제목을 적어주세요", HttpStatus.BAD_REQUEST);
		}
		if (blogDto.getContent() == null || blogDto.getContent().isEmpty()) {
			throw new CustomRestfulException("내용을 적어주세요", HttpStatus.BAD_REQUEST);
		}
		// 2. 서비스 호출
		boardService.save(blogDto);
		return "redirect:/";
	}

	/**
	 * 게시물 업데이트
	 * 
	 * @param id
	 * @param blogDto
	 * @return
	 */
	@PostMapping("/board/{id}/update")
	public String update(@PathVariable(value = "id") Integer id, BoardDto blogDto) {
		// 1. 유효성 검사
		if (id == null) {
			throw new CustomRestfulException("게시물 id 값이 없습니다.", HttpStatus.BAD_REQUEST);
		}
		if (blogDto.getAuthor() == null || blogDto.getAuthor().isEmpty()) {
			throw new CustomRestfulException("작성자를 적어주세요", HttpStatus.BAD_REQUEST);
		}
		if (blogDto.getTitle() == null || blogDto.getTitle().isEmpty()) {
			throw new CustomRestfulException("제목을 적어주세요", HttpStatus.BAD_REQUEST);
		}
		if (blogDto.getContent() == null || blogDto.getContent().isEmpty()) {
			throw new CustomRestfulException("내용을 적어주세요", HttpStatus.BAD_REQUEST);
		}
		// 2. 서비스 호출
		boardService.update(id, blogDto);
		return "redirect:/";
	}

	@GetMapping("/board/{id}/delete")
	public String delete(@PathVariable(value = "id") Integer id) {
		// 1. 유효성 검사
		if (id == null) {
			throw new CustomRestfulException("게시물 id 값이 없습니다.", HttpStatus.BAD_REQUEST);
		}
		// 2. 서비스 호출
		boardService.delete(id);
		return "redirect:/";
	}
}
