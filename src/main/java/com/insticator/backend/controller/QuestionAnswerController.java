package com.insticator.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insticator.backend.dto.AnswerExtensionDTO;
import com.insticator.backend.model.AnswerExtension;
import com.insticator.backend.repository.AnswerExtensionRepository;
import com.insticator.backend.repository.QuestionAnswerRepository;

import java.util.List;

@RestController
@RequestMapping("/answers")
public class QuestionAnswerController {

	@Autowired
	QuestionAnswerRepository qaRepository;

	@Autowired
	AnswerExtensionRepository aeRepository;

	@PostMapping("/{id}/extensions")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<AnswerExtensionDTO> createAnswerExtension(@PathVariable(value = "id") Long answerId,
																    @RequestBody AnswerExtensionDTO newAeDto) {
		return qaRepository
				.findById(answerId)
				.map(answer -> {
					final AnswerExtension newAe = AnswerExtensionDTO.transform(newAeDto, answer);
					return new ResponseEntity<>(AnswerExtensionDTO.build(aeRepository.save(newAe)), HttpStatus.CREATED);
				})
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/{id}/extensions")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<List<AnswerExtensionDTO>> getAnswerExtensions(@PathVariable(value = "id") Long answerId) {
		return qaRepository
				.findById(answerId)
				.map(answer -> ResponseEntity.ok(AnswerExtensionDTO.build(answer.getExtensions())))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}