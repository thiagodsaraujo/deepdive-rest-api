package com.thg.springboot.deepdiverestapi;

import com.thg.springboot.deepdiverestapi.survey.Question;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    {"id":"Question1","description":"Most Popular Cloud Platform Today","options":["AWS","Azure","Google Cloud","Oracle Cloud"],"correctAnswer":"AWS"}
//[Content-Type:"application/json", 

    @Test
    void retrieveSpecifySurveyQuestion_basicScenario(){
        ResponseEntity<String> responseEntity = template.getForEntity(SPECIFIC_QUESTION_URL, String.class);
        System.out.println(responseEntity.getBody());
        System.out.println(responseEntity.getHeaders());
    }


}
