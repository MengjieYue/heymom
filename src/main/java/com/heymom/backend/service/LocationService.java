package com.heymom.backend.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.dao.LocationDao;
import com.heymom.backend.dto.activity.LocationDto;
import com.heymom.backend.entity.EntityStatus;
import com.heymom.backend.entity.activity.Location;

@Service
public class LocationService {
	@Autowired
	private LocationDao locationDao;

	@Transactional
	public void createLocation(LocationDto locationDto) {
		locationDao.save(locationDto.toEntity());
	}

	@Transactional
	public void deleteLocation(Integer id) {
		locationDao.findOne(id).setStatus(EntityStatus.DELETEED.getValue());
	}

	@Transactional(readOnly = true)
	public Location findById(Integer locationId) {
		Location entity = locationDao.findOne(locationId);
		if (entity == null) {
			throw new HeymomException(700001);
		}
		return entity;
	}

	@Transactional
	public void updateLocation(LocationDto locationDto) {
		Location entity = locationDao.findOne(locationDto.getId());
		BeanUtils.copyProperties(locationDto, entity);
	}
}
