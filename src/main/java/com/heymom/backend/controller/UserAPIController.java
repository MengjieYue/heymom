package com.heymom.backend.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.heymom.backend.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.common.LoginRequired;
import com.heymom.backend.dto.APIResult;
import com.heymom.backend.dto.user.KidDto;
import com.heymom.backend.dto.user.UserInfoDto;
import com.heymom.backend.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/user/")
public class UserAPIController {
	@Autowired
	private UserService userService;

	@Value("${upload.dir}")
	private String uploadDIr;
	private SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Map<String, String>> createUser(@RequestBody Map<String, String> parameters) {
		if (parameters.get("mobile") == null || parameters.get("verificationCode") == null
				|| parameters.get("password") == null) {
			throw new HeymomException(100004);
		}
		String userToken = userService.createUser(parameters.get("mobile"), parameters.get("verificationCode"),
				parameters.get("password"));
		return generateTokenResult(userToken);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<String> upload(@RequestHeader("token") String userToken,
												 @RequestParam("file") MultipartFile file) {
		String filePath =  "/_upload_/" + df.format(new Date())+"/" + System.currentTimeMillis() + "_" + RandomUtil.generate6Int()+".jpg";
		try{
			File f = new File( uploadDIr + filePath);
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			file.transferTo(f);
		}catch (Exception e){
			e.printStackTrace();
		}
		return new APIResult<String>(filePath);
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<UserInfoDto> findUserInfo(@RequestHeader("token") String userToken) {
		return new APIResult<UserInfoDto>(userService.findUserInfo());
	}

	@RequestMapping(value = "/kid", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<KidDto> findUserKid(@RequestHeader("token") String userToken) {
		return new APIResult<KidDto>(userService.findUserKid());
	}

	private APIResult<Map<String, String>> generateTokenResult(String userToken) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("userToken", userToken);
		return new APIResult<Map<String, String>>(result);
	}

	@RequestMapping(value = "/login", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Map<String, String>> login(@RequestBody Map<String, String> parameters) {
		return generateTokenResult(userService.login(parameters.get("mobile"), parameters.get("password")));
	}

	@RequestMapping(value = "/refreshToken", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Map<String, String>> refreshToken(@RequestHeader("token") String userToken) {
		return generateTokenResult(userService.reLogin(userToken));
	}

	@RequestMapping(value = "sendMobileVerification", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Integer> sendMobileVerification(@RequestBody Map<String, String> parameters) {
		userService.sendMobileVerification(parameters.get("mobile"));
		return new APIResult<Integer>(0);
	}

	@RequestMapping(value = "updatePasword", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Integer> updatePasword(@RequestBody Map<String, String> parameters) {
		if (parameters.get("mobile") == null || parameters.get("verificationCode") == null
				|| parameters.get("password") == null) {
			throw new HeymomException(100004);
		}
		userService.changeUserPassword(parameters.get("mobile"), parameters.get("mobile"), parameters.get("password"));
		return new APIResult<Integer>(0);
	}

	@RequestMapping(value = "/userInfo", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Integer> updateUserInfo(@RequestBody UserInfoDto userInfo, @RequestHeader("token") String userToken) {
		userService.updateUserInfo(userInfo);
		return new APIResult<Integer>(0);
	}

	@RequestMapping(value = "/kid", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Integer> updateUserKid(@RequestBody KidDto kidDto, @RequestHeader("token") String userToken) {
		userService.updateUserKid(kidDto);
		return new APIResult<Integer>(0);
	}

	@RequestMapping(value = "verifyMobileVerification", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Boolean> verifyMobileVerification(@RequestBody Map<String, String> parameters) {
		if (parameters.get("mobile") == null || parameters.get("verificationCode") == null) {
			throw new HeymomException(100004);
		}
		Boolean result = userService.verifyMobileVerification(parameters.get("mobile"),
				parameters.get("verificationCode"));
		return new APIResult<Boolean>(result);
	}

}
