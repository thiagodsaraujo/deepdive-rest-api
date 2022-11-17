package com.thg.springboot.deepdiverestapi.survey;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class SurveyResource {

    @Autowired
    SurveyService surveyService;

    // surveys
    @RequestMapping("/surveys")
    public List<Survey> retrieveAllSurveys(){
        return surveyService.retrieveAllSurveys();
    }

    @RequestMapping("/surveys/{surveyId}")
    public Survey retrieveAllSurveys(@PathVariable String surveyId){
        var survey = surveyService.retrieveSurveyById(surveyId);

        if (survey == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return survey;
    }

    @RequestMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveAllSurveysQuestions(@PathVariable String surveyId){
        List<Question> questions = surveyService.retrieveAllSurveysQuestions(surveyId);

        if (questions == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return questions;
    }


    @RequestMapping("/surveys/{surveyId}/questions/{questionsId}")
    public Question retrieveSpecifySurveyQuestion(
            @PathVariable String surveyId,
            @PathVariable String questionsId){


        Question question = surveyService.retrieveSpecifySurveyQuestion(surveyId, questionsId);

        if (question == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return question;
    }

}
