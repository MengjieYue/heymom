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
import com.heymom.backend.dao.ActivityProviderDao;
import com.heymom.backend.dto.activity.ActivityProviderDto;
import com.heymom.backend.entity.EntityStatus;
import com.heymom.backend.entity.activity.ActivityProvider;
import com.heymom.backend.utils.DtoUtils;

@Service
public class ActivityProviderService {
	@Autowired
	private ActivityProviderDao activityProviderDao;

	private Specification<ActivityProvider> buildSpecification(
			final Long userId, final List<Integer> citiyIds,
			final Integer type, final String nameContent) {
		return new Specification<ActivityProvider>() {
			@Override
			public Predicate toPredicate(Root<ActivityProvider> root,
					CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.ge(root.get("status").as(Integer.class), 0));
				if (userId != null) {
					predicates.add(cb.equal(
							root.get("user").get("id").as(Long.class), userId));
				}
				if (citiyIds != null) {
					Path<Integer> cityId = root.get("city.id");
					In<Integer> in = cb.in(cityId);
					for (Integer tempCityId : citiyIds) {
						in.value(tempCityId);
					}
					predicates.add(in);
				}
				if (nameContent != null) {
					predicates.add(cb.like(root.get("name").as(String.class),
							"%" + nameContent + "%"));
				}

				if (predicates.size() > 0) {
					cq.where(cb.and(predicates.toArray(new Predicate[predicates
							.size()])));
				} else {
					cq.where(cb.conjunction());
				}
				return cq.getRestriction();
			}
		};
	}

	@Transactional
	public void createActivityProvider(ActivityProviderDto activityProviderDto) {
		activityProviderDao.save(activityProviderDto.toEntity());
	}

	@Transactional(readOnly = true)
	public ActivityProvider findById(Integer activityProviderId) {
		ActivityProvider entity = activityProviderDao
				.findOne(activityProviderId);
		if (entity == null) {
			throw new HeymomException(500001);
		}
		return entity;
	}

	@Transactional
	public void deleteActivityProvider(Integer activityProviderId) {
		ActivityProvider entity = findById(activityProviderId);
		entity.setStatus(EntityStatus.DELETEED.getValue());
	}

	@Transactional(readOnly = true)
	public List<ActivityProviderDto> listAllActivityProviders() {
		return DtoUtils.activityProviderDtoUtil.toDTO(activityProviderDao
				.findAvaliableActivityProviders());
	}

	@Transactional
	public void updateActivityProvider(ActivityProviderDto activityProviderDto) {
		ActivityProvider entity = activityProviderDao
				.findOne(activityProviderDto.getId());
		activityProviderDto.toUpdateEntity(entity);
	}

	@Transactional(readOnly = true)
	public Page<ActivityProviderDto> listAllActivityProviders(int currentPage,
			int pageSize, String sortProperty, String sortDirection,
			List<Integer> citiyIds, Integer startAge, Integer endAge,
			Integer type, String nameContent) {

		Pageable pageRequest = new PageRequest(currentPage, pageSize,
				Direction.fromString(sortDirection), sortProperty);

		Page<ActivityProvider> providers = activityProviderDao.findAll(
				buildSpecification(null, citiyIds, type, nameContent),
				pageRequest);
		return DtoUtils.activityProviderDtoUtil.toDTO(providers, pageRequest);
	}

	@Transactional(readOnly = true)
	public boolean findByName(String providerName) {
		if (activityProviderDao.findByName(providerName) != null)
			return true;
		return false;
	}

}
