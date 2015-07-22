package com.heymom.backend.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.heymom.backend.entity.activity.Activity;

public interface ActivityDao extends BaseDao<Activity, Integer> {
	@Query("from Activity a where a.status>=0")
	public List<Activity> findAvaliableActivities();

	@Query("from Activity a where a.status>=0")
	public Page<Activity> findAvaliableActivities(Pageable pageRequest);

	@Query("from Activity a where a.status>=0 and id in (:ids)")
	public List<Activity> findByIds(@Param("ids") List<Integer> ids);
}
