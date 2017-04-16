package com.mservice.sdk.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Configuration;

import java.io.*;

/**
 * Created by jinz on 4/16/17.
 */

public class MainConfig {
    private static volatile MainConfig INSTANCE = null;
    private static JsonObject CONFIG_JSON;
    private MainConfig(){
    };
    public static MainConfig getInstance(){
        if(INSTANCE == null){
            INSTANCE = new MainConfig();
        }
        return INSTANCE;
    }

    public void loadConfig(String filePath){

        try {
            File file = new File(filePath);
            String json = FileUtils.readFileToString(file);
            CONFIG_JSON  = new JsonParser().parse(json).getAsJsonObject();
        }catch (IOException e){
            System.out.println("File not found...");
        }

    }


}