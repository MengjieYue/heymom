package com.heymom.backend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heymom.backend.common.HeymomException;
import com.heymom.backend.dao.ActivityDao;
import com.heymom.backend.dao.CustomizedResultDao;
import com.heymom.backend.dao.KidDao;
import com.heymom.backend.dao.OptionDao;
import com.heymom.backend.dao.QuestionDao;
import com.heymom.backend.dto.customized.CustomizedResultDto;
import com.heymom.backend.dto.customized.QuestionDto;
import com.heymom.backend.entity.EntityStatus;
import com.heymom.backend.entity.activity.Activity;
import com.heymom.backend.entity.customized.CustomizedResult;
import com.heymom.backend.entity.customized.Option;
import com.heymom.backend.entity.customized.Question;
import com.heymom.backend.entity.user.Kid;
import com.heymom.backend.entity.user.User;
import com.heymom.backend.utils.DtoUtils;

@Service
public class CustomizedService {
	private static final int[] ACTIVITY_TYPE_MAPPING = { 2, 1, 0, 1, 0, 7, 6 };
	private static final String[] FIELD = { "语言", "数理逻辑", "视觉空间", "身体动觉", "音乐", "情绪智力", "人际交往" };
	private static final int QUESTION_EATH_TYPE_COUNT = 2;
	private static final int QUESTION_TOTAL_VALUE = 2;
	private static final int QUESTION_TYPE_COUNT = 7;

	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private CustomizedResultDao customizedResultDao;
	@Autowired
	private KidDao kidDao;
	@Autowired
	private OptionDao optionDao;
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private UserService userService;

