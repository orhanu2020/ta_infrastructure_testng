package org.cb.ta.tests.APITest.utils;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.Validatable;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpRequest;
import org.openqa.selenium.remote.http.HttpMethod;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Helpers {

    public static Object getDataFromPath(Response response, String jsonPathString) {
        JsonPath jsonPath = response.jsonPath();
        return jsonPath.get(jsonPathString);
    }

    public static ValidatableResponse generateRequest(String resource
            , HttpMethod requestmethod
            , boolean enableLog) throws IOException {
        RequestSpecification requestSpecification = given();
        if(enableLog) requestSpecification = requestSpecification.log().all();
        requestSpecification.header
                ("Authorization"
                        , "Bearer "
                                + ConfigurationManager.getInstance().getAccount().getToken());
        requestSpecification = requestSpecification.when();
        Response response = switch (requestmethod) {
            case GET -> requestSpecification.get(resource);
            default -> throw new IOException("Unimplemented HttpMethod");
        };
        ValidatableResponse validatableResponse = response.then();
        if(enableLog) validatableResponse.log().all();
        return validatableResponse;
    }

}
