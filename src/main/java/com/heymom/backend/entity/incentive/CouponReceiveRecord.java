package com.heymom.backend.entity.incentive;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.heymom.backend.entity.BaseEntity;
import com.heymom.backend.entity.user.User;

@Entity
@Table(name = "coupon_receive_record")
public class CouponReceiveRecord extends BaseEntity {
	private Coupon coupon;
	private Long id;
	private Date usedTime;
	private User user;

	@ManyToOne
	@JoinColumn(name = "coupon_id")
	public Coupon getCoupon() {
		return coupon;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, precision = 20, scale = 0)
	public Long getId() {
		return id;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getUsedTime() {
		return usedTime;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
