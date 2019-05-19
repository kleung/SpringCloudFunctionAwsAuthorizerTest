package com.test.TestAwsLambdaAuthorizer.cloudFunction;

import java.util.Map;
import java.util.function.Function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.test.TestAwsLambdaAuthorizer.model.AuthPolicy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component
public class AuthorizerFunction implements Function<APIGatewayProxyRequestEvent, AuthPolicy> {
	
	private static final Logger LOG = LoggerFactory.getLogger(AuthorizerFunction.class);
	
	private Context context;
	
	public AuthorizerFunction() {
		super();
	}

	@Autowired
	@Qualifier("targetExecutionContext")
	public void setContext(Context context) {
		LOG.info("context: " + context);
		this.context = context;
	}
	
	@Override
	public AuthPolicy apply(APIGatewayProxyRequestEvent event) {		
		String functionArn = context.getInvokedFunctionArn();
		LOG.info("ARN: {}", functionArn);
		
		String[] arnPartials = functionArn.split(":");
		String region = arnPartials[3];
		String awsAccountId = arnPartials[4];
		String[] apiGatewayArnPartials = arnPartials[5].split("/");
    	String restApiId = apiGatewayArnPartials[0];
    	String stage = apiGatewayArnPartials[1];
		
		AuthPolicy result = new AuthPolicy("fake-principal", 
												AuthPolicy.PolicyDocument.getDenyAllPolicy(region, 
																							awsAccountId, 
																							restApiId, 
																							stage));
		
		Map<String, String> headers = event.getHeaders();
		String apiKey = headers.get("x-api-key");
		LOG.info("api key: {}", apiKey);
		
		if((!StringUtils.isEmpty(apiKey)) && ("abcd1234".equalsIgnoreCase(apiKey))) {
			result = new AuthPolicy("fake-principal",
										AuthPolicy.PolicyDocument.getAllowAllPolicy(region, 
																						awsAccountId, 
																						restApiId, 
																						stage));
		}
				
		return result;
	}

}
