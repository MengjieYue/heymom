package com.heymom.backend.dto.customized;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.customized.Option;

public class OptionDto extends BaseEntity {
	private String decription;
	private Integer id;
	private QuestionDto question;
	private Integer value;

	public OptionDto() {
		super();
	}

	public OptionDto(Option entity) {
		BeanUtils.copyProperties(entity, this);
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDecription() {
		return decription;
	}

	public Integer getId() {
		return id;
	}

	public QuestionDto getQuestion() {
		return question;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setQuestion(QuestionDto question) {
		this.question = question;
	}
	
	public Option toEntity(){
		Option entity = new Option();
		BeanUtils.copyProperties(entity, this);
		if(question != null){
			entity.setQuestion(question.toEntity());
		}
		return entity;
	}

}
