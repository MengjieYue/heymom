package com.heymom.backend.dto.customized;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.customized.Option;
import com.heymom.backend.entity.customized.Question;

public class QuestionDto extends BaseEntity {
	private String decription;
	private Integer id;
	private List<OptionDto> options;
	private Integer type;
	private Integer gender;
	private Integer age;
	
	public QuestionDto(){
		super();
	}
	
	public QuestionDto(Question entity){ 
		BeanUtils.copyProperties(entity, this);
		if (entity.getOptions() != null) {
			options = new ArrayList<OptionDto>();
			for (Option option : entity.getOptions())
				options.add(new OptionDto(option));
		}
	}

	public String getDecription() {
		return decription;
	}

	public Integer getId() {
		return id;
	}

	public List<OptionDto> getOptions() {
		return options;
	}

	public Integer getType() {
		return type;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOptions(List<OptionDto> options) {
		this.options = options;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Question toEntity(){
		Question entity = new Question();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

}
