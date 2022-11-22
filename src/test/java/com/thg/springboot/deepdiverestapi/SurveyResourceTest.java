package com.thg.springboot.deepdiverestapi;


import com.thg.springboot.deepdiverestapi.survey.Question;
import com.thg.springboot.deepdiverestapi.survey.SurveyResource;
import com.thg.springboot.deepdiverestapi.survey.SurveyService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


//Mock -> surveyService.retrieveSpecifySurveyQuestion(surveyId, questionsId);
    // Fire a request para esse url especifico -> /surveys/{surveyId}/questions/{questionsId}
    //http://localhost:8080/surveys/Survey1/questions/Question1 GET
    // E DEPOIS SE O RESULTADO é o ESPERADO

//SurveyResource
@WebMvcTest(controllers =  SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class SurveyResourceTest {

    // Não iniciará nenhum outro bean que nao esteja aqui presente, só testaremos esse
    // umas das vantagens de usar mock mvc unit test
    @MockBean
    private SurveyService surveyService;

    @Autowired
    private MockMvc mockMvc;

    private static String SPECIFIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions/Question1";

    private static String GENERIC_QUESTION_URL = "http://localhost:8080/surveys/Survey1/questions";

    // Como nao definimos os parametros no serviço vai retornar null, portanto um teste de 404
    @Test
    void retrieveSpecifySurveyQuestion_404Scenario() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);
        var mvcResult = mockMvc.perform(requestBuilder).andReturn();

        System.out.println("+++++++++++++++++++++++++  PRINT +++++++++++++++++++++++++");
        System.out.println(mvcResult.getResponse().getContentAsString());

        var status = mvcResult.getResponse().getStatus();
        System.out.println(status);

        if (status == HttpStatus.NOT_FOUND.value()) {
            System.out.println("VALOR 404");
        }

    }

    @Test
    void retrieveSpecificSurveyQuestion_basicScenario() throws Exception {
        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.get(SPECIFIC_QUESTION_URL).accept(MediaType.APPLICATION_JSON);


        Question question = new Question("Question1", "Most Popular Cloud Platform Today",
                Arrays.asList("AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

        when(surveyService.retrieveSpecifySurveyQuestion("Survey1", "Question1")).thenReturn(question);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        String expectedResponse = """
				{
				
					"id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"options":["AWS","Azure","Google Cloud","Oracle Cloud"],
					"correctAnswer":"AWS"
				
				}
						
				""";


        MockHttpServletResponse response = mvcResult.getResponse();

        assertEquals(200, response.getStatus());
        JSONAssert.assertEquals(expectedResponse, response.getContentAsString(), false);


    }

    //addNewSurveyQuestion
    //post
    @Test
    void addNewSurveyQuestion_basicScenario() throws Exception {

        String requestBody = """
				{
				  "description": "Your Favorite Language",
				  "options": [
				    "Java",
				    "Python",
				    "JavaScript",
				    "Haskell"
				  ],
				  "correctAnswer": "Java"
				}
			""";

        when(surveyService.addNewSurveysQuestions(anyString(),any())).thenReturn("SOME_ID");

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post(GENERIC_QUESTION_URL)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(requestBody).contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        String locationHeader = response.getHeader("Location");

        assertEquals(201, response.getStatus());
        assertTrue(locationHeader.contains("/surveys/Survey1/questions/SOME_ID"));

    }

}
