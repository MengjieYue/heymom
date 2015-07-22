package com.heymom.backend.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.dao.CouponDao;
import com.heymom.backend.dao.CouponReceiveRecordDao;
import com.heymom.backend.dto.incentive.CouponDto;
import com.heymom.backend.entity.EntityStatus;
import com.heymom.backend.entity.incentive.Coupon;
import com.heymom.backend.entity.incentive.CouponReceiveRecord;
import com.heymom.backend.entity.user.User;
import com.heymom.backend.utils.DtoUtils;

@Service
public class CouponService {
	@Autowired
	private CouponDao couponDao;
	@Autowired
	private CouponReceiveRecordDao couponReceiveRecordDao;
	@Autowired
	private UserService userService;

	private Specification<CouponReceiveRecord> buildSpecification(final Long userId, final Integer couponId) {
		return new Specification<CouponReceiveRecord>() {
			@Override
			public Predicate toPredicate(Root<CouponReceiveRecord> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.ge(root.get("status").as(Integer.class), 0));
				if (userId != null) {
					predicates.add(cb.equal(root.get("user").get("id").as(Long.class), userId));
				}
				if (couponId != null) {
					predicates.add(cb.equal(root.get("coupon").get("id").as(Integer.class), couponId));
				}

				if (predicates.size() > 0) {
					cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
				} else {
					cq.where(cb.conjunction());
				}
				return cq.getRestriction();
			}
		};
	}

	@Transactional
	public void createCoupon(CouponDto dto) {
		couponDao.save(dto.toEntity());
	}

	@Transactional
	public void deleteCoupon(Integer id) {
		couponDao.findOne(id).setStatus(EntityStatus.DELETEED.getValue());
	}

	@Transactional(readOnly = true)
	public Coupon findById(Integer couponId) {
		Coupon entity = couponDao.findOne(couponId);
		if (entity == null) {
			throw new HeymomException(400001);
		}
		return entity;
	}

	@Transactional(readOnly = true)
	public Boolean hasReceiveCoupon(Integer couponId) {
		User user = userService.getCurrentUser();
		return null != couponReceiveRecordDao.findOne(buildSpecification(user.getId(), couponId));
	}

	@Transactional(readOnly = true)
	public Page<CouponDto> listAllAvaliableCoupons(int currentPage, int pageSize, String sortProperty,
			String sortDirection) {
		Pageable pageRequest = new PageRequest(currentPage, pageSize, Direction.fromString(sortDirection), sortProperty);
		return DtoUtils.couponDtoUtil.toDTO(couponDao.findAllAvaliableCoupons(pageRequest), pageRequest);
	}

	@Transactional(readOnly = true)
	public Page<CouponDto> listAllNotBeginCoupons(int currentPage, int pageSize, String sortProperty,
			String sortDirection) {
		Pageable pageRequest = new PageRequest(currentPage, pageSize, Direction.fromString(sortDirection), sortProperty);
		return DtoUtils.couponDtoUtil.toDTO(couponDao.findAllNotBeginCoupons(pageRequest), pageRequest);
	}

	@Transactional(readOnly = true)
	public Page<CouponDto> listAllReceivedCoupons(int currentPage, int pageSize, String sortProperty,
			String sortDirection) {
		Pageable pageRequest = new PageRequest(currentPage, pageSize, Direction.fromString(sortDirection), sortProperty);
		User user = userService.getCurrentUser();
		Page<CouponReceiveRecord> recordPage = couponReceiveRecordDao.findAll(buildSpecification(user.getId(), null),
				pageRequest);
		List<CouponDto> couponDtos = new ArrayList<CouponDto>();
		for (CouponReceiveRecord couponReceiveRecord : recordPage) {
			couponDtos.add(new CouponDto(couponReceiveRecord.getCoupon()));
		}
		Page<CouponDto> couponPage = new PageImpl<CouponDto>(couponDtos, pageRequest, recordPage.getTotalElements());
		return couponPage;
	}

	@Transactional
	public void updateCoupon(CouponDto couponDto) {
		Coupon entity = couponDao.findOne(couponDto.getId());
		BeanUtils.copyProperties(couponDto, entity);
	}
}
