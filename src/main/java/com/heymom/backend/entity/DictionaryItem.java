package com.heymom.backend.entity;

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

@Entity
@Table(name = "dictionary_item")
public class DictionaryItem extends BaseEntity {
	private Integer id;
	private String name;
	private List<DictionaryValue> values;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	@Column(name = "name", length = 150)
	public String getName() {
		return name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
	@Where(clause = "status>=0")
	public List<DictionaryValue> getValues() {
		return values;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValues(List<DictionaryValue> values) {
		this.values = values;
	}
}
