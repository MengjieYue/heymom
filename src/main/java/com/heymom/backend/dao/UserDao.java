package com.heymom.backend.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.heymom.backend.entity.user.User;

public interface UserDao extends BaseDao<User, Long> {
	@Query("from User where status>=0 and mobile =:mobile")
	public User findByMobile(@Param("mobile") String mobile);

	@Query("from User where status>=0 and mobile =:mobile and password=:password")
	public User findByMobileAndPassword(@Param("mobile") String mobile, @Param("password") String password);

	@Query("from User where status>=0 and token =:userToken")
	public User findByToken(@Param("userToken") String userToken);
}
