package com.heymom.backend.entity.customized;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.user.User;

@Entity
@Table(name = "question_option")
public class Option extends BaseEntity {
	private String decription;
	private Integer id;
	private Question question;
	private List<User> selectedUsers;
	private Integer value;
	
	@Column(name = "value", nullable = false, precision = 10, scale = 0)
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Column(name = "decription", length = 250, nullable = false)
	public String getDecription() {
		return decription;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "question_id")
	public Question getQuestion() {
		return question;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_option", joinColumns = { @JoinColumn(name = "option_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") })
	public List<User> getSelectedUsers() {
		return selectedUsers;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public void setSelectedUsers(List<User> selectedUsers) {
		this.selectedUsers = selectedUsers;
	}

}
