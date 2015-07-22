package com.heymom.backend.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.heymom.backend.entity.DictionaryItem;

public interface DictionaryItemDao extends BaseDao<DictionaryItem, Integer> {
	@Query("from DictionaryItem d where d.status>=0")
	public List<DictionaryItem> findAllAvailable();

}
