package com.heymom.backend.dto.activity;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.entity.activity.ActivityProvider;

public class ActivityProviderDto {
	private LocationDto city;
	private LocationDto country;
	private Integer id;
	private String name;
	private LocationDto province;
	private Integer type;
	private String contactPhone;

	public ActivityProviderDto() {
		super();
	}

	public ActivityProviderDto(ActivityProvider entity) {
		BeanUtils.copyProperties(entity, this);
		if (entity.getCity() != null) {
			city = new LocationDto(entity.getCity());
		}
		if (entity.getCountry() != null) {
			country = new LocationDto(entity.getCountry());
		}
		if (entity.getProvince() != null) {
			province = new LocationDto(entity.getProvince());
		}
	}

	public LocationDto getCity() {
		return city;
	}

	public LocationDto getCountry() {
		return country;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocationDto getProvince() {
		return province;
	}

	public void setCity(LocationDto city) {
		this.city = city;
	}

	public void setCountry(LocationDto country) {
		this.country = country;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProvince(LocationDto province) {
		this.province = province;
	}

	public ActivityProvider toEntity() {
		ActivityProvider entity = new ActivityProvider();
		BeanUtils.copyProperties(this, entity);
		if (city != null) {
			entity.setCity(city.toEntity());
		}
		if (country != null) {
			entity.setCity(country.toEntity());
		}
		if (province != null) {
			entity.setCity(province.toEntity());
		}
		return entity;
	}
	
	public ActivityProvider toUpdateEntity(ActivityProvider entity) {
		BeanUtils.copyProperties(this, entity);
		if (city != null) {
			entity.setCity(city.toEntity());
		}
		if (country != null) {
			entity.setCountry(country.toEntity());
		}
		if (province != null) {
			entity.setProvince(province.toEntity());
		}
		return entity;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
}
