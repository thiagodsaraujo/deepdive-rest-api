package com.thg.springboot.deepdiverestapi;

import com.thg.springboot.deepdiverestapi.survey.Question;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveySourceIT {

    //                http://localhost:8080/surveys/Survey1/questions/Question1
    String str = """
                {

                    "id": "Question1",
                        "description": "Most Popular Cloud Platform Today",
                        "options": [
                            "AWS",
                            "Azure",
                            "Google Cloud",
                            "Oracle Cloud"
              ],
                    "correctAnswer": "AWS"
                }
            """;

    @Autowired
    private TestRestTemplate template;

    private static String SPECIFIC_QUESTION_URL = "/surveys/Survey1/questions/Question1";

//
//[Content-Type:"application/json",

    @Test
    void retrieveSpecifySurveyQuestion_basicScenario() throws JSONException {
        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);

        String expectedResponse =
                """
                {"id":"Question1",
                "description":"Most Popular Cloud Platform Today",
                "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                "correctAnswer":"AWS"
                }
                """;

        JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(),false);
//        assertEquals(expectedResponse.trim(), responseEntity.getBody());
//
//        System.out.println(responseEntity.getBody());
//        System.out.println(responseEntity.getHeaders());
    }


}
