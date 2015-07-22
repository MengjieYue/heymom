package com.heymom.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.heymom.backend.entity.customized.Question;

public interface QuestionDao extends BaseDao<Question, Integer> {
	@Query("from Question a where a.status>=0 order by type")
	public List<Question> findAvaliableQuestions();
}
