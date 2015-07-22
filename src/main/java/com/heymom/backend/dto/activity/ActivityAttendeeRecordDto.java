package com.heymom.backend.dto.activity;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.dto.user.UserDto;
import com.heymom.backend.entity.activity.ActivityAttendeeRecord;
import com.heymom.backend.entity.user.Kid;

public class ActivityAttendeeRecordDto {
	private ActivityDto activity;
	private String comments;
	private Long id;
	private Kid kid;
	private Integer score;
	private UserDto user;

	public ActivityAttendeeRecordDto() {
	}

	public ActivityAttendeeRecordDto(ActivityAttendeeRecord entity) {
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

	public String getComments() {
		return comments;
	}

	public Long getId() {
		return id;
	}

	public Kid getKid() {
		return kid;
	}

	public Integer getScore() {
		return score;
	}

	public UserDto getUser() {
		return user;
	}

	public void setActivity(ActivityDto activity) {
		this.activity = activity;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKid(Kid kid) {
		this.kid = kid;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public ActivityAttendeeRecord toEntity() {
		ActivityAttendeeRecord entity = new ActivityAttendeeRecord();
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
