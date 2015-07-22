package com.heymom.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "delivery")
public class Delivery extends BaseEntity {
	private String content;
	private Integer hotPriority = 0;
	private Integer id;
	private String name;

	@Column(name = "content", length = 2000)
	public String getContent() {
		return content;
	}

	@Column(name = "hot_priority", precision = 5, scale = 0)
	public Integer getHotPriority() {
		return hotPriority;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	@Column(name = "name", length = 150)
	public String getName() {
		return name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setHotPriority(Integer hotPriority) {
		this.hotPriority = hotPriority;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
