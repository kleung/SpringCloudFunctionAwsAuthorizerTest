package com.test.TestAwsLambdaAuthorizer.eventHandler;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.test.TestAwsLambdaAuthorizer.model.AuthPolicy;

public class AuthorizerEventHandler extends SpringBootRequestHandler<APIGatewayProxyRequestEvent, AuthPolicy> {

}
