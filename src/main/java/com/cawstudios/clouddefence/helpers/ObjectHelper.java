package com.cawstudios.clouddefence.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;

public class ObjectHelper {

    private ObjectHelper() {
    }

    public static String objectToString(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        StringWriter stringEmp = new StringWriter();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        try {
            objectMapper.writeValue(stringEmp, object);
        } catch (IOException e) {
            return "{}";
        }
        return stringEmp.toString();
    }

    public static Object stringToObject(String data) {
        try {
            ObjectMapper objectMapperResponse = new ObjectMapper();
            objectMapperResponse.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapperResponse.readValue(data, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
