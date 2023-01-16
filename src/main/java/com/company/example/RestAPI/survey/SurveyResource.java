package com.company.example.RestAPI.survey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class SurveyResource {
	@Autowired
	private SurveyService surveyService;

	@RequestMapping("/surveys")
	public List<Survey> RetriveAllSurveys() {
		return surveyService.retrieveAllSurveys();
	}

	@RequestMapping("/surveys/{surveyId}")
	public Survey RetrieveSurveyById(@PathVariable String surveyId) {
		Survey retrieveSurveyById = surveyService.retrieveSurveyById(surveyId);
		if (retrieveSurveyById == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return retrieveSurveyById;
	}
}
