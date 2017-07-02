package com.refix.main.rest.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.refix.main.entity.Customer;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.io.IOException;


public class JsonUtils {
    private static GsonBuilder gsonBuilder;
    private static Gson gson;

    static {
        gsonBuilder = new GsonBuilder();
        gsonBuilder.addSerializationExclusionStrategy(getExclusionStrategy());
        gsonBuilder.registerTypeAdapterFactory(HibernateProxyTypeAdapter.FACTORY);
        gson = gsonBuilder.create();
    }

    private static ExclusionStrategy getExclusionStrategy(){
        return new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                return fieldAttributes.getAnnotation(GsonIgnore.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        };
    }


    public static String getJsonFromObject(Object object){
        return gson.toJson(object);
    }
}