	private Specification<Activity> activityBuildSpecification(final Long userId, final Long startTime,
			final Integer startAge, final Integer endAge, final Integer type, final String nameContent) {
		return new Specification<Activity>() {
			@Override
			public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.ge(root.get("status").as(Integer.class), 0));
				if (userId != null) {
					predicates.add(cb.equal(root.get("user").get("id").as(Long.class), userId));
				}
				if (startAge != null) {
					predicates.add(cb.lt(root.get("attendeeMinAge").as(Integer.class), startAge));
				}
				if (endAge != null) {
					predicates.add(cb.gt(root.get("attendeeMaxAge").as(Integer.class), endAge));
				}
				if (startTime != null) {
					predicates.add(cb.gt(root.get("startTime").as(Long.class), startTime));
				}
				if (type != null) {
					predicates.add(cb.equal(root.get("type").as(Integer.class), type));
				}
				if (nameContent != null) {
					predicates.add(cb.like(root.get("name").as(String.class), "%" + nameContent + "%"));
				}

				if (predicates.size() > 0) {
					cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
				} else {
					cq.where(cb.conjunction());
				}
				return cq.getRestriction();
			}
		};
	}

	@Transactional
	public Option addOption(Option entity) {
		return optionDao.save(entity);
	}

	@Transactional
	public Question addQuestion(QuestionDto questionDto) {
		return questionDao.save(questionDto.toEntity());
	}

	private Specification<Question> buildSpecification(final Long userId, final Integer gender, final Integer age,
			final Integer type, final String decription) {
		return new Specification<Question>() {
			@Override
			public Predicate toPredicate(Root<Question> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				predicates.add(cb.ge(root.get("status").as(Integer.class), 0));
				if (userId != null) {
					predicates.add(cb.equal(root.get("user").get("id").as(Long.class), userId));
				}
				if (type != null) {
					predicates.add(cb.equal(root.get("type").as(Integer.class), type));
				}
				if (gender != null) {
					predicates.add(cb.equal(root.get("gender").as(Integer.class), gender));
				}
				if (age != null) {
					predicates.add(cb.equal(root.get("age").as(Integer.class), age));
				}
				if (decription != null) {
					predicates.add(cb.like(root.get("decription").as(String.class), "%" + decription + "%"));
				}

				if (predicates.size() > 0) {
					cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
				} else {
					cq.where(cb.conjunction());
				}
				return cq.getRestriction();
			}
		};
	}

	/**
	 * Get kid age
	 *
	 * @param birthday
	 * @return age
	 */
	private int calculateAge(Date birthday) {
		if (birthday.after(new Date())) {
			throw new IllegalArgumentException("The birthDay is before Now. It's unbelievable!");
		}
		return (int) Math.ceil((double) (System.currentTimeMillis() - birthday.getTime()) / 31536000000l);

	}

	@Transactional
	public void deleteQuestion(Integer questionId) {
		Question entity = findById(questionId);
		entity.setStatus(EntityStatus.DELETEED.getValue());
	}

	@Transactional(readOnly = true)
	public Question findById(Integer questionId) {
		Question entity = questionDao.findOne(questionId);
		if (entity == null) {
			throw new HeymomException(600001);
		}
		return entity;
	}

	/**
	 * Customized Result
	 *
	 * @param answers
	 * @return Test Result
	 */
	private CustomizedResult generateResult(List<Option> options) {
		CustomizedResult result = new CustomizedResult();
		// List<Integer> values = new ArrayList<Integer>();
		StringBuilder values = new StringBuilder();
		int maxType = 0, minType = 0, maxValue = 0, minValue = 0, typeValue = 0;
		for (int i = 0; i < QUESTION_TYPE_COUNT; i++) {
			for (Option o : options) {
				if (o.getQuestion().getType() == i) {
					typeValue += o.getValue();
				}
			}
			values.append(Integer.valueOf(typeValue * 100 / QUESTION_TOTAL_VALUE) + ",");
			if (typeValue > maxValue) {
				maxValue = typeValue;
				maxType = i;
			} else if (typeValue <= minValue) {
				minType = typeValue;
				minType = i;
			}
			typeValue = 0;
		}
		result.setValues(values.toString());
		result.setRetult(String.format("您的孩子在%s方面表现很赞哦！可以往一下课程继续提高成为高手！", FIELD[maxType]));
		result.setAdvice(String.format("您的孩子在%s方面看起来还需要继续在努力！", FIELD[minType]));

		Pageable pageRequest = new PageRequest(0, 10, Direction.fromString("DESC"), "followCount");
		Page<Activity> questions = activityDao.findAll(
				activityBuildSpecification(null, System.currentTimeMillis(), null, null,
						ACTIVITY_TYPE_MAPPING[maxType], null), pageRequest);
		result.setUser(userService.getCurrentUser());
		if (questions.getContent() == null || questions.getContent().size() < 4) {
			questions = activityDao.findAll(
					activityBuildSpecification(null, System.currentTimeMillis(), null, null, null, null), pageRequest);
		}
		result.setRecommendation(questions.getContent());
		return result;
	}

	@Transactional(readOnly = true)
	public List<QuestionDto> listAvaliableQuestions() {
		return DtoUtils.questionDtoUtil.toDTO(questionDao.findAvaliableQuestions());
	}

	@Transactional(readOnly = true)
	public Page<QuestionDto> listAvaliableQuestions(int currentPage, int pageSize, String sortProperty,
			String sortDirection, Integer babyGender, Integer babyAge, Integer type, String decription) {

		Pageable pageRequest = new PageRequest(currentPage, pageSize, Direction.fromString(sortDirection), sortProperty);

		Page<Question> questions = questionDao.findAll(buildSpecification(null, null, null, type, null), pageRequest);
		return DtoUtils.questionDtoUtil.toDTO(questions, pageRequest);
	}

	@Transactional
	public List<QuestionDto> listQustions(Integer gender, Date birthday) {

		int age = calculateAge(birthday);
		if (age < 3 || age > 7) {
			throw new HeymomException(300004);
		}

		Kid entity = new Kid();
		entity.setGender(gender);
		entity.setBirthday(birthday);
		entity.setParent(userService.getCurrentUser());
		kidDao.save(entity);

		List<Question> questions = questionDao.findAvaliableQuestions();
		Random random = new Random();
		List<Question> outQuestionList = new ArrayList<Question>();
		List<Question> typeQuesiontsCount = new ArrayList<Question>();
		for (int type = 0; type < QUESTION_TYPE_COUNT; type++) {
			for (Question q : questions) {
				System.out.println("type : " + q.getType() + ",gender : " + q.getGender() + ",age : " + q.getAge());
				if (q.getType() == type && q.getAge() == age && q.getGender() == gender.intValue()) {
					typeQuesiontsCount.add(q);
				}
			}
			if (typeQuesiontsCount.size() < QUESTION_EATH_TYPE_COUNT) {
				throw new HeymomException(300001);
			}
			int index = random.nextInt(typeQuesiontsCount.size() - 1);
			if (index == typeQuesiontsCount.size() - 1) {
				outQuestionList.add(typeQuesiontsCount.get(0));
				outQuestionList.add(typeQuesiontsCount.get(typeQuesiontsCount.size() - 1));
			} else {
				outQuestionList.add(typeQuesiontsCount.get(index));
				outQuestionList.add(typeQuesiontsCount.get(index + 1));
			}
			questions.removeAll(typeQuesiontsCount);
			typeQuesiontsCount.clear();
		}
		return DtoUtils.questionDtoUtil.toDTO(outQuestionList);
	}

	@Transactional
	public CustomizedResultDto createCustomizedResult(List<Integer> optionIds) {
		Long userId = userService.getCurrentUser().getId();
		List<Option> options = optionDao.findByIds(optionIds);
		createUserOption(userId, options);
		CustomizedResult result = generateResult(options);
		customizedResultDao.save(result);
		return new CustomizedResultDto(result);
	}

	/**
	 * Save User Test Record
	 *
	 * @param userId
	 * @param parameters
	 */
	private void createUserOption(Long userId, List<Option> options) {
		User user = userService.getCurrentUser();
		for (Option option : options) {
			option.getSelectedUsers().add(user);
			optionDao.save(option);
		}
	}

}
