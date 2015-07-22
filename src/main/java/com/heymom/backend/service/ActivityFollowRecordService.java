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

import com.heymom.backend.dao.ActivityFollowRecordDao;
import com.heymom.backend.dto.activity.ActivityFollowRecordDto;
import com.heymom.backend.entity.EntityStatus;
import com.heymom.backend.entity.activity.Activity;
import com.heymom.backend.entity.activity.ActivityFollowRecord;
import com.heymom.backend.utils.DtoUtils;

@Service
public class ActivityFollowRecordService {
	@Autowired
	private ActivityFollowRecordDao activityFollowRecordDao;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private UserService userService;

	private Specification<ActivityFollowRecord> buildSpecification(final Integer activityId, final Long userId) {
		return new Specification<ActivityFollowRecord>() {
			@Override
			public Predicate toPredicate(Root<ActivityFollowRecord> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.ge(root.get("status").as(Integer.class), 0));
				if (activityId != null) {
					predicates.add(cb.equal(root.get("activity").get("id").as(Integer.class), activityId));
				}
				if (userId != null) {
					predicates.add(cb.equal(root.get("user").get("id").as(Long.class), userId));
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
	public void cancelFollow(Integer activityId) {
		ActivityFollowRecord entity = decreaseFollowCount(activityId);
		entity.setStatus(EntityStatus.DELETEED.getValue());
	}

	private ActivityFollowRecord decreaseFollowCount(Integer activityId) {
		ActivityFollowRecord entity = findByUserIdAndActivityId(activityId);
		entity.getActivity().setFollowCount(entity.getActivity().getFollowCount() - 1);
		return entity;
	}

	public ActivityFollowRecord findByUserIdAndActivityId(Integer activityId) {
		Long userId = userService.getCurrentUser().getId();
		ActivityFollowRecord entity = activityFollowRecordDao.findOne(buildSpecification(activityId, userId));
		return entity;
	}

	public void follow(Integer activityId) {
		Activity activity = activityService.findById(activityId);

		ActivityFollowRecord entity = findByUserIdAndActivityId(activityId);
		if (entity == null) {
			entity = new ActivityFollowRecord();
			entity.setActivity(activity);
			entity.setUser(userService.getCurrentUser());
			activityFollowRecordDao.save(entity);
			activity.setFollowCount(activity.getFollowCount() + 1);
		}
	}

	public Page<ActivityFollowRecordDto> listMyFollowed(int currentPage, int pageSize, String sortProperty,
			String sortDirection) {
		Long userId = userService.getCurrentUser().getId();
		Pageable pageRequest = new PageRequest(currentPage, pageSize, Direction.fromString(sortDirection), sortProperty);
		return DtoUtils.activityFollowRecordDtoUtilsUtil.toDTO(page(pageRequest, userId), pageRequest);
	}

	private Page<ActivityFollowRecord> page(Pageable pageRequest, final Long userId) {

		Specification<ActivityFollowRecord> specification = buildSpecification(null, userId);
		return activityFollowRecordDao.findAll(specification, pageRequest);
	}
	
	public Long countMyFollow(){
		Long userId = userService.getCurrentUser().getId();
		return activityFollowRecordDao.count(buildSpecification(null, userId));
	}
}
