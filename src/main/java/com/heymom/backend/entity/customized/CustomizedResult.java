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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.activity.Activity;
import com.heymom.backend.entity.user.User;

@Entity
@Table(name = "customized_result_record")
public class CustomizedResult extends BaseEntity {
	private String advice;
	private Long id;
	private List<Activity> recommendation;
	private String retult;
	private User user;
	private String values;

	@Column(name = "advice", length = 1000)
	public String getAdvice() {
		return advice;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 20, scale = 0)
	public Long getId() {
		return id;
	}

	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "customized_activity", joinColumns = { @JoinColumn(name = "customized_result_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "activity_id", referencedColumnName = "id") })
	public List<Activity> getRecommendation() {
		return recommendation;
	}

	@Column(name = "retult", length = 1000)
	public String getRetult() {
		return retult;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	@Column(name = "test_values", length = 200)
	public String getValues() {
		return values;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setRecommendation(List<Activity> recommendation) {
		this.recommendation = recommendation;
	}

	public void setRetult(String retult) {
		this.retult = retult;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setValues(String values) {
		this.values = values;
	}

}
