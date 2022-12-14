package com.thg.springboot.deepdiverestapi.survey;


import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();

    static {

        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList(
                "Kubernetes", "Docker", "Terraform", "Azure DevOps"), "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);


    }

    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }

    public Survey retrieveSurveyById(String  surveyId){

        Predicate<? super Survey> predicate = survey -> survey.getId().equalsIgnoreCase(surveyId);
        Optional<Survey> optionalSurvey = surveys.stream().filter(predicate).findFirst();
        if (optionalSurvey.isEmpty()) return null;

        return optionalSurvey.get();

    }

    public List<Question> retrieveAllSurveysQuestions(String surveyId) {
        var survey = retrieveSurveyById(surveyId);
        if (survey==null) return null;

        return survey.getQuestions();

    }

    public Question retrieveSpecifySurveyQuestion(String surveyId, String questionsId) {
        var surveysQuestions = retrieveAllSurveysQuestions(surveyId);

        if (surveysQuestions==null) return null;

        var optionalQuestion =
                surveysQuestions.stream().filter(q -> q.getId().equalsIgnoreCase(questionsId)).findFirst();

        if (optionalQuestion.isEmpty()) return null;

        return optionalQuestion.get();
    }

    public String addNewSurveysQuestions(String surveyId, Question question) {
        var questionList = retrieveAllSurveysQuestions(surveyId);
        question.setId(generateRandomId());
        questionList.add(question);
        return question.getId();
    }

    private String generateRandomId() {
        SecureRandom secureRandom = new SecureRandom();
        var randomId = new BigInteger(32, secureRandom).toString();
        return randomId;
    }


    public String deleteSurveyQuestion(String surveyId, String questionsId) {
        var surveysQuestions = retrieveAllSurveysQuestions(surveyId);

        if (surveysQuestions==null) return null;

        Predicate<Question> questionPredicate = q -> q.getId().equalsIgnoreCase(questionsId);

        var removeIf = surveysQuestions.removeIf(questionPredicate);

        if (!removeIf) return null;

        return questionsId;
    }

    public void updateSurveyQuestion(String surveyId, String questionsId, Question question) {
        var surveysQuestions = retrieveAllSurveysQuestions(surveyId);
        Predicate<Question> questionPredicate = q -> q.getId().equalsIgnoreCase(questionsId);
        surveysQuestions.removeIf(questionPredicate);
        surveysQuestions.add(question);
    }
}
