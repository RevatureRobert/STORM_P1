package com.storm.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesLoader {
    public static Map<String, String> readPropertiesFileAsMap(String fileName, String delimiter) throws IOException {
        Map<String, String> map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null){
            if(line.trim().length()==0) continue;
            if (line.charAt(0)=='#') continue;
            int delimPosition = line.indexOf(delimiter);
            String key = line.substring(0, delimPosition).trim();
            String value = line.substring(delimPosition+1);
            map.put(key,value);
        }
        reader.close();
        return map;
    }

    public static Properties readPropertiesFileAsProperties(String fileName) throws IOException {
        Properties properties = new Properties();
        InputStream input = new FileInputStream(fileName);
        properties.load(input);
        return properties;
    }
}
