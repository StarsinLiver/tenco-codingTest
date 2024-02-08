package com.tenco.blog.board.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BoardDto {
	private String title;
	private String content;
	private String author;
}
