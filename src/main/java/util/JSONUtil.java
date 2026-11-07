package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JSONUtil {

    static ObjectMapper mapper = new ObjectMapper();


    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.activateDefaultTyping(typeValidator(), ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
    }

    /**
     * Polymorphic type handling is restricted to this project's own model classes and a few
     * plain JDK value/collection types. Unrestricted default typing lets whoever controls the
     * JSON instantiate arbitrary classes on the classpath (deserialization gadgets).
     */
    static PolymorphicTypeValidator typeValidator()
    {
        return BasicPolymorphicTypeValidator.builder()
                .allowIfSubType("shareablemerkletree.")
                .allowIfSubType("data.")
                .allowIfSubType("util.SymKeyStringTuple")
                .allowIfSubType("java.util.ArrayList")
                .allowIfSubType("java.util.LinkedList")
                .allowIfSubType("java.util.HashMap")
                .allowIfSubType("java.util.LinkedHashMap")
                .allowIfSubType("java.util.HashSet")
                .allowIfSubType("java.util.LinkedHashSet")
                .allowIfSubType(String.class)
                .allowIfSubType(Number.class)
                .allowIfSubType(Boolean.class)
                .build();
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
