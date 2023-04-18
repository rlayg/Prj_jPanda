package com.kakao.jPanda.lhw.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.kakao.jPanda.lhw.domain.Talent;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TalentDaoImpl implements TalentDao {

	private final SqlSession sqlSession;
	
	@Override
	public List<Talent> selectTalentList() {
		return sqlSession.selectList("selectTalentList");
	}
	
	@Override
	public Talent selectTalentByTalentNo(Long talentNo) {
		return sqlSession.selectOne("selectTalentByTalentNo", talentNo);
	}

	@Override
	public List<Talent> selectTalentListByUpperCategoryNo(Long upperCategoryNo) {
		return sqlSession.selectList("selectTalentListByUpperCategoryNo", upperCategoryNo);
	}

	@Override
	public List<Talent> selectTalentListByLowerCategoryNo(Long lowerCategoryNo) {
		return sqlSession.selectList("selectTalentListByLowerCategoryNo", lowerCategoryNo);
	}

}
