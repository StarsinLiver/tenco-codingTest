package com.tenco.blog.board.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tenco.blog.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

	@Query(value = "select * from board", countQuery = "SELECT COUNT(*) from board", nativeQuery = true)
	public Page<Board> findAllPage(Pageable page);
}
