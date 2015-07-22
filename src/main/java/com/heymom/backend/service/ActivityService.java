package com.heymom.backend.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
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
import com.heymom.backend.dao.ActivityDao;
import com.heymom.backend.dto.activity.ActivityDto;
import com.heymom.backend.entity.EntityStatus;
import com.heymom.backend.entity.activity.Activity;
import com.heymom.backend.utils.DtoUtils;

@Service
public class ActivityService {
	@Autowired
	private ActivityDao activityDao;

	private Specification<Activity> buildSpecification(final Long userId, final List<Integer> citiyIds,
			final Integer startAge, final Integer endAge, final List<Integer> types, final String nameContent) {
		return new Specification<Activity>() {
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.ge(root.get("status").as(Integer.class), 0));
				if (userId != null) {
					predicates.add(cb.equal(root.get("user").get("id").as(Long.class), userId));
				}
				if (citiyIds != null) {
					Path<Integer> cityId = root.get("city").get("id");
					In<Integer> in = cb.in(cityId);
					for (Integer tempCityId : citiyIds) {
						in.value(tempCityId);
					}
					predicates.add(in);
				}
				if (types != null) {
					Path<Integer> type = root.get("type");
					In<Integer> in = cb.in(type);
					for (Integer tmpType : types) {
						in.value(tmpType);
					}
					predicates.add(in);
				}
				if (startAge != null) {
					predicates.add(cb.ge(root.get("attendeeMinAge").as(Integer.class), startAge));
				}
				if (endAge != null) {
					predicates.add(cb.le(root.get("attendeeMaxAge").as(Integer.class), endAge));
				}
				if (nameContent != null) {
					predicates.add(cb.like(root.get("name").as(String.class), "%" + nameContent + "%"));
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
	public void createActivity(ActivityDto activityDto) {
		activityDao.save(activityDto.toEntity());
	}

	@Transactional
	public void deleteActivity(Integer activityId) {
		Activity entity = findById(activityId);
		entity.setStatus(EntityStatus.DELETEED.getValue());
	}

	@Transactional(readOnly = true)
	public Activity findById(Integer activityId) {
		Activity entity = activityDao.findOne(activityId);
		if (entity == null) {
			throw new HeymomException(200001);
		}
		return entity;
	}

	@Transactional(readOnly = true)
	public ActivityDto findOneActivity(Integer activityId) {
		Activity entity = findById(activityId);
		return new ActivityDto(entity);
	}

	@Transactional(readOnly = true)
	public List<ActivityDto> listAvaliableActivities() {
		return DtoUtils.activityDtoUtil.toDTO(activityDao.findAvaliableActivities());
	}

	@Transactional(readOnly = true)
	public Page<ActivityDto> listAvaliableActivities(int currentPage, int pageSize, String sortProperty,
			String sortDirection, List<Integer> citiyIds, Integer startAge, Integer endAge, List<Integer> types,
			String nameContent) {

		Pageable pageRequest = new PageRequest(currentPage, pageSize, Direction.fromString(sortDirection), sortProperty);

		Page<Activity> activties = activityDao.findAll(
				buildSpecification(null, citiyIds, startAge, endAge, types, nameContent), pageRequest);
		return DtoUtils.activityDtoUtil.toDTO(activties, pageRequest);
	}

	@Transactional(readOnly = true)
	public List<ActivityDto> listByIds(List<Integer> ids) {
		return DtoUtils.activityDtoUtil.toDTO(activityDao.findByIds(ids));
	}

	@Transactional
	public void updateActivity(ActivityDto activityDto) {
		Activity entity = activityDao.findOne(activityDto.getId());
		activityDto.toUpdateEntity(entity);
	}
}
