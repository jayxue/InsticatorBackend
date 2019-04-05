package com.insticator.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.insticator.backend.dto.AnswerExtensionDTO;
import com.insticator.backend.dto.QuestionAnswerDTO;
import com.insticator.backend.dto.QuestionDTO;
import com.insticator.backend.dto.QuestionResponseDTO;
import com.insticator.backend.model.AnswerExtension;
import com.insticator.backend.model.Question;
import com.insticator.backend.model.QuestionAnswer;
import com.insticator.backend.model.QuestionResponse;
import com.insticator.backend.model.Site;
import com.insticator.backend.model.User;
import com.insticator.backend.repository.AnswerExtensionRepository;
import com.insticator.backend.repository.QuestionAnswerRepository;
import com.insticator.backend.repository.QuestionRepository;
import com.insticator.backend.repository.QuestionResponseRepository;
import com.insticator.backend.repository.SiteRepository;
import com.insticator.backend.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private SiteRepository siteRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private QuestionAnswerRepository qaRepository;
	
	@Autowired
	private AnswerExtensionRepository aeRepository;
	
	@Autowired
	private QuestionResponseRepository qrRepository;
	
	private String userUUID1 = "d276d1b1-df63-494a-848b-787cf4174c33";
	private String userUUID2 = "0b0222f8-35f5-425b-9425-0957f7565fed";
	private String nonExistingUserUUID = "679b9b3d-15a0-4fab-a249-12c1d0671ec5";
	private String nonExistingSiteUUID = "d276d1b1-df63-494a-848b-787cf4174c33";
	
	@Before
	public void setUp() {
		// Prepare one site
		Site site = new Site();
		site.setUrl("www.foo.com");
		ResponseEntity<Site> siteResponseEntity = restTemplate.postForEntity("/sites", site, Site.class);
		Site createdSite = siteResponseEntity.getBody();
		
		// Prepare three questions
		QuestionDTO question = new QuestionDTO();
		question.setQuestion("Q1");
		question.setSiteId(createdSite.getSiteId());
		ResponseEntity<Question> questionResponseEntity = restTemplate.postForEntity("/questions", question, Question.class);
		Question createdQuestion1 = questionResponseEntity.getBody();

		question = new QuestionDTO();
		question.setQuestion("Q2");
		question.setSiteId(createdSite.getSiteId());
		questionResponseEntity = restTemplate.postForEntity("/questions", question, Question.class);
		Question createdQuestion2 = questionResponseEntity.getBody();
		
		question = new QuestionDTO();
		question.setQuestion("Q3");
		question.setSiteId(createdSite.getSiteId());
		questionResponseEntity = restTemplate.postForEntity("/questions", question, Question.class);
		Question createdQuestion3 = questionResponseEntity.getBody();
		
		// Prepare two non-matrix answers for question #1
		QuestionAnswerDTO answer = new QuestionAnswerDTO();
		answer.setAnswer("A11");
		answer.setIsCorrectAnswer(false);
		answer.setQuestionId(createdQuestion1.getQuestionId());
		ResponseEntity<QuestionAnswer> questionAnswerResponseEntity = restTemplate.postForEntity("/questions/" + createdQuestion1.getQuestionId() + "/answers", answer, QuestionAnswer.class);

		answer = new QuestionAnswerDTO();
		answer.setAnswer("A12");
		answer.setIsCorrectAnswer(true);
		answer.setQuestionId(createdQuestion1.getQuestionId());
		questionAnswerResponseEntity = restTemplate.postForEntity("/questions/" + createdQuestion1.getQuestionId() + "/answers", answer, QuestionAnswer.class);
		
		// Prepare three non-matrix answers for question #2
		answer = new QuestionAnswerDTO();
		answer.setAnswer("A21");
		answer.setIsCorrectAnswer(true);
		answer.setQuestionId(createdQuestion2.getQuestionId());
		restTemplate.postForEntity("/questions/" + createdQuestion2.getQuestionId() + "/answers", answer, QuestionAnswer.class);

		answer = new QuestionAnswerDTO();
		answer.setAnswer("A22");
		answer.setIsCorrectAnswer(true);
		answer.setQuestionId(createdQuestion2.getQuestionId());
		restTemplate.postForEntity("/questions/" + createdQuestion2.getQuestionId() + "/answers", answer, QuestionAnswer.class);
		
		answer = new QuestionAnswerDTO();
		answer.setAnswer("A23");
		answer.setIsCorrectAnswer(false);
		answer.setQuestionId(createdQuestion2.getQuestionId());
		restTemplate.postForEntity("/questions/" + createdQuestion2.getQuestionId() + "/answers", answer, QuestionAnswer.class);
		
		// Prepare two matrix answers for question #3
		answer = new QuestionAnswerDTO();
		answer.setAnswer("A31");
		answer.setIsCorrectAnswer(false);
		answer.setQuestionId(createdQuestion3.getQuestionId());
		questionAnswerResponseEntity = restTemplate.postForEntity("/questions/" + createdQuestion3.getQuestionId() + "/answers", answer, QuestionAnswer.class);
		QuestionAnswer createdAnswer6 = questionAnswerResponseEntity.getBody();

		AnswerExtensionDTO answerExtension = new AnswerExtensionDTO();
		answerExtension.setAnswerId(createdAnswer6.getAnswerId());
		answerExtension.setExtension("Ext1");
		answerExtension.setIsCorrectExtension(false);
		restTemplate.postForEntity("/answers/" + createdAnswer6.getAnswerId() + "/extensions", answerExtension, AnswerExtension.class);
		answerExtension.setExtension("Ext2");
		restTemplate.postForEntity("/answers/" + createdAnswer6.getAnswerId() + "/extensions", answerExtension, AnswerExtension.class);
		
		answer = new QuestionAnswerDTO();
		answer.setAnswer("A32");
		answer.setIsCorrectAnswer(false);
		answer.setQuestionId(createdQuestion3.getQuestionId());
		questionAnswerResponseEntity = restTemplate.postForEntity("/questions/" + createdQuestion3.getQuestionId() + "/answers", answer, QuestionAnswer.class);
		QuestionAnswer createdAnswer7 = questionAnswerResponseEntity.getBody();
		
		answerExtension = new AnswerExtensionDTO();
		answerExtension.setAnswerId(createdAnswer7.getAnswerId());
		answerExtension.setExtension("Ext3");
		answerExtension.setIsCorrectExtension(false);
		restTemplate.postForEntity("/answers/" + createdAnswer7.getAnswerId() + "/extensions", answerExtension, AnswerExtension.class);
		answerExtension.setExtension("Ext4");
		restTemplate.postForEntity("/answers/" + createdAnswer7.getAnswerId() + "/extensions", answerExtension, AnswerExtension.class);
	}
	
	@Test
	public void fetchQuestionTest() {
		Site site = siteRepository.findAll().get(0);
		
		List<Question> questions = questionRepository.findAll();
		Question question1 = questions.get(0);
		Question question2 = questions.get(1);
		Question question3 = questions.get(2);

		// Test invalid path
		ResponseEntity<Question> fetchedQuestionResponseEntity = restTemplate.getForEntity("/fetchquestionnnnnnn", Question.class);
		assertEquals(HttpStatus.NOT_FOUND, fetchedQuestionResponseEntity.getStatusCode());
		
		// Test missing mandatory parameter site_uuid
		fetchedQuestionResponseEntity = restTemplate.getForEntity("/fetchquestion", Question.class);
		assertEquals(HttpStatus.BAD_REQUEST, fetchedQuestionResponseEntity.getStatusCode());

		// Test missing mandatory parameter user_uuid
		fetchedQuestionResponseEntity = restTemplate.getForEntity("/fetchquestion?site_uuid=" + site.getSiteUUID(), Question.class);
		assertEquals(HttpStatus.BAD_REQUEST, fetchedQuestionResponseEntity.getStatusCode());

		// Test empty parameter values
		fetchedQuestionResponseEntity = restTemplate.getForEntity("/fetchquestion?site_uuid=&user_uuid=", Question.class);
		assertEquals(HttpStatus.NOT_FOUND, fetchedQuestionResponseEntity.getStatusCode());

		// Test missing equal sign
		fetchedQuestionResponseEntity = restTemplate.getForEntity("/fetchquestion?site_uuid&user_uuid", Question.class);
		assertEquals(HttpStatus.NOT_FOUND, fetchedQuestionResponseEntity.getStatusCode());

		// Test invalid site UUID
		fetchedQuestionResponseEntity = restTemplate.getForEntity("/fetchquestion?site_uuid=invalid_uuid&user_uuid=" + userUUID1, Question.class);
		assertEquals(HttpStatus.BAD_REQUEST, fetchedQuestionResponseEntity.getStatusCode());
		
		// Test non-existing site UUID
		fetchedQuestionResponseEntity = restTemplate.getForEntity("/fetchquestion?site_uuid=" + nonExistingSiteUUID + "&user_uuid=" + userUUID1, Question.class);
		assertEquals(HttpStatus.NOT_FOUND, fetchedQuestionResponseEntity.getStatusCode());		
		
		// Test invalid user UUID. Note that we don't test "non-existing" user UUID since we'll store new user UUIDs
		fetchedQuestionResponseEntity = restTemplate.getForEntity("/fetchquestion?site_uuid=" + site.getSiteUUID() + "&user_uuid=invalid_uuid", Question.class);
		assertEquals(HttpStatus.BAD_REQUEST, fetchedQuestionResponseEntity.getStatusCode());

		// Fetch to get question #1 for a user
		String fetchQuestionForUser1 = "/fetchquestion?site_uuid=" + site.getSiteUUID() + "&user_uuid=" + userUUID1;
		fetchedQuestionResponseEntity = restTemplate.getForEntity(fetchQuestionForUser1, Question.class);
		assertEquals(HttpStatus.OK, fetchedQuestionResponseEntity.getStatusCode());
		Question fetchedQuestion = fetchedQuestionResponseEntity.getBody();
		assertEquals(question1.getQuestionId(), fetchedQuestion.getQuestionId());
		
		// For the same site and user, fetch again get question #2
		fetchedQuestionResponseEntity = restTemplate.getForEntity(fetchQuestionForUser1, Question.class);
		fetchedQuestion = fetchedQuestionResponseEntity.getBody();
		assertEquals(question2.getQuestionId(), fetchedQuestion.getQuestionId());
		
		// For the same site and user, fetch again to get question #3
		fetchedQuestionResponseEntity = restTemplate.getForEntity(fetchQuestionForUser1, Question.class);
		fetchedQuestion = fetchedQuestionResponseEntity.getBody();
		assertEquals(question3.getQuestionId(), fetchedQuestion.getQuestionId());
		
		// For the same site and user, fetch again. Back end will start over since all questions have been fetched
		fetchedQuestionResponseEntity = restTemplate.getForEntity(fetchQuestionForUser1, Question.class);
		fetchedQuestion = fetchedQuestionResponseEntity.getBody();
		assertEquals(question1.getQuestionId(), fetchedQuestion.getQuestionId());

		// Verify that only one user was saved
		List<User> users = userRepository.findAll();
		assertEquals(1, users.size());
		
		// For the same site and another user, fetch a question. The first one will be returned for the new user
		String fetchQuestionForUser2 = "/fetchquestion?site_uuid=" + site.getSiteUUID() + "&user_uuid=" + userUUID2;
		fetchedQuestionResponseEntity = restTemplate.getForEntity(fetchQuestionForUser2, Question.class);
		fetchedQuestion = fetchedQuestionResponseEntity.getBody();
		assertEquals(question1.getQuestionId(), fetchedQuestion.getQuestionId());

		// Verify that two users have been saved
		users = userRepository.findAll();
		assertEquals(2, users.size());
	}
	
	@Test
	public void respondNonMatrixQuestionTest() {
		User user = userRepository.findByUuid(UUID.fromString(userUUID1));

		List<Question> questions = questionRepository.findAll();
		Question question1 = questions.get(0);

		List<QuestionAnswer> answers = qaRepository.findQuestionAnswers(question1.getQuestionId());
		QuestionAnswer answer1 = answers.get(0);
		QuestionAnswer answer2 = answers.get(1);

		QuestionResponseDTO questionResponse = new QuestionResponseDTO();
		questionResponse.setAnswerId(answer1.getAnswerId());
		questionResponse.setUserId(user.getUserId());
		
		// Test invalid path
		ResponseEntity<QuestionResponse> questionResponseResponseEntity = restTemplate.postForEntity("/respondquestionnnnnn", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.NOT_FOUND, questionResponseResponseEntity.getStatusCode());		
		
		// Test missing mandatory parameter user_uuid
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, questionResponseResponseEntity.getStatusCode());		

		// Test missing mandatory parameter answer_id
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1, questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, questionResponseResponseEntity.getStatusCode());

		// Test invalid user_uuid
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=invalid_uuid&answer_id=1", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, questionResponseResponseEntity.getStatusCode());

		// Test non-existing user_uuid
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + nonExistingUserUUID + "&answer_id=1", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.NOT_FOUND, questionResponseResponseEntity.getStatusCode());

		// Test empty answer_id
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, questionResponseResponseEntity.getStatusCode());

		// Test non-numeric answer_id
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=anything", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, questionResponseResponseEntity.getStatusCode());

		// Test invalid answer_id
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=0", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.NOT_FOUND, questionResponseResponseEntity.getStatusCode());

		// Test responding an answer to a question
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=" + answer1.getAnswerId(), questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.CREATED, questionResponseResponseEntity.getStatusCode());
		
		// Verify that one response has been saved for the answer
		List<QuestionResponse> responses = qrRepository.findResponsesForAnswer(answer1.getAnswerId());
		assertEquals(1, responses.size());
		
		// Verify that the user made one response
		responses = qrRepository.findResponsesForUserAndAnswer(user.getUserId(), answer1.getAnswerId());
		assertEquals(1, responses.size());
		
		// Verify the response is as expected
		QuestionResponse response = responses.get(0);
		assertEquals(user.getUserId(), response.getUser().getUserId());
		assertEquals(answer1.getAnswerId(), response.getAnswer().getAnswerId());
		assertNull(response.getExtension());
		
		// Test responding another answer to the same question
		String respondAnswer2 = "/respondquestion?user_uuid=" + userUUID1 + "&answer_id=" + answer2.getAnswerId();
		questionResponseResponseEntity = restTemplate.postForEntity(respondAnswer2, questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.CREATED, questionResponseResponseEntity.getStatusCode());
		
		// Verify that one responses has been saved for the answer
		responses = qrRepository.findResponsesForAnswer(answer2.getAnswerId());
		assertEquals(1, responses.size());
		
		// Verify the response is as expected
		response = responses.get(0);
		assertEquals(user.getUserId(), response.getUser().getUserId());
		assertEquals(answer2.getAnswerId(), response.getAnswer().getAnswerId());
		assertNull(response.getExtension());
		
		// Verify that the user made one response
		responses = qrRepository.findResponsesForUserAndAnswer(user.getUserId(), answer1.getAnswerId());
		assertEquals(1, responses.size());
		
		// Test responding the same answer to the same question
		questionResponseResponseEntity = restTemplate.postForEntity(respondAnswer2, questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.CREATED, questionResponseResponseEntity.getStatusCode());

		// Verify that the user made two responses with the answer
		responses = qrRepository.findResponsesForUserAndAnswer(user.getUserId(), answer2.getAnswerId());
		assertEquals(2, responses.size());
	}
	
	@Test
	public void respondMatrixQuestionTest() {
		User user = userRepository.findByUuid(UUID.fromString(userUUID1));

		List<Question> questions = questionRepository.findAll();
		// Question #3 is matrix
		Question question3 = questions.get(2);
		
		List<QuestionAnswer> answers = qaRepository.findQuestionAnswers(question3.getQuestionId());
		
		QuestionAnswer answer1 = answers.get(0);
		List<AnswerExtension> extensions = aeRepository.findAnswerExtensions(answer1.getAnswerId());
		AnswerExtension extension1 = extensions.get(0);
		AnswerExtension extension2 = extensions.get(1);
		
		QuestionResponseDTO questionResponse = new QuestionResponseDTO();
		questionResponse.setAnswerId(answer1.getAnswerId());
		questionResponse.setExtensionId(extension1.getExtensionId());
		questionResponse.setUserId(user.getUserId());
		
		// Test incorrect parameter extension_id
		ResponseEntity<QuestionResponse> questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "answer_id=" + answer1.getAnswerId() + "&ext", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, questionResponseResponseEntity.getStatusCode());
 
		// Test non-numeric extension_id
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "answer_id=" + answer1.getAnswerId() + "&extention_id=anything", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, questionResponseResponseEntity.getStatusCode());
		
		// Test responding an extension to an answer		
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=" + answer1.getAnswerId() + "&extension_id=" + extension1.getExtensionId(), questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.CREATED, questionResponseResponseEntity.getStatusCode());
		
		// Verify that one responses has been saved for the answer
		List<QuestionResponse> responses = qrRepository.findResponsesForAnswer(answer1.getAnswerId());
		assertEquals(1, responses.size());
		
		// Verify the response is as expected
		QuestionResponse response = responses.get(0);
		assertEquals(user.getUserId(), response.getUser().getUserId());
		assertEquals(answer1.getAnswerId(), response.getAnswer().getAnswerId());
		assertEquals(extension1.getExtensionId(), response.getExtension().getExtensionId());
		
		// Test responding another extension to the same answer		
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=" + answer1.getAnswerId() + "&extension_id=" + extension2.getExtensionId(), questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.CREATED, questionResponseResponseEntity.getStatusCode());
		
		// Verify that two responses have been saved for the answer
		responses = qrRepository.findResponsesForAnswer(answer1.getAnswerId());
		assertEquals(2, responses.size());
		
		// Verify the response is as expected
		response = responses.get(1);
		assertEquals(user.getUserId(), response.getUser().getUserId());
		assertEquals(answer1.getAnswerId(), response.getAnswer().getAnswerId());
		assertEquals(extension2.getExtensionId(), response.getExtension().getExtensionId());

		// Test responding the same extension to the same answer		
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=" + answer1.getAnswerId() + "&extension_id=" + extension2.getExtensionId(), questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.CREATED, questionResponseResponseEntity.getStatusCode());

		// Verify that three responses have been saved for the answer
		responses = qrRepository.findResponsesForAnswer(answer1.getAnswerId());
		assertEquals(3, responses.size());
		
		// Invalid user_uuid and answer_id have been covered by respondNonMatrixQuestionTest. Test extension_id only here.
		// Test that the request would be rejected if an extension is expected but not provided
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=" + answer1.getAnswerId() + "&extension_id=", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, questionResponseResponseEntity.getStatusCode());
		
		// Test handling of non-numeric extension id
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=" + answer1.getAnswerId() + "&extension_id=invalid", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.BAD_REQUEST, questionResponseResponseEntity.getStatusCode());
		
		// Test handling of invalid extension id
		questionResponseResponseEntity = restTemplate.postForEntity("/respondquestion?user_uuid=" + userUUID1 + "&answer_id=" + answer1.getAnswerId() + "&extension_id=0", questionResponse, QuestionResponse.class);
		assertEquals(HttpStatus.NOT_FOUND, questionResponseResponseEntity.getStatusCode());
	}
}
