package com.heymom.backend.dto.customized;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.dto.activity.ActivityDto;
import com.heymom.backend.dto.user.UserDto;
import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.activity.Activity;
import com.heymom.backend.entity.customized.CustomizedResult;

public class CustomizedResultDto extends BaseEntity {
	private Integer id;
	private List<Integer> values;
	private String retult;
	private String advice;
	private UserDto user;
	private List<ActivityDto> recommendation;

	public CustomizedResultDto() {
		super();
	}

	public CustomizedResultDto(CustomizedResult entity) {
		BeanUtils.copyProperties(entity, this);
		if (entity.getRecommendation() != null) {
			recommendation = new ArrayList<ActivityDto>();
			for (Activity activity : entity.getRecommendation())
				recommendation.add(new ActivityDto(activity));
		}
		if (entity.getValues() != null) {
			values = new ArrayList<Integer>();
			String[] tmpArray = entity.getValues().split(",");
			for (int i = 0; i < tmpArray.length; i++) {
				values.add(Integer.parseInt(tmpArray[i]));
			}
		}
	}

	public String getRetult() {
		return retult;
	}

	public void setRetult(String retult) {
		this.retult = retult;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public List<ActivityDto> getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(List<ActivityDto> recommendation) {
		this.recommendation = recommendation;
	}

	public List<Integer> getValues() {
		return values;
	}

	public void setValues(List<Integer> values) {
		this.values = values;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

}
