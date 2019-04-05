package com.insticator.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insticator.backend.model.QuestionResponse;

@Repository
public interface QuestionResponseRepository extends JpaRepository<QuestionResponse, Long> {

	@Query(value = "SELECT qr.* FROM question_response qr WHERE qr.answer_id = ?1", nativeQuery = true)
	List<QuestionResponse> findResponsesForAnswer(Long answerId);

	@Query(value = "SELECT qr.* FROM question_response qr WHERE qr.user_id = ?1 AND qr.answer_id=?2", nativeQuery = true)
	List<QuestionResponse> findResponsesForUserAndAnswer(Long userId, Long answerId);

}
