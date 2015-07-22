package com.heymom.backend.entity.customized;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.heymom.backend.entity.BaseEntity;

@Entity
@Table(name = "question")
public class Question extends BaseEntity {
	private String decription;
	private Integer id;
	private List<Option> options;
	private Integer type;
	private Integer gender;
	private Integer age;

	@Column(name = "age", precision = 2, scale = 0)
	public Integer getAge() {
		return age;
	}
	
	@Column(name = "gender", precision = 2, scale = 0)
	public Integer getGender() {
		return gender;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	@Where(clause = "status>=0")
	public List<Option> getOptions() {
		return options;
	}

	@Column(name = "type", precision = 2, scale = 0)
	public Integer getType() {
		return type;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
