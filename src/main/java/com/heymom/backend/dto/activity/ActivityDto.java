package com.heymom.backend.dto.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.heymom.backend.dto.incentive.CouponDto;
import com.heymom.backend.entity.activity.Activity;

public class ActivityDto {
	private String address;
	private List<Integer> amenities;
	private Integer attendCount;
	private Integer attendeeMaxAge;
	private Integer attendeeMinAge;
	private LocationDto city;
	private String contactPhone;
	private LocationDto country;
	private List<CouponDto> coupons;
	private String description;
	private LocationDto district;
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
	private ActivityProviderDto provider;
	private LocationDto province;
	private Date startTime;
	private Integer type;

	public ActivityDto() {
		super();
	}

	public ActivityDto(Activity entity) {
		BeanUtils.copyProperties(entity, this);
		if (entity.getAmenities() != null) {
			amenities = new ArrayList<Integer>();
			String[] tmpArray = entity.getAmenities().split(",");
			for (int i = 0; i < tmpArray.length; i++) {
				amenities.add(Integer.parseInt(tmpArray[i]));
			}
		}
		if (entity.getDistrict() != null) {
			district = new LocationDto(entity.getDistrict());
		}
		if (entity.getCity() != null) {
			city = new LocationDto(entity.getCity());
		}
		if (entity.getCountry() != null) {
			country = new LocationDto(entity.getCountry());
		}
		if (entity.getProvince() != null) {
			province = new LocationDto(entity.getProvince());
		}
		if (entity.getProvider() != null) {
			provider = new ActivityProviderDto(entity.getProvider());
		}
	}

	public String getAddress() {
		return address;
	}

	public List<Integer> getAmenities() {
		return amenities;
	}

	public Integer getAttendCount() {
		return attendCount;
	}

	public Integer getAttendeeMaxAge() {
		return attendeeMaxAge;
	}

	public Integer getAttendeeMinAge() {
		return attendeeMinAge;
	}

	public LocationDto getCity() {
		return city;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public LocationDto getCountry() {
		return country;
	}

	public List<CouponDto> getCoupons() {
		return coupons;
	}

	public String getDescription() {
		return description;
	}

	public LocationDto getDistrict() {
		return district;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Integer getFollowCount() {
		return followCount;
	}

	public Integer getId() {
		return id;
	}

	public String getImage() {
		return image;
	}

	public Integer getInitialAttendeeCount() {
		return InitialAttendeeCount;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public Integer getMaxAttedneeCount() {
		return maxAttedneeCount;
	}

	public String getName() {
		return name;
	}

	public Float getPrice() {
		return price;
	}

	public ActivityProviderDto getProvider() {
		return provider;
	}

	public LocationDto getProvince() {
		return province;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Integer getType() {
		return type;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAmenities(List<Integer> amenities) {
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

	public void setCity(LocationDto city) {
		this.city = city;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setCountry(LocationDto country) {
		this.country = country;
	}

	public void setCoupons(List<CouponDto> coupons) {
		this.coupons = coupons;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDistrict(LocationDto district) {
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

	public void setProvider(ActivityProviderDto provider) {
		this.provider = provider;
	}

	public void setProvince(LocationDto province) {
		this.province = province;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Activity toEntity() {
		Activity entity = new Activity();
		BeanUtils.copyProperties(this, entity);
		if (amenities != null) {
			String tempString = "";
			for (Integer integer : amenities) {
				tempString = tempString + integer + ",";
			}
			tempString = tempString.substring(0, tempString.length() - 1);
			entity.setAmenities(tempString);
		}
		if (district != null) {
			entity.setDistrict(district.toEntity());
		}
		if (city != null) {
			entity.setCity(city.toEntity());
		}
		if (country != null) {
			entity.setCountry(country.toEntity());
		}
		if (province != null) {
			entity.setProvince(province.toEntity());
		}
		if (provider != null) {
			entity.setProvider(provider.toEntity());
		}
		return entity;
	}

	public Activity toUpdateEntity(Activity entity) {
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
		if (provider != null) {
			entity.setProvider(provider.toEntity());
		}
		return entity;
	}
}
