package com.insticator.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "question_answer")
@EntityListeners(AuditingEntityListener.class)
public class QuestionAnswer implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "answer_id")
	private Long answerId;

	@JsonBackReference
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id", referencedColumnName = "question_id")
	private Question question;

	@NotBlank
	@Length(min = 0, max = 250)
	private String answer;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isCorrectAnswer;

	@JsonManagedReference
	@OneToMany(mappedBy = "baseAnswer", fetch = FetchType.EAGER)
	private List<AnswerExtension> extensions = new ArrayList<>();

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public QuestionAnswer() {
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(final Question question) {
		this.question = question;
	}

	public Long getAnswerId() {
		return answerId;
	}

	public void setAnswerId(final Long answerId) {
		this.answerId = answerId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(final String Answer) {
		this.answer = Answer;
	}

	public boolean isCorrectAnswer() {
		return isCorrectAnswer;
	}

	public void setIsCorrectAnswer(boolean isCorrectAnswer) {
		this.isCorrectAnswer = isCorrectAnswer;
	}

	public List<AnswerExtension> getExtensions() {
		return extensions;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public Date getCreatedAt() {
		return createdAt;
	}

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final QuestionAnswer that = (QuestionAnswer) o;
		return isCorrectAnswer == that.isCorrectAnswer &&
				Objects.equals(answerId, that.answerId) &&
				Objects.equals(question, that.question) &&
				Objects.equals(answer, that.answer) &&
				Objects.equals(extensions, that.extensions) &&
				Objects.equals(createdAt, that.createdAt) &&
				Objects.equals(updatedAt, that.updatedAt);
	}

	@Override
	public int hashCode() {
		return Objects.hash(answerId, question, answer, isCorrectAnswer, extensions, createdAt, updatedAt);
	}
}
