package com.heymom.backend.entity.incentive;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.activity.Activity;
import com.heymom.backend.entity.activity.ActivityProvider;

@Entity
@Table(name = "coupon")
public class Coupon extends BaseEntity {
	private List<Activity> activities;
	private Date beginDate;
	private String description;
	private Date expireDate;
	private Integer id;
	private String imageUrl;
	private Integer maxCount;
	private String name;
	private ActivityProvider provider;
	private List<CouponReceiveRecord> receiveRecords;
	private Date startTime;

	private Integer type;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "activity_coupon", joinColumns = { @JoinColumn(name = "coupon_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "activity_id", referencedColumnName = "id") })
	public List<Activity> getActivities() {
		return activities;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getBeginDate() {
		return beginDate;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return description;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getExpireDate() {
		return expireDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	@Column(name = "imageUrl", length = 200)
	public String getImageUrl() {
		return imageUrl;
	}

	@Column(name = "maxCount", nullable = false, precision = 10, scale = 0)
	public Integer getMaxCount() {
		return maxCount;
	}

	@Column(name = "name", length = 50)
	public String getName() {
		return name;
	}

	@ManyToOne
	@JoinColumn(name = "provider_id")
	public ActivityProvider getProvider() {
		return provider;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon")
	@Where(clause = "status>=0")
	public List<CouponReceiveRecord> getReceiveRecords() {
		return receiveRecords;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartTime() {
		return startTime;
	}

	@Column(name = "type", precision = 2, scale = 0)
	public Integer getType() {
		return type;
	}

	public void setActivities(List<Activity> activities) {
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

	public void setProvider(ActivityProvider provider) {
		this.provider = provider;
	}

	public void setReceiveRecords(List<CouponReceiveRecord> receiveRecords) {
		this.receiveRecords = receiveRecords;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
