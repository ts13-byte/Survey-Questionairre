package com.company.example.RestAPI.survey;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class SurveyResource {
	@Autowired
	private SurveyService surveyService;

	@GetMapping("/surveys")
	public List<Survey> RetriveAllSurveys() {
		return surveyService.retrieveAllSurveys();
	}

	@GetMapping("/surveys/{surveyId}")
	public Survey RetrieveSurveyById(@PathVariable String surveyId) {
		Survey retrieveSurveyById = surveyService.retrieveSurveyById(surveyId);
		if (retrieveSurveyById == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return retrieveSurveyById;
	}

	@GetMapping("/surveys/{surveyId}/questions")
	public List<Question> RetrieveSurveyQuestions(@PathVariable String surveyId) {
		List<Question> questions = surveyService.retrieveSurveyQues(surveyId);
		if (questions == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return questions;
	}

	@GetMapping("/surveys/{surveyId}/questions/{QuestionId}")
	public Question RetrieveSurveyQuestionsById(@PathVariable String surveyId, @PathVariable String QuestionId) {
		Question question = surveyService.retrieveSurveyQuesById(surveyId, QuestionId);
		if (question == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return question;
	}

	@PostMapping("/surveys/{surveyId}/questions")
	public ResponseEntity<Object> AddNewSurveyQuestions(@PathVariable String surveyId, @RequestBody Question question) {

		String questionId = surveyService.addNewSurveyQuestion(surveyId, question);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{questionId}").buildAndExpand(questionId)
				.toUri();
		return ResponseEntity.created(location).build();

	}
	@DeleteMapping("/surveys/{surveyId}/questions/{QuestionId}")
	public ResponseEntity<Object> DeleteSurveyQuestionsById(@PathVariable String surveyId, @PathVariable String QuestionId) {
		 surveyService.deleteSurveyQuestion(surveyId, QuestionId);
		 return ResponseEntity.noContent().build();
		
	}
	@PutMapping("/surveys/{surveyId}/questions/{QuestionId}")
	public ResponseEntity<Object> UpdateSurveyQuestionsById(@PathVariable String surveyId, @PathVariable String QuestionId,@RequestBody Question question) {
		 surveyService.updateSurveyQuestion(surveyId, QuestionId,question);
		 return ResponseEntity.noContent().build();
		
	}

}
