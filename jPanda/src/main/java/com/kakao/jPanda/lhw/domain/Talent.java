package com.kakao.jPanda.lhw.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Talent {
	// 판매 게시판
	private Long talentNo; 
	private String sellerId;
	private Long upperCategoryNo;
	private Long lowerCategoryNo;
	private String mainImg;
	private String title;
	private String content;
	private Long bamboo;
	private Long saleBamboo;
	private String summary;
	private String status;
	private Long viewCount;
	private Date regDate;
	private Date statusDate;
	
	// 리뷰 테이블용 
	private Double bambooScore;
	
	private int reviewCount;
}