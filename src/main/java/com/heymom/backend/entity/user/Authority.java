package com.heymom.backend.entity.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.heymom.backend.entity.BaseEntity;

@Entity
@Table(name = "authority")
public class Authority extends BaseEntity {
	private String description;
	private Integer id;
	private String name;

	@Column(name = "description", length = 100)
	public String getDescription() {
		return description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 4, scale = 0)
	public Integer getId() {
		return id;
	}

	@Column(name = "name", unique = true, length = 50)
	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
