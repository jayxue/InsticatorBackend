package com.insticator.backend.dto;

import java.util.Date;

import com.insticator.backend.model.QuestionAnswer;
import com.insticator.backend.model.QuestionResponse;
import com.insticator.backend.model.User;

public class QuestionResponseDTO {

	private Long responseId;

	private Long userId;

	private Long answerId;

	private Long extensionId;

	private Date createdAt;

	public static QuestionResponseDTO build(final QuestionResponse save) {
		final QuestionResponseDTO newQrDto = new QuestionResponseDTO();
		newQrDto.setResponseId(save.getResponseId());
		newQrDto.setAnswerId(save.getAnswer().getAnswerId());
		newQrDto.setCreatedAt(save.getCreatedAt());
		if(save.getExtension() != null) {
			newQrDto.setExtensionId(save.getExtension().getExtensionId());
		}
		newQrDto.setUserId(save.getUser().getUserId());
		
		return newQrDto;
	}
	
	public static QuestionResponse createQuestionResponse(final User user, final QuestionAnswer answer) {
		final QuestionResponse newQr = new QuestionResponse();
		newQr.setUser(user);
		newQr.setAnswer(answer);

		return newQr;
	}

	public Long getResponseId() {
		return responseId;
	}

	public void setResponseId(final Long responseId) {
		this.responseId = responseId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Long answerId) {
		this.answerId = answerId;
	}

	public Long getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(Long extensionId) {
		this.extensionId = extensionId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

}
