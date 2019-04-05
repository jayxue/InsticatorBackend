package com.insticator.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insticator.backend.model.QuestionAnswer;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, Long> {
	
	@Query(value = "SELECT qa.* FROM question_answer qa WHERE qa.question_id = ?1", nativeQuery = true)
	List<QuestionAnswer> findQuestionAnswers(Long questionId);
}
