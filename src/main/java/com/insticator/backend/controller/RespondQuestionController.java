package com.insticator.backend.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insticator.backend.dto.QuestionResponseDTO;
import com.insticator.backend.model.AnswerExtension;
import com.insticator.backend.model.QuestionAnswer;
import com.insticator.backend.model.QuestionResponse;
import com.insticator.backend.model.User;
import com.insticator.backend.repository.AnswerExtensionRepository;
import com.insticator.backend.repository.QuestionAnswerRepository;
import com.insticator.backend.repository.QuestionResponseRepository;
import com.insticator.backend.repository.UserRepository;

@RestController
@RequestMapping("/respondquestion")
public class RespondQuestionController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	QuestionAnswerRepository qaRepository;

	@Autowired
	AnswerExtensionRepository aeRepository;

	@Autowired
	QuestionResponseRepository qrRepository;

	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<QuestionResponseDTO> saveResponse(
			@RequestParam(value = "user_uuid", required = true) String userUUID,
			@RequestParam(value = "answer_id", required = true) Long answerId,
			@RequestParam(value = "extension_id", required = false) Long extensionId) {
		if(userUUID == null || userUUID.isEmpty() || answerId == null) return ResponseEntity.badRequest().build();

		// Make sure a valid user can be found
		User user = null;
		try {
			user = userRepository.findByUuid(UUID.fromString(userUUID));
		} catch(IllegalArgumentException e) {
			return ResponseEntity.badRequest().build();
		}

		if(user == null) return ResponseEntity.notFound().build();

		QuestionAnswer answer = qaRepository.findById(answerId).orElse(null);

		if(answer == null) return ResponseEntity.notFound().build();

		QuestionResponse newQr = QuestionResponseDTO.createQuestionResponse(user, answer);

		if(extensionId == null && answer.getExtensions().size() > 0) {
			// An extension must be selected but no valid extension id is provided
			return ResponseEntity.badRequest().build();
		}

		if(extensionId != null) {
			AnswerExtension extension = aeRepository.findById(extensionId).orElse(null);
			if(extension == null) return ResponseEntity.notFound().build();
			newQr.setExtension(extension);
		}

		return new ResponseEntity<>(QuestionResponseDTO.build(qrRepository.save(newQr)), HttpStatus.CREATED);
	}
}
