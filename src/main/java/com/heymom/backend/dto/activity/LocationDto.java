package com.heymom.backend.dto.activity;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.entity.activity.Location;

public class LocationDto {
	private Integer id;
	private double latitude;
	private double longitude;
	private String name;
	private Integer type;

	public LocationDto() {
		super();
	}

	public LocationDto(Location entity) {
		BeanUtils.copyProperties(entity, this);
	}

	public Integer getId() {
		return id;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getName() {
		return name;
	}

	public Integer getType() {
		return type;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Location toEntity() {
		Location entity = new Location();
		BeanUtils.copyProperties(this, entity);
		return entity;
	}
}
