package com.tenco.blog.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//create table blog(
//		 id int auto_increment primary key,
//		 title varchar(400) ,
//		 content varchar(1000),
//		 author varchar(400)
//		);
@Entity
@Table(name = "Board") // 테이블 명 지정
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String title;
	private String content;
	private String author;
}
