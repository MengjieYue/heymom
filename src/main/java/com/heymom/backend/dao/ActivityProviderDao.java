package com.heymom.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.heymom.backend.entity.activity.ActivityProvider;

public interface ActivityProviderDao extends BaseDao<ActivityProvider, Integer>{
	@Query("from ActivityProvider a where a.status>=0")
	public List<ActivityProvider> findAvaliableActivityProviders();
	@Query("from ActivityProvider a where a.name = :providerName")
	public ActivityProvider findByName(@Param("providerName")String providerName);
}
