package com.heymom.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.common.LoginRequired;
import com.heymom.backend.dto.APIResult;
import com.heymom.backend.dto.activity.ActivityAttendeeRecordDto;
import com.heymom.backend.dto.activity.ActivityDto;
import com.heymom.backend.dto.activity.ActivityFollowRecordDto;
import com.heymom.backend.entity.activity.ActivityAttendeeRecord;
import com.heymom.backend.service.ActivityAttendeeRecordService;
import com.heymom.backend.service.ActivityFollowRecordService;
import com.heymom.backend.service.ActivityService;

@Controller
@RequestMapping("/api/activity/")
public class ActivityAPIController {
	@Autowired
	private ActivityAttendeeRecordService activityAttendeeRecordService;
	@Autowired
	private ActivityFollowRecordService activityFollowRecordService;
	@Autowired
	private ActivityService activityService;

	@RequestMapping(value = "attend/{activityId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Integer> attend(@PathVariable Integer activityId, @RequestHeader("token") String userToken) {
		activityAttendeeRecordService.attend(activityId);
		return new APIResult<Integer>(0);
	}

	@RequestMapping(value = "cancel/{activityId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Integer> cancel(@PathVariable Integer activityId, @RequestHeader("token") String userToken) {
		activityAttendeeRecordService.cancel(activityId);
		return new APIResult<Integer>(0);
	}

	@RequestMapping(value = "cancelFollow/{activityId}", method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Integer> cancelFollow(@PathVariable Integer activityId, @RequestHeader("token") String userToken) {
		activityFollowRecordService.cancelFollow(activityId);
		return new APIResult<Integer>(0);
	}

	@RequestMapping(value = "check/{activityId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Map<String, Boolean>> check(@PathVariable Integer activityId,
			@RequestHeader("token") String userToken) {
		Map<String, Boolean> res = new HashMap<String, Boolean>();
		ActivityAttendeeRecord attend = activityAttendeeRecordService.findByUserIdAndActivityId(activityId);
		res.put("attend", attend != null);
		res.put("follow", activityFollowRecordService.findByUserIdAndActivityId(activityId) != null);
		res.put("feedback", attend != null && attend.getComments() != null);
		return new APIResult<Map<String, Boolean>>(res);
	}

	@RequestMapping(value = "countMyActivity", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Map<String, Long>> countMyActivity(@RequestHeader("token") String userToken) {
		Map<String, Long> ret = new HashMap<String, Long>();
		ret.put("myActivityCount", activityAttendeeRecordService.countMyActivity());
		return new APIResult<Map<String, Long>>(ret);
	}

	@RequestMapping(value = "countMyFollow", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Map<String, Long>> countMyFollow(@RequestHeader("token") String userToken) {
		Map<String, Long> ret = new HashMap<String, Long>();
		ret.put("myFollowCount", activityFollowRecordService.countMyFollow());
		return new APIResult<Map<String, Long>>(ret);
	}

	@RequestMapping(value = "feedback/{activityId}/{score}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Integer> feedback(@PathVariable Integer activityId, @PathVariable Integer score,
			@RequestBody Map<String, String> parameters, @RequestHeader("token") String userToken) {
		String comments = parameters.get("comments");
		if (comments.length() < 140 && comments.length() > 10) {
			throw new HeymomException(200003);
		}
		activityAttendeeRecordService.feedback(activityId, score, comments);
		return new APIResult<Integer>(0);
	}

	@RequestMapping(value = "follow/{activityId}", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Integer> follow(@PathVariable Integer activityId, @RequestHeader("token") String userToken) {
		activityFollowRecordService.follow(activityId);
		return new APIResult<Integer>(0);
	}

	@RequestMapping(value = "listAllAvailableActivities", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<List<ActivityDto>> listAllAvailableActivities() {
		return new APIResult<List<ActivityDto>>(activityService.listAvaliableActivities());
	}

	@RequestMapping(value = "listByIds/{ids}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<List<ActivityDto>> listByIds(@PathVariable List<Integer> ids) {
		return new APIResult<List<ActivityDto>>(activityService.listByIds(ids));
	}

	@RequestMapping(value = "listFeedBack/{activityId}", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public APIResult<Page<ActivityAttendeeRecordDto>> listFeedBack(@PathVariable Integer activityId,
			@RequestParam(defaultValue = "0") int currentPage, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "updateTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection) {
		return new APIResult<Page<ActivityAttendeeRecordDto>>(activityAttendeeRecordService.listFeedBack(activityId,
				currentPage, pageSize, sortProperty, sortDirection));
	}

	@RequestMapping(value = "listMyActivityAndFeedback", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Page<ActivityAttendeeRecordDto>> listMyActivityAndFeedback(
			@RequestParam(defaultValue = "0") int currentPage, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "updateTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection, @RequestHeader("token") String userToken) {
		return new APIResult<Page<ActivityAttendeeRecordDto>>(activityAttendeeRecordService.listMyActivityAndFeedback(
				currentPage, pageSize, sortProperty, sortDirection));
	}

	@RequestMapping(value = "listMyFollowed", method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	@LoginRequired
	public APIResult<Page<ActivityFollowRecordDto>> listMyFollowed(@RequestParam(defaultValue = "0") int currentPage,
			@RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "updateTime") String sortProperty,
			@RequestParam(defaultValue = "DESC") String sortDirection, @RequestHeader("token") String userToken) {

		return new APIResult<Page<ActivityFollowRecordDto>>(activityFollowRecordService.listMyFollowed(currentPage,
				pageSize, sortProperty, sortDirection));
	}
}
