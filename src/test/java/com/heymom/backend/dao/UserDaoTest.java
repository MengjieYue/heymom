package com.heymom.backend.dao;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heymom.backend.entity.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
public class UserDaoTest {
	@Autowired
	private UserDao userDao;

	@Before
	public void setUp() throws Exception {
		User user1 = new User();
		user1.setEmail("test email");
		user1.setMobile("123");
		user1.setName("test");
		user1.setPassword("password");
		userDao.save(user1);
	}

	public void tearDown() throws Exception {
		userDao.deleteAll();
	}

	@Test
	public void testFindByMobile() {
		User user1 = userDao.findByMobile("123");
		Assert.assertNotNull(user1);
		Assert.assertEquals(user1.getMobile(), "123");
	}

}
