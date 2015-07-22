package com.heymom.backend.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.heymom.backend.entity.user.MobileVerificationRecord;

public interface MobileVerificationRecordDao extends BaseDao<MobileVerificationRecord, Long> {
	@Query("select count(*) from MobileVerificationRecord where status>=0 and mobile =:mobile and code=:code")
	public long countByMobileandCode(@Param("mobile") String mobile, @Param("code") String code);

	@Query("select count(*) from MobileVerificationRecord where status>=0 and mobile =:mobile and createTime>=:startTime and createTime <:endTime")
	public Integer countByMobileandCreateTime(@Param("mobile") String mobile, @Param("startTime") Date startTime,
			@Param("endTime") Date endTime);

}
