package com.heymom.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "dictionary_value")
public class DictionaryValue extends BaseEntity {
	private Integer id;
	private DictionaryItem item;
	private String name;
	private String value;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "item_id")
	public DictionaryItem getItem() {
		return item;
	}

	@Column(name = "name", length = 150)
	public String getName() {
		return name;
	}

	@Column(name = "value", length = 1000)
	public String getValue() {
		return value;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setItem(DictionaryItem item) {
		this.item = item;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
