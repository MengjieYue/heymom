package com.heymom.backend.controller;

import java.io.File;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.heymom.backend.common.LoginRequired;
import com.heymom.backend.dto.APIResult;
import com.heymom.backend.utils.RandomUtil;

@Controller
@RequestMapping(value = "/backend/upload")
public class UploadController {

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired(authority = "backend_activity_list")
	public APIResult<String> upload(@RequestParam("file") MultipartFile file) {
		String filePath = "/_upload_/" + System.currentTimeMillis() + "_" + RandomUtil.generate6Int() + ".jpg";
		try {
			File f = new File("/mnt/resource" + filePath);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			file.transferTo(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new APIResult<String>(filePath);
	}
}
