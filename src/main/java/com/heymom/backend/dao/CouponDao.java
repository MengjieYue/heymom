package com.heymom.backend.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.heymom.backend.entity.incentive.Coupon;

public interface CouponDao extends BaseDao<Coupon, Integer> {
	@Query("from Coupon c where c.status>=0 and c.expireDate>CURRENT_TIMESTAMP")
	public Page<Coupon> findAllAvaliableCoupons(Pageable pageRequest);

	@Query("from Coupon c where c.status>=0 and c.beginDate>CURRENT_TIMESTAMP")
	public Page<Coupon> findAllNotBeginCoupons(Pageable pageRequest);

}
