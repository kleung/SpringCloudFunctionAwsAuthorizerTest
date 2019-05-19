package com.test.TestAwsLambdaAuthorizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.messaging.Message;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.test.TestAwsLambdaAuthorizer.cloudFunction.AuthorizerFunction;
import com.test.TestAwsLambdaAuthorizer.cloudFunction.TestFunction;
import com.test.TestAwsLambdaAuthorizer.model.AuthPolicy;

/*@Import({
	AuthorizerFunction.class
})

@SpringBootApplication*/
@SpringBootConfiguration
public class TestAwsLambdaAuthorizerApplication implements ApplicationContextInitializer<GenericApplicationContext> {
	
	public static void main(String[] args) {
		FunctionalSpringApplication.run(TestAwsLambdaAuthorizerApplication.class, args);
		//SpringApplication.run(TestAwsLambdaAuthorizerApplication.class, args);
	}

	//@Bean
	public AuthorizerFunction authorizer() {
		return new AuthorizerFunction();
	}
	
	//@Bean
	public TestFunction testFunction() {
		return new TestFunction();
	}
	
	
	
	@Override
	  public void initialize(GenericApplicationContext context) {
	    context.registerBean("authorizer", FunctionRegistration.class,
	        () -> 
	        	new FunctionRegistration<>(authorizer())
	            .type(FunctionType.from(APIGatewayProxyRequestEvent.class).to(AuthPolicy.class))
	        );
	    
	    context.registerBean("testFunction", FunctionRegistration.class,
		        () -> 
		        	new FunctionRegistration<>(testFunction())
		            .type(FunctionType.from(String.class).to(String.class))
		        );

	  }

}
