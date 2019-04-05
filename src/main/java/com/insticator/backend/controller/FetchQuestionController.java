package com.insticator.backend.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insticator.backend.model.Question;
import com.insticator.backend.model.Site;
import com.insticator.backend.model.User;
import com.insticator.backend.repository.QuestionRepository;
import com.insticator.backend.repository.SiteRepository;
import com.insticator.backend.repository.UserRepository;

@RestController
@RequestMapping("/fetchquestion")
public class FetchQuestionController {

	// The map stores mappings between sites and users/question of the sites.
	// The inner map stores the displayed question for a user.
	private static Map<String, Map<String, Long>> displayedQuestionsForSitesAndUsers = new HashMap<>();

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	SiteRepository siteRepository;
	
	@Autowired
	UserRepository userRepository;

	/**
	 * Fetch one question for a given site and a given user. The same user may keep visiting the same site, therefore
	 * multiple questions may be fetched. The rule is to fetch questions one by one. The current solution uses a simple
	 * sequential algorithm. For example, for a given user on a given site, fetch question #1 first, next time fetch
	 * question #2 and so on. If the last question #N has been fetched, next time start over with #1.
	 * 
	 * When the given site UUID is invalid or no any question is available, the API will return 404 Not Found.
	 *  
	 * @param siteUUID the uuid indicating one specific site
	 * @param userId the uuid indicating one specific user
	 * @return response with a question
	 */
	@GetMapping()
	public ResponseEntity<Question> fetchQuestion(
			@RequestParam(value = "site_uuid", required = true) String siteUUID,
			@RequestParam(value = "user_uuid", required = true) String userUUID) {
		// No question can be fetched if input parameters are not right
		if(siteUUID == null || siteUUID.isEmpty() || userUUID == null || userUUID.isEmpty())
			return ResponseEntity.notFound().build();

		Site site = null;
		User user = null;
		try {
			site = siteRepository.findByUuid(UUID.fromString(siteUUID));
			user = userRepository.findByUuid(UUID.fromString(userUUID));
		} catch(IllegalArgumentException e) {
			// If site UUID or user UUID is invalid, no question could be found
			return ResponseEntity.badRequest().build();
		}

		// If site UUID is valid but not stored in the system, no question could be found
		if(site == null) return ResponseEntity.notFound().build();

		List<Question> questions = questionRepository.findSiteQuestions(Long.valueOf(site.getSiteId()));

		// No any question available for the site
		if(questions.size() == 0) return ResponseEntity.notFound().build();

		Map<String, Long> userQuestions = displayedQuestionsForSitesAndUsers.getOrDefault(siteUUID, new HashMap<String, Long>());
		Long lastDisplayedQuestionId = userQuestions.getOrDefault(userUUID, -1L);
		
		// Find the question to return
		Question question = null;
		if(lastDisplayedQuestionId > 0) {
			// A question has been displayed for the user. Check if there is next available question
			for(int i = 0; i < questions.size(); i++) {
				if(questions.get(i).getQuestionId() == lastDisplayedQuestionId) {
					if(i < questions.size() - 1) {
						// We assume the questions are ordered by ids. Next question is available
						question = questions.get(i + 1);
					} else {
						// The last question was fetched, so fetch the first question
						question = questions.get(0);						
					}
					break;
				}
			}
		} else {
			question = questions.get(0);			 
		}

		lastDisplayedQuestionId = question.getQuestionId();

		// Also save the user if not saved yet
		if(user == null) {
			user = new User();
			user.setUserUUID(UUID.fromString(userUUID));
			user.setCreatedAt(new Date());
			user.setSite(site);
			userRepository.save(user);
		}

		// Update mappings
		userQuestions.put(userUUID, lastDisplayedQuestionId);
		displayedQuestionsForSitesAndUsers.put(siteUUID, userQuestions);

		return  ResponseEntity.ok(question);
	}
}
