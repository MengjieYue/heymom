package com.heymom.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.heymom.backend.dto.BatchRequest;
import com.heymom.backend.utils.JsonUtils;

public class TestEncrypt {

	@Test
	public void test() {
		// PBEWithMD5AndDES
		BasicTextEncryptor encryptor = new BasicTextEncryptor();
		encryptor.setPassword("heymom");
		String encrypted = encryptor.encrypt("57W?9d*!");
		System.out.println(encrypted);
		List<String> pList = new ArrayList<String>();
		pList.add("1");
		pList.add("2");
		System.out.println(pList.toString().substring(1, pList.toString().length() - 1));

		List<BatchRequest> rs = new ArrayList<BatchRequest>();
		BatchRequest r1 = new BatchRequest();
		r1.setMethod("listAllAvailableActivities");
		r1.setResource("ActivityAPIController");
		r1.setParams(null);
		rs.add(r1);
		BatchRequest r2 = new BatchRequest();
		r2.setMethod("listByIds");
		r2.setResource("ActivityAPIController");
		Map<String, Object> params = new HashMap<String, Object>();
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		params.put("ids", ids);
		r2.setParams(params);
		rs.add(r2);
		try {
			System.out.println(JsonUtils.toJson(rs));
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
