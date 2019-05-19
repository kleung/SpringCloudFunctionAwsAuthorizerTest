package com.test.TestAwsLambdaAuthorizer.cloudFunction;

import java.util.function.Function;

public class TestFunction implements Function<String, String> {

	@Override
	public String apply(String t) {
		return t;
	}

}
