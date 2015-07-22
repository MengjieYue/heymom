package com.heymom.backend.entity.activity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.incentive.Coupon;

@Entity
@Table(name = "activity")
public class Activity extends BaseEntity {
	private String address;
	private String amenities;
	private Integer attendCount;
	private Integer attendeeMaxAge;
	private Integer attendeeMinAge;
	private Location city;
	private String contactPhone;
	private Location country;
	private List<Coupon> coupons;
	private String description;
	private Location district;
	private Date endTime;
	private Integer followCount;
	private Integer id;
	private String image;
	private Integer InitialAttendeeCount;
	private Double latitude;
	private Double longitude;
	private Integer maxAttedneeCount;
	private String name;
	private Float price;
	private ActivityProvider provider;
	private Location province;
	private Date startTime;
	private Integer type;

	@Column(name = "address", length = 150)
	public String getAddress() {
		return address;
	}

	@Column(name = "amenities", length = 150)
	public String getAmenities() {
		return amenities;
	}

	@Column(name = "attend_count", precision = 10, scale = 0, columnDefinition = "INT DEFAULT 0")
	public Integer getAttendCount() {
		return attendCount;
	}

	@Column(name = "attendee_max_age", precision = 3, scale = 0)
	public Integer getAttendeeMaxAge() {
		return attendeeMaxAge;
	}

	@Column(name = "attendee_min_age", precision = 3, scale = 0)
	public Integer getAttendeeMinAge() {
		return attendeeMinAge;
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

	@ManyToMany(mappedBy = "activities")
	public List<Coupon> getCoupons() {
		return coupons;
	}

	@Column(name = "description", length = 200)
	public String getDescription() {
		return description;
	}

	@ManyToOne
	@JoinColumn(name = "district_id")
	public Location getDistrict() {
		return district;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time")
	public Date getEndTime() {
		return endTime;
	}

	@Column(name = "follow_count", precision = 10, scale = 0, columnDefinition = "INT DEFAULT 0")
	public Integer getFollowCount() {
		return followCount;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 10, scale = 0)
	public Integer getId() {
		return id;
	}

	@Column(name = "image", length = 300)
	public String getImage() {
		return image;
	}

	@Column(name = "initial_attendee_count", precision = 4, scale = 0)
	public Integer getInitialAttendeeCount() {
		return InitialAttendeeCount;
	}

	@Column(name = "latitude", precision = 10, scale = 6)
	public Double getLatitude() {
		return latitude;
	}

	@Column(name = "longitude", precision = 10, scale = 6)
	public Double getLongitude() {
		return longitude;
	}

	@Column(name = "max_attendee_count", precision = 5, scale = 0)
	public Integer getMaxAttedneeCount() {
		return maxAttedneeCount;
	}

	@Column(name = "name", length = 50, nullable = false)
	public String getName() {
		return name;
	}

	@Column(name = "price", precision = 10, scale = 2)
	public Float getPrice() {
		return price;
	}

	@ManyToOne
	@JoinColumn(name = "provider_id")
	public ActivityProvider getProvider() {
		return provider;
	}

	@ManyToOne
	@JoinColumn(name = "province_id")
	public Location getProvince() {
		return province;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time")
	public Date getStartTime() {
		return startTime;
	}

	@Column(name = "type", precision = 1, nullable = false)
	public Integer getType() {
		return type;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAmenities(String amenities) {
		this.amenities = amenities;
	}

	public void setAttendCount(Integer attendCount) {
		this.attendCount = attendCount;
	}

	public void setAttendeeMaxAge(Integer attendeeMaxAge) {
		this.attendeeMaxAge = attendeeMaxAge;
	}

	public void setAttendeeMinAge(Integer attendeeMinAge) {
		this.attendeeMinAge = attendeeMinAge;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDistrict(Location district) {
		this.district = district;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setFollowCount(Integer followCount) {
		this.followCount = followCount;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setInitialAttendeeCount(Integer initialAttendeeCount) {
		InitialAttendeeCount = initialAttendeeCount;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setMaxAttedneeCount(Integer maxAttedneeCount) {
		this.maxAttedneeCount = maxAttedneeCount;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public void setProvider(ActivityProvider provider) {
		this.provider = provider;
	}

	public void setProvince(Location province) {
		this.province = province;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
