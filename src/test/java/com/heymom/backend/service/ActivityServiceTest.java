package com.heymom.backend.service;

import javax.transaction.Transactional;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heymom.backend.dto.activity.ActivityDto;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
public class ActivityServiceTest {
	@Autowired
	private ActivityService activityService;

	@Test
	public void testListAvaliableActivitiesIntIntStringStringListOfIntegerIntegerIntegerIntegerString() {
		Page<ActivityDto> page = activityService.listAvaliableActivities(0, 2, "createTime", "DESC", null, null, null,
				null, null);
		Assert.assertNotNull(page);
		System.out.println(page.getTotalElements());
	}

}
