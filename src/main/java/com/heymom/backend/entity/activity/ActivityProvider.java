package com.heymom.backend.entity.activity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.incentive.Coupon;

@Entity
@Table(name = "activity_provider")
public class ActivityProvider extends BaseEntity {
	private List<Activity> activities;
	private Location city;
	private String contactPhone;
	private Location country;
	private List<Coupon> coupons;
	private Integer id;
	private String name;
	private Location province;
	private Integer type;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "provider")
	@Where(clause = "status>=0")
	public List<Activity> getActivities() {
		return activities;
	}

	@ManyToOne
	@JoinColumn(name = "city_id")
	public Location getCity() {
		return city;
	}

	@Column(name = "contact_phone", length = 50)
	public String getContactPhone() {
		return contactPhone;
	}

	@ManyToOne
	@JoinColumn(name = "country_id")
	public Location getCountry() {
		return country;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "provider")
	@Where(clause = "status>=0")
	public List<Coupon> getCoupons() {
		return coupons;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	@Column(name = "name", unique = true, length = 50)
	public String getName() {
		return name;
	}

	@ManyToOne
	@JoinColumn(name = "province_id")
	public Location getProvince() {
		return province;
	}

	@Column(name = "type", precision = 2, scale = 0)
	public Integer getType() {
		return type;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public void setCity(Location city) {
		this.city = city;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setCountry(Location country) {
		this.country = country;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProvince(Location province) {
		this.province = province;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
