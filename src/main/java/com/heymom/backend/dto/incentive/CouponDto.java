package com.heymom.backend.dto.incentive;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.dto.activity.ActivityDto;
import com.heymom.backend.dto.activity.ActivityProviderDto;
import com.heymom.backend.entity.activity.ActivityProvider;
import com.heymom.backend.entity.incentive.Coupon;

public class CouponDto {
	private List<ActivityDto> activities;
	private Date beginDate;
	private String description;
	private Date expireDate;
	private Integer id;
	private String imageUrl;
	private Integer maxCount;
	private Integer type;
	private String name;
	private ActivityProviderDto provider;

	public CouponDto() {
		super();
	}

	public CouponDto(Coupon entity) {
		BeanUtils.copyProperties(entity, this);
		if (entity.getProvider() != null) {
			provider = new ActivityProviderDto(entity.getProvider());
		}
	}

	public List<ActivityDto> getActivities() {
		return activities;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public String getDescription() {
		return description;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public Integer getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public String getName() {
		return name;
	}

	public ActivityProviderDto getProvider() {
		return provider;
	}

	public void setActivities(List<ActivityDto> activities) {
		this.activities = activities;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProvider(ActivityProviderDto provider) {
		this.provider = provider;
	}

	public Coupon toEntity() {
		Coupon entity = new Coupon();
		BeanUtils.copyProperties(this, entity);
		if (provider != null) {
			ActivityProvider providerEntity = provider.toEntity();
			entity.setProvider(providerEntity);
		}
		return entity;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
