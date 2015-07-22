package com.heymom.backend.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.dao.ActivityAttendeeRecordDao;
import com.heymom.backend.dto.activity.ActivityAttendeeRecordDto;
import com.heymom.backend.entity.EntityStatus;
import com.heymom.backend.entity.activity.Activity;
import com.heymom.backend.entity.activity.ActivityAttendeeRecord;
import com.heymom.backend.entity.user.User;
import com.heymom.backend.utils.DtoUtils;

@Service
public class ActivityAttendeeRecordService {

	@Autowired
	private ActivityAttendeeRecordDao activityAttendeeRecordDao;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserService userService;

	@Transactional
	public void attend(Integer activityId) {
		Activity activity = activityService.findById(activityId);
		if (activity.getAttendCount() != null && activity.getMaxAttedneeCount() <= activity.getAttendCount()) {
			throw new HeymomException(200002);
		}
		ActivityAttendeeRecord entity = findByUserIdAndActivityId(activityId);
		if (entity == null) {
			entity = new ActivityAttendeeRecord();
			entity.setActivity(activity);
			entity.setUser(userService.getCurrentUser());
			activityAttendeeRecordDao.save(entity);
			activity.setAttendCount(activity.getAttendCount() + 1);
		}
	}

	private Specification<ActivityAttendeeRecord> buildSpecification(final Integer activityId, final Long userId,
			final Boolean isFeedBack) {
		return new Specification<ActivityAttendeeRecord>() {
			@Override
			public Predicate toPredicate(Root<ActivityAttendeeRecord> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.ge(root.get("status").as(Integer.class), 0));
				if (activityId != null) {
					predicates.add(cb.equal(root.get("activity").get("id").as(Integer.class), activityId));
				}
				if (userId != null) {
					predicates.add(cb.equal(root.get("user").get("id").as(Long.class), userId));
				}

				if (isFeedBack != null && isFeedBack) {
					predicates.add(cb.isNotNull(root.get("score")));
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
	public void cancel(Integer activityId) {
		ActivityAttendeeRecord entity = decreaseAttendeeCount(activityId);
		entity.setStatus(EntityStatus.DELETEED.getValue());
	}

	private ActivityAttendeeRecord decreaseAttendeeCount(Integer activityId) {
		ActivityAttendeeRecord entity = findByUserIdAndActivityId(activityId);
		entity.getActivity().setAttendCount(entity.getActivity().getAttendCount() - 1);
		return entity;
	}

	@Transactional
	public void feedback(Integer activityId, Integer score, String comments) {
		User user = userService.getCurrentUser();
		ActivityAttendeeRecord record = findByUserIdAndActivityId(activityId);
		if (record == null) {
			record = new ActivityAttendeeRecord();
			Activity activity = activityService.findById(activityId);
			record.setActivity(activity);
			record.setUser(user);
		}
		record.setScore(score);
		record.setComments(comments);
		activityAttendeeRecordDao.save(record);
	}

	@Transactional(readOnly = true)
	public ActivityAttendeeRecord findByUserIdAndActivityId(Integer activityId) {
		Long userId = userService.getCurrentUser().getId();
		ActivityAttendeeRecord entity = activityAttendeeRecordDao
				.findOne(buildSpecification(activityId, userId, false));
		return entity;
	}

	@Transactional(readOnly = true)
	public Page<ActivityAttendeeRecordDto> listFeedBack(Integer activityId, int currentPage, int pageSize,
			String sortProperty, String sortDirection) {
		Pageable pageRequest = new PageRequest(currentPage, pageSize, Direction.fromString(sortDirection), sortProperty);
		return DtoUtils.activityAttendeeRecordDtoUtil.toDTO(page(pageRequest, activityId, null, true), pageRequest);
	}

	@Transactional(readOnly = true)
	public Page<ActivityAttendeeRecordDto> listMyActivityAndFeedback(int currentPage, int pageSize,
			String sortProperty, String sortDirection) {
		Long userId = userService.getCurrentUser().getId();
		Pageable pageRequest = new PageRequest(currentPage, pageSize, Direction.fromString(sortDirection), sortProperty);
		return DtoUtils.activityAttendeeRecordDtoUtil.toDTO(page(pageRequest, null, userId, false), pageRequest);
	}

	private Page<ActivityAttendeeRecord> page(Pageable pageRequest, final Integer activityId, final Long userId,
			Boolean isFeedBack) {
		Specification<ActivityAttendeeRecord> specification = buildSpecification(activityId, userId, isFeedBack);
		return activityAttendeeRecordDao.findAll(specification, pageRequest);
	}
	
	public Long countMyActivity(){
		Long userId = userService.getCurrentUser().getId();
		return activityAttendeeRecordDao.count(buildSpecification(null, userId, null));
	}
}
