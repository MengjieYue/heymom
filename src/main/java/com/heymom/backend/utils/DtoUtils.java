package com.heymom.backend.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.heymom.backend.dto.DeliveryDto;
import com.heymom.backend.dto.DictionaryItemDto;
import com.heymom.backend.dto.activity.ActivityAttendeeRecordDto;
import com.heymom.backend.dto.activity.ActivityDto;
import com.heymom.backend.dto.activity.ActivityFollowRecordDto;
import com.heymom.backend.dto.activity.ActivityProviderDto;
import com.heymom.backend.dto.customized.QuestionDto;
import com.heymom.backend.dto.incentive.CouponDto;
import com.heymom.backend.dto.user.UserDto;
import com.heymom.backend.entity.Delivery;
import com.heymom.backend.entity.DictionaryItem;
import com.heymom.backend.entity.activity.Activity;
import com.heymom.backend.entity.activity.ActivityAttendeeRecord;
import com.heymom.backend.entity.activity.ActivityFollowRecord;
import com.heymom.backend.entity.activity.ActivityProvider;
import com.heymom.backend.entity.customized.Question;
import com.heymom.backend.entity.incentive.Coupon;
import com.heymom.backend.entity.user.User;

public class DtoUtils<CDTO, CEntity> {
	public static DtoUtils<ActivityProviderDto, ActivityProvider> activityProviderDtoUtil = new DtoUtils<ActivityProviderDto, ActivityProvider>(ActivityProviderDto.class, ActivityProvider.class);
	public static DtoUtils<QuestionDto, Question> questionDtoUtil = new DtoUtils<QuestionDto, Question>(QuestionDto.class, Question.class);
	public static DtoUtils<ActivityAttendeeRecordDto, ActivityAttendeeRecord> activityAttendeeRecordDtoUtil = new DtoUtils<ActivityAttendeeRecordDto, ActivityAttendeeRecord>(
			ActivityAttendeeRecordDto.class, ActivityAttendeeRecord.class);
	public static DtoUtils<ActivityDto, Activity> activityDtoUtil = new DtoUtils<ActivityDto, Activity>(
			ActivityDto.class, Activity.class);
	public static DtoUtils<ActivityFollowRecordDto, ActivityFollowRecord> activityFollowRecordDtoUtilsUtil = new DtoUtils<ActivityFollowRecordDto, ActivityFollowRecord>(
			ActivityFollowRecordDto.class, ActivityFollowRecord.class);
	public static DtoUtils<CouponDto, Coupon> couponDtoUtil = new DtoUtils<CouponDto, Coupon>(CouponDto.class,
			Coupon.class);
	public static DtoUtils<DeliveryDto, Delivery> deliveryDtoUtil = new DtoUtils<DeliveryDto, Delivery>(
			DeliveryDto.class, Delivery.class);
	public static DtoUtils<DictionaryItemDto, DictionaryItem> dictionaryItemDtoUtil = new DtoUtils<DictionaryItemDto, DictionaryItem>(
			DictionaryItemDto.class, DictionaryItem.class);
	private static final Logger logger = LoggerFactory.getLogger(DtoUtils.class);
	public static DtoUtils<UserDto, User> userDtoUtil = new DtoUtils<UserDto, User>(UserDto.class, User.class);
	private Class<?> dtoClass;
	private Class<?> entityClass;

	public DtoUtils(Class<?> dtoClass, Class<?> entityClass) {
		super();
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
	}

	@SuppressWarnings("unchecked")
	private CDTO newDtoInstance(CEntity entity) {
		try {
			return (CDTO) dtoClass.getConstructor(entityClass).newInstance(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	public List<CDTO> toDTO(List<CEntity> listSource) {
		List<CDTO> result = new ArrayList<CDTO>();
		for (CEntity entity : listSource) {
			result.add(newDtoInstance(entity));
		}
		return result;
	}

	public Page<CDTO> toDTO(Page<CEntity> pageSource, Pageable pageRequest) {
		return new PageImpl<CDTO>(toDTO(pageSource.getContent()), pageRequest, pageSource.getTotalElements());
	}

	@SuppressWarnings("unchecked")
	public List<CEntity> toEntity(List<CDTO> listSource) {
		List<CEntity> result = new ArrayList<CEntity>();
		for (CDTO dto : listSource) {
			try {
				Method method = dto.getClass().getMethod("toEntity");
				result.add((CEntity) method.invoke(dto));
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return result;
	}

}
