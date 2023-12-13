package com.aws.rest.config;

import com.aws.Credenciales;

import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

public class AmazonSNSService {
    private SnsClient snsClient;
    private final String TOPIC_ARN = "arn:aws:sns:us-east-1:691035830416:calificaciones-finales";

    public AmazonSNSService(){
        AwsSessionCredentials awsCredentials = AwsSessionCredentials.create(Credenciales.ACCESS_KEY, Credenciales.SECRET_KEY, Credenciales.SESSION_TOKEN);

        snsClient = SnsClient.builder().region(Region.US_EAST_1).credentialsProvider(StaticCredentialsProvider.create(awsCredentials)).build();
    }

    public SnsClient getSnsClient(){
        return snsClient;
    }

    public String getTopicARN(){
        return TOPIC_ARN;
    }
}
