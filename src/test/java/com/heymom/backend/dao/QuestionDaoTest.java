package com.heymom.backend.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heymom.backend.entity.customized.Question;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
public class QuestionDaoTest {
	@Autowired
	private OptionDao optionDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private UserDao userDao;

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testListAvaliableQuestions() {
		List<Question> ret = questionDao.findAvaliableQuestions();
		System.out.println(ret.size());
		System.out.println(ret.get(0).getOptions().size());
		System.out.println(ret.get(0).getOptions().get(0).getDecription());
		Assert.assertNotNull(ret);
	}

}
