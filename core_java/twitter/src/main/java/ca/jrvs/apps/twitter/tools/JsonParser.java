package ca.jrvs.apps.twitter.tools;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

public class JsonParser {

    /**
     * Convert a Java object to a JSON String
     *
     * @param object input object
     * @return JSON String
     * @throws JsonProcessingException JSON exception
     */
    public static String toJson(Object object, boolean prettyJson, boolean includeNullValues)
            throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (!includeNullValues) {
            mapper.setSerializationInclusion((JsonInclude.Include.NON_NULL));
        }

        if (prettyJson) {
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
        }
        return mapper.writeValueAsString(object);
    }

    /**
     * Parse JSON string to an object
     *
     * @param json     JSON string
     * @param mapClass Map class
     * @param <T>      Type
     * @return object Java object
     * @throws IOException IO Exception
     */
    @SuppressWarnings("unchecked")
    public static <T> T toObjectFromJson(String json, Class<?> mapClass) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return (T) mapper.readValue(json, mapClass);

    }
}