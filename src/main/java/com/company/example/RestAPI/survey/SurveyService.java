package com.company.example.RestAPI.survey;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class SurveyService {

	private static List<Survey> surveys = new ArrayList<>();

	static {
		Question question1 = new Question("Question1", "Most Popular Cloud Platform Today",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
		Question question2 = new Question("Question2", "Fastest Growing Cloud Platform",
				Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
		Question question3 = new Question("Question3", "Most Popular DevOps Tool",
				Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

		List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

		Survey survey = new Survey("Survey1", "My Favorite Survey", "Description of the Survey", questions);

		surveys.add(survey);

	}

	public List<Survey> retrieveAllSurveys() {

		return surveys;
	}

	public Survey retrieveSurveyById(String surveyId) {
		// TODO Auto-generated method stub
		Predicate<? super Survey> predicate = survey -> survey.getId().equalsIgnoreCase(surveyId);
		Optional<Survey> optionalsurvey = surveys.stream().filter(predicate).findFirst();
		if (optionalsurvey.isEmpty())
			return null;
		return optionalsurvey.get();
	}

	public List<Question> retrieveSurveyQues(String surveyId) {
		// TODO Auto-generated method stub
		Survey survey = retrieveSurveyById(surveyId);
		if (survey == null)
			return null;
		return survey.getQuestions();
	}

	public Question retrieveSurveyQuesById(String surveyId, String questionId) {
		// TODO Auto-generated method stub
		Survey survey = retrieveSurveyById(surveyId);
		if (survey == null)
			return null;
		List<Question> questions = survey.getQuestions();
		Predicate<? super Question> predicate = question -> question.getId().equalsIgnoreCase(questionId);
		Optional<Question> optionalsurvey = questions.stream().filter(predicate).findFirst();
		if (optionalsurvey.isEmpty())
			return null;
		return optionalsurvey.get();

	}

	public String addNewSurveyQuestion(String surveyId, Question question) {
		// TODO Auto-generated method stub
		List<Question> questions = retrieveSurveyQues(surveyId);
		String randomId = generateRandomId();
		question.setId(randomId);
		questions.add(question);
		return question.getId();

	}

	private String generateRandomId() {
		SecureRandom secureRandom = new SecureRandom();
		String randomId = new BigInteger(32, secureRandom).toString();
		return randomId;
	}

	public void deleteSurveyQuestion(String surveyId, String questionId) {
		// TODO Auto-generated method stub
		List<Question> surveyQuestions = retrieveSurveyQues(surveyId);
		Predicate<? super Question> predicate = question -> question.getId().equalsIgnoreCase(questionId);
		surveyQuestions.removeIf(predicate);

	}

	public void updateSurveyQuestion(String surveyId, String questionId, Question question) {
		// TODO Auto-generated method stub
		List<Question> surveyQuestions = retrieveSurveyQues(surveyId);
		Predicate<? super Question> predicate = q -> q.getId().equalsIgnoreCase(questionId);
		surveyQuestions.removeIf(predicate);
		surveyQuestions.add(question);
	}
}
