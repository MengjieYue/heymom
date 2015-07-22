package com.heymom.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.heymom.backend.entity.customized.Option;

public interface OptionDao extends BaseDao<Option, Integer> {
	@Query("from Option a where a.status>=0 and id in (:ids)")
	public List<Option> findByIds(@Param("ids") List<Integer> ids);
}
