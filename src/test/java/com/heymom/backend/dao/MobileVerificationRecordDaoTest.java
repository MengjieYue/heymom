package com.heymom.backend.dao;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heymom.backend.entity.user.MobileVerificationRecord;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
public class MobileVerificationRecordDaoTest {
	@Autowired
	private MobileVerificationRecordDao mobileVerificationRecordDao;

	@Before
	public void setUp() throws Exception {
		MobileVerificationRecord r = new MobileVerificationRecord();
		r.setCode("123456");
		r.setMobile("123");
		mobileVerificationRecordDao.save(r);
	}

	@After
	public void tearDown() throws Exception {
		mobileVerificationRecordDao.deleteAll();
	}

	@Test
	public void testCountByMobileandCreateTime() {
		int count = mobileVerificationRecordDao.countByMobileandCreateTime("123",
				new DateTime().toLocalDate().toDate(), new DateTime().toLocalDate().plusDays(1).toDate());
		Assert.assertEquals(1, count);
	}
}
