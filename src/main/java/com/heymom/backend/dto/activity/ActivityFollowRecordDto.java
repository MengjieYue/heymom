package com.heymom.backend.dto.activity;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.dto.user.UserDto;
import com.heymom.backend.entity.activity.ActivityFollowRecord;

public class ActivityFollowRecordDto {
	private ActivityDto activity;
	private Long id;
	private UserDto user;

	public ActivityFollowRecordDto(ActivityFollowRecord entity) {
		BeanUtils.copyProperties(entity, this);
		if (entity.getUser() != null) {
			user = new UserDto(entity.getUser());
		}
		if (entity.getActivity() != null) {
			activity = new ActivityDto(entity.getActivity());
		}
	}

	public ActivityDto getActivity() {
		return activity;
	}

	public Long getId() {
		return id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setActivity(ActivityDto activity) {
		this.activity = activity;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public ActivityFollowRecord toEntity() {
		ActivityFollowRecord entity = new ActivityFollowRecord();
		BeanUtils.copyProperties(this, entity);
		if (user != null) {
			entity.setUser(user.toEntity());
		}
		if (activity != null) {
			entity.setActivity(activity.toEntity());
		}
		return entity;
	}
}
