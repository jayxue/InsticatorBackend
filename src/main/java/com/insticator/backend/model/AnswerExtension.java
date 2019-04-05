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
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "answer_extension")
@EntityListeners(AuditingEntityListener.class)
public class AnswerExtension implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "extension_id")
	private Long extensionId;

	@JsonBackReference
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "answer_id", referencedColumnName = "answer_id")
	private QuestionAnswer baseAnswer;

	@NotBlank
	@Length(min = 0, max = 250)
	private String extension;

	@Column(nullable = false, columnDefinition = "TINYINT(1)")
	private boolean isCorrectExtension;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public AnswerExtension() {
	}

	public QuestionAnswer getBaseAnswer() {
		return baseAnswer;
	}

	public void setBaseAnswer(final QuestionAnswer baseAnswer) {
		this.baseAnswer = baseAnswer;
	}

	public Long getExtensionId() {
		return extensionId;
	}

	public void setExtensionId(final Long extensionId) {
		this.extensionId = extensionId;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(final String extension) {
		this.extension = extension;
	}

	public boolean isCorrectExtension() {
		return isCorrectExtension;
	}

	public void setIsCorrectExtension(boolean isCorrectExtension) {
		this.isCorrectExtension = isCorrectExtension;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baseAnswer == null) ? 0 : baseAnswer.hashCode());
		result = prime * result + ((createdAt == null) ? 0 : createdAt.hashCode());
		result = prime * result + ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((extensionId == null) ? 0 : extensionId.hashCode());
		result = prime * result + (isCorrectExtension ? 1231 : 1237);
		result = prime * result + ((updatedAt == null) ? 0 : updatedAt.hashCode());
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
		AnswerExtension other = (AnswerExtension) obj;
		if (baseAnswer == null) {
			if (other.baseAnswer != null)
				return false;
		} else if (!baseAnswer.equals(other.baseAnswer))
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
		if (extensionId == null) {
			if (other.extensionId != null)
				return false;
		} else if (!extensionId.equals(other.extensionId))
			return false;
		if (isCorrectExtension != other.isCorrectExtension)
			return false;
		if (updatedAt == null) {
			if (other.updatedAt != null)
				return false;
		} else if (!updatedAt.equals(other.updatedAt))
			return false;
		return true;
	}

}
