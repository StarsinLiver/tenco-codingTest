package com.tenco.blog.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tenco.blog.board.dto.BoardDto;
import com.tenco.blog.board.dto.PageRes;
import com.tenco.blog.board.entity.Board;
import com.tenco.blog.board.repository.BoardRepository;
import com.tenco.blog.handler.exception.CustomPageException;
import com.tenco.blog.handler.exception.CustomRestfulException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {

	@Autowired
	BoardRepository boardRepository;

	/**
	 * 게시물 만들기
	 * @param boardDto
	 * @return void
	 */
	public void save(BoardDto boardDto) {
		Board board = Board.builder().author(boardDto.getAuthor()).title(boardDto.getTitle())
				.content(boardDto.getContent()).build();

		Board result = boardRepository.save(board);

		if (result == null) {
			throw new CustomRestfulException("서버 에러로 정상 처리되지 않았습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 페이징 처리가 된 모든 게시물 보기
	 * @param pageable
	 * @return PageRes<Board>
	 */
	public PageRes<Board> findAll(Pageable pageable) {
		// 1. DB 조회
		Page<Board> page = boardRepository.findAllPage(pageable);

		// 2. DB 조회를 실패하였을 경우 
		if (page == null || page.getContent().isEmpty()) {
			throw new CustomPageException("컨텐츠가 없습니다. 컨텐츠를 작성해 주세요", HttpStatus.BAD_REQUEST);
		}
		if (page == null || page.isEmpty()) {
			throw new CustomRestfulException("서버 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// VIEW(뷰) 에서 페이징 처리를 할 PageRes 객체 생성
		PageRes<Board> pageRes = new PageRes<>(page.getContent(), page.getNumber(), page.getTotalElements(),
				page.getSize());
	
		return pageRes;
	}

	/**
	 * 상세 조회
	 * @param id
	 * @return Optional<Board>
	 */
	public Optional<Board> findById(int id) {
		// 1. DB 조회
		Optional<Board> board = boardRepository.findById(id);
		// 2. 검사
		if(board.isEmpty()) {
			throw new CustomRestfulException("게시판이 삭제되었거나 없습니다.", HttpStatus.BAD_REQUEST);
		}
		return board;
	}

	/**
	 * 업데이트 서비스
	 * @param id
	 * @param boardDto
	 * @return void
	 */
	public void update(int id, BoardDto boardDto) {
		if (boardRepository.existsById(id)) {
			Board board = Board.builder().id(id).author(boardDto.getAuthor()).title(boardDto.getTitle())
					.content(boardDto.getContent()).build();

			boardRepository.save(board);
		} else {
			throw new CustomRestfulException("게시물이 삭제되었거나 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 삭제 서비스
	 * @param id
	 * @return void
	 */
	public void delete(int id) {
		if (boardRepository.existsById(id)) {
			boardRepository.deleteById(id);
		} else {
			throw new CustomRestfulException("게시물이 삭제되었거나 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
