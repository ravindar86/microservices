package com.microservice.productservice.errorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class MyErrorHandler implements ResponseErrorHandler {
    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println(response.getStatusCode());
        if(response.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR))
            System.out.println("Error Occurred during Microservice call");
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
     //   System.out.println(response.getStatusCode());
        return true;
    }
}
