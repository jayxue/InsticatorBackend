package com.insticator.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.insticator.backend.model.AnswerExtension;

@Repository
public interface AnswerExtensionRepository extends JpaRepository<AnswerExtension, Long> {
	
	@Query(value = "SELECT ae.* FROM answer_extension ae WHERE ae.answer_id = ?1", nativeQuery = true)
	List<AnswerExtension> findAnswerExtensions(Long answerId);
}
