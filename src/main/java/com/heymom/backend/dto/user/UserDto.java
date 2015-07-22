package com.heymom.backend.dto.user;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.entity.user.User;

public class UserDto {
	private String email;
	private Integer gender;
	private Long id;
	private String mobile;
	private String name;
	private String password;

	public UserDto() {
	}

	public UserDto(User entity) {
		BeanUtils.copyProperties(entity, this);
	}

	public String getEmail() {
		return email;
	}

	public Integer getGender() {
		return gender;
	}

	public Long getId() {
		return id;
	}

	public String getMobile() {
		return mobile;
	}

	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User toEntity() {
		User entity = new User();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}

}
