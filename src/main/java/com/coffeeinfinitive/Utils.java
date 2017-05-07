package com.coffeeinfinitive;

import com.coffeeinfinitive.dao.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by jinz on 5/4/17.
 */
public class Utils<F,T> {
    public ObjectMapper mapper = new ObjectMapper();
    private final Class<T> type;

    public Utils(Class<T> type) {
        this.type = type;
    }

    public T ConvertObject(F from){
        try{
            String json = mapper.writeValueAsString(from);
            return mapper.readValue(json, getType());
        }catch (JsonProcessingException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    };

    public Class<T> getType() {
        return type;
    }
}
