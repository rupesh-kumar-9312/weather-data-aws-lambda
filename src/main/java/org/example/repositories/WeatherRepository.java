package org.example.repositories;
import org.example.models.WeatherData;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
public class WeatherRepository {

    private final DynamoDbClient dynamoDbClient;

    public WeatherRepository(DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    public void saveWeatherData(WeatherData weatherData) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("Id", AttributeValue.builder().s(UUID.randomUUID().toString()).build());
        item.put("City", AttributeValue.builder().s(weatherData.getCity()).build());
        item.put("Description", AttributeValue.builder().s(weatherData.getDescription()).build());
        item.put("Temperature", AttributeValue.builder().n(String.valueOf(weatherData.getTemperature())).build());
        item.put("Humidity", AttributeValue.builder().n(String.valueOf(weatherData.getHumidity())).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName("WeatherData")
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
    }
}
