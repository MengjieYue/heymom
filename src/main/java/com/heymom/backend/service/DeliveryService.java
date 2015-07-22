package com.heymom.backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.heymom.backend.dao.DeliveryDao;
import com.heymom.backend.dto.DeliveryDto;
import com.heymom.backend.entity.Delivery;
import com.heymom.backend.utils.DtoUtils;

@Service
public class DeliveryService {
	@Autowired
	private DeliveryDao deliveryDao;

	@Transactional
	public void createDelivery(DeliveryDto dto) throws JsonParseException, JsonMappingException, IOException {
		deliveryDao.save(dto.toEntity());
	}

	@Transactional(readOnly = true)
	public Page<DeliveryDto> listAvaliableDeliveries(int currentPage, int pageSize, String sortProperty,
			String sortDirection) {
		Pageable pageRequest = new PageRequest(currentPage, pageSize, Direction.fromString(sortDirection), sortProperty);
		Page<Delivery> deliveries = deliveryDao.findAll(pageRequest);
		return DtoUtils.deliveryDtoUtil.toDTO(deliveries, pageRequest);
	}

	@Transactional(readOnly = true)
	public List<DeliveryDto> listByIds(Iterable<Integer> ids) {
		return DtoUtils.deliveryDtoUtil.toDTO(deliveryDao.findByIds(ids));
	}
	
	@Transactional(readOnly = true)
	public Delivery findbyId(Integer deliveryId) throws JsonParseException, JsonMappingException, IOException {
		return deliveryDao.findOne(deliveryId);
	}

	@Transactional
	public void updateDelivery(DeliveryDto dto) throws JsonParseException, JsonMappingException, IOException {
		deliveryDao.save(dto.toEntity());
	}
}
