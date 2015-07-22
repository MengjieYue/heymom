package com.heymom.backend.entity.activity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.heymom.backend.entity.BaseEntity;

@Entity
@Table(name = "location")
public class Location extends BaseEntity {
	private Integer id;
	private double latitude;
	private double longitude;
	private String name;
	private Location parent;
	private Integer type;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	@Column(name = "latitude", precision = 10, scale = 6)
	public double getLatitude() {
		return latitude;
	}

	@Column(name = "longitude", precision = 10, scale = 6)
	public double getLongitude() {
		return longitude;
	}

	@Column(name = "name", unique = true, nullable = false, length = 50)
	public String getName() {
		return name;
	}

	@OneToOne
	@PrimaryKeyJoinColumn
	public Location getParent() {
		return parent;
	}

	@Column(name = "type", precision = 1, nullable = false)
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

	public void setParent(Location parent) {
		this.parent = parent;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
