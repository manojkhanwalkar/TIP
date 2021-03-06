package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JSONUtil {

    static ObjectMapper mapper = new ObjectMapper();


    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.enableDefaultTyping();
    }


    public static String toJSON(Object object)
    {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Object fromJSON(String json, Class<?> clazz)
    {
        try {
            return mapper.readValue(json,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String toPrettyJSON(Object object)
    {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }


}
