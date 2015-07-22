package com.heymom.backend.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.dao.KidDao;
import com.heymom.backend.dao.MobileVerificationRecordDao;
import com.heymom.backend.dao.UserDao;
import com.heymom.backend.dao.UserInfoDao;
import com.heymom.backend.dto.user.KidDto;
import com.heymom.backend.dto.user.UserDto;
import com.heymom.backend.dto.user.UserInfoDto;
import com.heymom.backend.entity.user.Authority;
import com.heymom.backend.entity.user.Kid;
import com.heymom.backend.entity.user.MobileVerificationRecord;
import com.heymom.backend.entity.user.Role;
import com.heymom.backend.entity.user.User;
import com.heymom.backend.entity.user.UserInfo;
import com.heymom.backend.utils.RandomUtil;

@Service
public class UserService {

	private ThreadLocal<User> currentUser = new ThreadLocal<User>();
	@Autowired
	private KidDao kidDao;
	@Value("${max.send.mobile.verification.code.count}")
	private int maxSendMobileVerificationCodeCount;
	@Autowired
	private MobileVerificationRecordDao mobileVerificationRecordDao;
	@Autowired
	private SMSService SMSService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserInfoDao userInfoDao;
	@Value("${sms.user.verify.code}")
	private String userVerifyTemplate;

	@Transactional
	public String changeUserPassword(String mobile, String verificationCode, String password) {
		if (mobileVerificationRecordDao.countByMobileandCode(mobile, verificationCode) == 0) {
			throw new HeymomException(100003);
		}
		User user = userDao.findByMobile(mobile);
		user.setPassword(password);
		user.setUserToken(UUID.randomUUID().toString());
		return user.getUserToken();
	}

	@Transactional(readOnly = true)
	public void checkUserAuthority(String authorityName) {
		User user = currentUser.get();
		if (user == null) {
			throw new HeymomException(100005);
		}
		if (authorityName == null || authorityName.length() == 0) {
			return;
		}

		for (Role role : user.getRoles()) {
			for (Authority authority : role.getAuthorities()) {
				if (authority.getName().equalsIgnoreCase(authorityName)) {
					return;
				}
			}
		}
		throw new HeymomException(100007);
	}

	@Transactional
	public String createUser(String mobile, String verificationCode, String password) {
		UserDto dto = new UserDto();
		dto.setMobile(mobile);
		dto.setPassword(password);
		return createUser(dto, verificationCode).getUserToken();
	}

	@Transactional
	public User createUser(UserDto dto, String verificationCode) {
		if (StringUtils.isEmpty(verificationCode) && StringUtils.isEmpty(dto.getMobile())) {
			throw new HeymomException(100004);
		}
		isMobileExist(dto.getMobile());

		hasSentMaxTimes(dto.getMobile());
		if (mobileVerificationRecordDao.countByMobileandCode(dto.getMobile(), verificationCode) == 0) {
			throw new HeymomException(100003);
		}
		User entity = dto.toEntity();
		entity.setUserToken(UUID.randomUUID().toString());
		entity.setTokenCreateTime(new Date());
		userDao.save(entity);

		return entity;
	}

	@Transactional(readOnly = true)
	public UserInfoDto findUserInfo() {
		if (getCurrentUser().getUserInfo() != null) {
			return new UserInfoDto(getCurrentUser().getUserInfo());
		} else {
			return null;
		}
	}

	@Transactional(readOnly = true)
	public KidDto findUserKid() {
		User user = getCurrentUser();
		KidDto kidDto = new KidDto();
		if (user.getKids() != null && user.getKids().iterator().hasNext()) {
			kidDto = new KidDto(user.getKids().iterator().next());
		}
		return kidDto;
	}

	public User getCurrentUser() {
		return currentUser.get();
	}

	@Transactional(readOnly = true)
	public User getCurrentUserByToken(String userToken) {
		User user = userDao.findByToken(userToken);
		if (user == null) {
			throw new HeymomException(100005);
		}
		if (System.currentTimeMillis() - user.getTokenCreateTime().getTime() > 86400000) {
			throw new HeymomException(100006);
		}
		currentUser.set(user);
		return currentUser.get();
	}

	public void getCurrentUserByTokenNoException(String userToken) {
		try {
			getCurrentUserByToken(userToken);
		} catch (HeymomException e) {
		}
	}

	private void hasSentMaxTimes(String mobile) {
		Date startTime = new DateTime().toLocalDate().toDate();
		Date endTime = new DateTime().toLocalDate().plusDays(1).toDate();
		int count = mobileVerificationRecordDao.countByMobileandCreateTime(mobile, startTime, endTime);
		if (count > maxSendMobileVerificationCodeCount) {
			throw new HeymomException(100002);
		}
	}

	private void isMobileExist(String mobile) {
		if (userDao.findByMobile(mobile) != null) {
			throw new HeymomException(100001);
		}
	}

	@Transactional
	public String login(String mobile, String password) {
		User user = userDao.findByMobileAndPassword(mobile, password);
		return refreshToken(user);
	}

	private String refreshToken(User user) {
		if (user != null) {
			user.setUserToken(UUID.randomUUID().toString());
			user.setTokenCreateTime(new Date());
			userDao.save(user);
			currentUser.set(user);
			return user.getUserToken();
		}
		throw new HeymomException(100005);
	}

	@Transactional
	public String reLogin(String userToken) {
		User user = userDao.findByToken(userToken);
		return refreshToken(user);
	}

	@Transactional
	public void sendMobileVerification(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			throw new HeymomException(100004);
		}
		// isMobileExist(mobile); //忘记密码发送短信不需要检查已存在
		hasSentMaxTimes(mobile);
		String code = RandomUtil.generate6Int();
		SMSService.sendSMS(mobile, MessageFormat.format(userVerifyTemplate, code));
		mobileVerificationRecordDao.save(new MobileVerificationRecord(code, mobile));
	}

	@Transactional
	public void updateUserInfo(UserInfoDto userInfo) {
		User user = getCurrentUser();
		UserInfo userInfoEntity = user.getUserInfo();
		if (userInfoEntity == null) {
			userInfoEntity = userInfo.toEntity();
			userInfoEntity.setUser(user);
			userInfoDao.save(userInfoEntity);
		} else {
			userInfoEntity.setAddress(userInfo.getAddress());
		}
	}

	@Transactional
	public void updateUserKid(KidDto kidDto) {
		User user = getCurrentUser();
		List<Kid> entityList = user.getKids();
		if (entityList == null || entityList.isEmpty()) {
			Kid entity = kidDto.toEntity();
			entity.setParent(user);
			kidDao.save(entity);
		} else {
			Kid entity = entityList.iterator().next();
			BeanUtils.copyProperties(kidDto, entity);
		}
	}

	public Boolean verifyMobileVerification(String mobile, String verificationCode) {
		return 0 < mobileVerificationRecordDao.countByMobileandCode(mobile, verificationCode);
	}
}
