package com.heymom.backend.dao;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.heymom.backend.entity.Delivery;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
public class DeliveryDaoTest {
	@Autowired
	private DeliveryDao deliveryDao;

	@Before
	public void setUp() throws Exception {
		Delivery d1 = new Delivery();
		d1.setId(1);
		d1.setName("test1");
		d1.setHotPriority(3);
		deliveryDao.save(d1);
		Delivery d2 = new Delivery();
		d2.setId(2);
		d2.setName("test2");
		d2.setHotPriority(2);
		deliveryDao.save(d2);
		Delivery d3 = new Delivery();
		d3.setId(3);
		d3.setName("test3");
		deliveryDao.save(d3);
	}

	@After
	public void tearDown() throws Exception {
		deliveryDao.deleteAll();
	}

	@Test
	public void testFindByHotPriority() {
		Pageable pageRequest = new PageRequest(0, 2, Sort.Direction.DESC, "hotPriority", "createTime");
		Page<Delivery> ret = deliveryDao.findAll(pageRequest);
		Assert.assertNotNull(ret);
		Assert.assertEquals(2, ret.getNumberOfElements());
	}

	@Test
	public void testFindByIds() {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		List<Delivery> ret = deliveryDao.findByIds(ids);
		Assert.assertNotNull(ret);
		Assert.assertEquals(3, ret.size());
	}

}
