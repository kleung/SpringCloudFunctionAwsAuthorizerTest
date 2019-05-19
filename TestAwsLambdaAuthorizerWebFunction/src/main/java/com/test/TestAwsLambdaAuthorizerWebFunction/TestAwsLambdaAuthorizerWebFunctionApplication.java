package com.test.TestAwsLambdaAuthorizerWebFunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.function.context.FunctionRegistration;
import org.springframework.cloud.function.context.FunctionType;
import org.springframework.cloud.function.context.FunctionalSpringApplication;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.messaging.Message;

import com.test.TestAwsLambdaAuthorizerWebFunction.cloudFunction.HelloWorldFunction;

@SpringBootConfiguration
public class TestAwsLambdaAuthorizerWebFunctionApplication implements ApplicationContextInitializer<GenericApplicationContext> {

	public static void main(String[] args) {
		FunctionalSpringApplication.run(TestAwsLambdaAuthorizerWebFunctionApplication.class, args);
	}
	
	public HelloWorldFunction helloworld() {
		return new HelloWorldFunction();
	}
	
	@Override
	  public void initialize(GenericApplicationContext context) {
		context.registerBean("helloworld", FunctionRegistration.class,
		        () -> new FunctionRegistration<>(helloworld())
		            .type(FunctionType.from(String.class).to(String.class)));
	}
}
