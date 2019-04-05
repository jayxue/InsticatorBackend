package com.insticator.backend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insticator.backend.model.AnswerExtension;
import com.insticator.backend.model.QuestionAnswer;

public class AnswerExtensionDTO {

	private Long extensionId;

	private Long answerId;

	private String extension;

	private boolean isCorrectExtension;

	private Date createdAt;

	private Date updatedAt;

	public static AnswerExtension transform(final AnswerExtensionDTO newAeDto, final QuestionAnswer questionAnswer) {
		final AnswerExtension newAe = new AnswerExtension();
		newAe.setExtension(newAeDto.getExtension());
		newAe.setIsCorrectExtension(newAeDto.getIsCorrectExtension());
		newAe.setBaseAnswer(questionAnswer);

		return newAe;
	}

	public static AnswerExtensionDTO build(final AnswerExtension save) {
		final AnswerExtensionDTO newAeDto = new AnswerExtensionDTO();

		newAeDto.setExtensionId(save.getExtensionId());
		newAeDto.setExtension(save.getExtension());
		newAeDto.setIsCorrectExtension(save.isCorrectExtension());
		newAeDto.setCreatedAt(save.getCreatedAt());
		newAeDto.setUpdatedAt(save.getUpdatedAt());
		newAeDto.setAnswerId(save.getBaseAnswer().getAnswerId());

		return newAeDto;
	}

	public static List<AnswerExtensionDTO> build(final List<AnswerExtension> extensions) {
		final List<AnswerExtensionDTO> ret = new ArrayList<>();
		for (AnswerExtension ae : extensions) {
			ret.add(build(ae));
		}
		return ret;
	}

	public static AnswerExtension createAnswerExtension(final AnswerExtensionDTO incomingAnswerExtension, final QuestionAnswer answer) {
		final AnswerExtension newAe = new AnswerExtension();
		newAe.setBaseAnswer(answer);
		newAe.setExtension(incomingAnswerExtension.getExtension());
		newAe.setIsCorrectExtension(incomingAnswerExtension.isCorrectExtension);

		return newAe;
	}

	public Long getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(final Long extensionId) {
		this.extensionId = extensionId;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(final Long answerId) {
		this.answerId = answerId;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(final String extension) {
		this.extension = extension;
	}

	public boolean getIsCorrectExtension() {
		return isCorrectExtension;
	}

	public void setIsCorrectExtension(final boolean correctExtension) {
		isCorrectExtension = correctExtension;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
