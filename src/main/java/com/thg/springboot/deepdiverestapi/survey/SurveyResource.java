package com.thg.springboot.deepdiverestapi.survey;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public Survey retrieveSurveyById(@PathVariable String surveyId){
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

    @RequestMapping(value = "/surveys/{surveyId}/questions",method = RequestMethod.POST )
    public ResponseEntity<Object> addNewSurveysQuestions(@PathVariable String surveyId,
                                                         @RequestBody Question question){
        String questionId = surveyService.addNewSurveysQuestions(surveyId,question);

        ///surveys/{surveyId}/add/questions/{questionId} vai retornar no id que criamos
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{questionId}").buildAndExpand(questionId).toUri();
        return ResponseEntity.created(location).build();

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

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionsId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestion(
            @PathVariable String surveyId,
            @PathVariable String questionsId){
        surveyService.deleteSurveyQuestion(surveyId, questionsId);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionsId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSurveyQuestion(
            @PathVariable String surveyId,
            @PathVariable String questionsId,
            @RequestBody Question question){

        surveyService.updateSurveyQuestion(surveyId,  questionsId, question);
        return ResponseEntity.accepted().build();
    }



}
