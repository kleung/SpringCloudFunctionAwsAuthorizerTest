package com.test.TestAwsLambdaAuthorizerWebFunction.cloudFunction;

import java.util.function.Function;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class HelloWorldFunction implements Function<String, String> {

	@Override
	public String apply(String input) {
		return input;
	}

}
