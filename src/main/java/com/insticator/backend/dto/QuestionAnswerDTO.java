package com.insticator.backend.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.insticator.backend.model.Question;
import com.insticator.backend.model.QuestionAnswer;

public class QuestionAnswerDTO {

	private Long answerId;

	private Long questionId;

	private String answer;

	private boolean isCorrectAnswer;

	private Date createdAt;

	private Date updatedAt;

	public static QuestionAnswer transform(final QuestionAnswerDTO newQADto, final Question question) {
		final QuestionAnswer newQa = new QuestionAnswer();
		newQa.setAnswer(newQADto.getAnswer());
		newQa.setIsCorrectAnswer(newQADto.getIsCorrectAnswer());
		newQa.setQuestion(question);

		return newQa;
	}

	public static QuestionAnswerDTO build(final QuestionAnswer save) {
		final QuestionAnswerDTO newQaDto = new QuestionAnswerDTO();

		newQaDto.setAnswerId(save.getAnswerId());
		newQaDto.setAnswer(save.getAnswer());
		newQaDto.setIsCorrectAnswer(save.isCorrectAnswer());
		newQaDto.setCreatedAt(save.getCreatedAt());
		newQaDto.setUpdatedAt(save.getUpdatedAt());
		newQaDto.setQuestionId(save.getQuestion().getQuestionId());

		return newQaDto;
	}

	public static List<QuestionAnswerDTO> build(final List<QuestionAnswer> answers) {
		final List<QuestionAnswerDTO> ret = new ArrayList<>();
		for (QuestionAnswer qa : answers) {
			ret.add(build(qa));
		}
		return ret;
	}

	public static QuestionAnswer createQuestionAnswer(final QuestionAnswerDTO incomingQuestionAnswer, final Question question) {
		final QuestionAnswer newQA = new QuestionAnswer();
		newQA.setQuestion(question);
		newQA.setAnswer(incomingQuestionAnswer.getAnswer());
		newQA.setIsCorrectAnswer(incomingQuestionAnswer.isCorrectAnswer);

		return newQA;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(final Long answerId) {
		this.answerId = answerId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(final Long questionId) {
		this.questionId = questionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(final String answer) {
		this.answer = answer;
	}

	public boolean getIsCorrectAnswer() {
		return isCorrectAnswer;
	}

	public void setIsCorrectAnswer(final boolean correctAnswer) {
		isCorrectAnswer = correctAnswer;
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
