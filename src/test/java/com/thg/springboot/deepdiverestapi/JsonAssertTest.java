package com.thg.springboot.deepdiverestapi;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JsonAssertTest {


    @Test
    void jsonAssert_learningBasics() throws JSONException {
        String expectedResponse =
                """
                {"id":"Question1","description":"Most Popular Cloud Platform Today",
                "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                "correctAnswer":"AWS"
                }
                """;

        String  actualResponse =
                """
                {
                "id":"Question1",
                "description":"Most Popular Cloud Platform Today",
                "options":["AWS","Azure","Google Cloud","Oracle Cloud"],
                "correctAnswer":"AWS"
                }
                """;
    // Com o JSONAssert voce pode mudar os jsons um pouco e ainda continua funcionando, se add espaços tbm funcionaria
        // e ajuda bastante onde está o erro, se é no id ou options por exemplo.
        // tbm da para definir a pririodade do que deve estar igual com a coparação se por exemplo excluirmos o options
        // e colocamos como false na condição, irá dar certo  o teste
        JSONAssert.assertEquals(expectedResponse,actualResponse, true);
    }
}
