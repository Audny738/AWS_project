package com.aws.rest.config;

import com.aws.Credenciales;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class DynamoDBConfiguration {

    private DynamoDbClient dynamoDbClient;
    private final String TABLE_NAME = "sesiones-alumnos";

    public DynamoDBConfiguration(){
        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(Credenciales.ACCESS_KEY, Credenciales.SECRET_KEY, Credenciales.SESSION_TOKEN);

        dynamoDbClient = DynamoDbClient.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();
    }

    public DynamoDbClient getDynamoClient(){
        return dynamoDbClient;
    }

    public String getTableName(){
        return TABLE_NAME;
    }
}
