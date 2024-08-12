package org.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DynamoDBConfig {

    @Value("${aws.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint ;//= "http://localhost:8000";

    @Value("${aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${aws.secretkey}")
    private String amazonAWSSecretKey;

    @Bean
    public AwsBasicCredentials getAwsCredentials(){
        return AwsBasicCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean
    public DynamoDbClient dynamoDbClient() throws URISyntaxException {
        return DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .endpointOverride(URI.create("https://dynamodb.ap-south-1.amazonaws.com"))
                .credentialsProvider(StaticCredentialsProvider.create(getAwsCredentials()))
                .build();
    }

}