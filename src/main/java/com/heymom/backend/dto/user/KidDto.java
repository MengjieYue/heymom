package com.heymom.backend.dto.user;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.entity.user.Kid;

public class KidDto {
	private Date birthday;
	private Integer gender;
	private Long id;
	private String image;
	private String name;
	private String nickName;
	private UserDto parent;

	public KidDto() {
	}

	public KidDto(Kid entity) {
		BeanUtils.copyProperties(entity, this);
	}

	public Date getBirthday() {
		return birthday;
	}

	public Integer getGender() {
		return gender;
	}

	public Long getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public String getName() {
		return name;
	}

	public String getNickName() {
		return nickName;
	}

	public UserDto getParent() {
		return parent;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public void setParent(UserDto parent) {
		this.parent = parent;
	}

	public Kid toEntity() {
		Kid entity = new Kid();
		if (parent != null) {
			entity.setParent(parent.toEntity());
		}
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
