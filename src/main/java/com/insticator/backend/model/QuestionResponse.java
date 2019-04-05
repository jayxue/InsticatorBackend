package com.insticator.backend.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "question_response")
@EntityListeners(AuditingEntityListener.class)
public class QuestionResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "response_id")
	private Long responseId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;

	/**
	 * For a non-matrix question, a user picks an answer for the question.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
	private QuestionAnswer answer;
	
	/**
	 * For a matrix question, a user picks an extension for an answer of the question.
	 */
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "extension_id", referencedColumnName = "extension_id")
	private AnswerExtension extension;
	
	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	public Long getResponseId() {
		return responseId;
	}

	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public QuestionAnswer getAnswer() {
		return answer;
	}

	public void setAnswer(QuestionAnswer answer) {
		this.answer = answer;
	}

	public AnswerExtension getExtension() {
		return extension;
	}

	public void setExtension(AnswerExtension extension) {
		this.extension = extension;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((responseId == null) ? 0 : responseId.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionResponse other = (QuestionResponse) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (createdAt == null) {
			if (other.createdAt != null)
				return false;
		} else if (!createdAt.equals(other.createdAt))
			return false;
		if (extension == null) {
			if (other.extension != null)
				return false;
		} else if (!extension.equals(other.extension))
			return false;
		if (responseId == null) {
			if (other.responseId != null)
				return false;
		} else if (!responseId.equals(other.responseId))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

}
