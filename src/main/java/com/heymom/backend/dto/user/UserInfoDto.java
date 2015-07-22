package com.heymom.backend.dto.user;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.entity.user.UserInfo;

public class UserInfoDto {
	private String address;

	private UserDto user;

	public UserInfoDto() {
	}

	public UserInfoDto(UserInfo entity) {
		if (entity.getUser() != null) {
			user = new UserDto(entity.getUser());
		}
		BeanUtils.copyProperties(entity, this);
	}

	public String getAddress() {
		return address;
	}

	public UserDto getUser() {
		return user;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public UserInfo toEntity() {
		UserInfo entity = new UserInfo();
		if (user != null) {
			entity.setUser(user.toEntity());
		}
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
