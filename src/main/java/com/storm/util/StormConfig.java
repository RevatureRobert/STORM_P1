package com.storm.util;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.util.Properties;


/**
 * StormConfig will hold an instance of a jdbc
 */

public class StormConfig {

    private String username;
    private String password;
    private String jdbcUrl;
    private String schema;

    private static Connection conn;
    private int maxConnections;

    private Properties dataSourceProperties;
    private boolean sealed;

    public StormConfig(){
        // default constructor will attempt to load properties manually from the "h2.properties" file
        dataSourceProperties = loadProperties();
    }

    public StormConfig(Properties properties){
        dataSourceProperties = properties;
        setTargetFromProperties(properties);
    }

    public StormConfig(String fileName){
        dataSourceProperties = loadProperties(fileName);
    }


    private void setTargetFromProperties(Properties properties){
        if(properties == null) return;

        properties.forEach((key,value) ->{
            switch(key.toString()){
                case "username": this.username = value.toString();
                    break;
                case "password": this.password = value.toString();
                    break;
                case "jdbcUrl": this.jdbcUrl = value.toString();
                    break;
            }
        });
    }

    private Properties loadProperties(){
        try(InputStream input = new FileInputStream("h2.properties")){
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private Properties loadProperties(String fileName){
        try(InputStream input = new FileInputStream(fileName)){
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public void copyStateTo(StormConfig other){
        for (Field field : StormConfig.class.getDeclaredFields()) {
            if (!Modifier.isFinal(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    field.set(other, field.get(this));
                }
                catch (Exception e) {
                    throw new RuntimeException("Failed to copy HikariConfig state: " + e.getMessage(), e);
                }
            }
        }

        other.sealed = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public static Connection getConn() {
        return conn;
    }

    public static void setConn(Connection conn) {
        StormConfig.conn = conn;
    }

    public Properties getDataSourceProperties() {
        return dataSourceProperties;
    }

    public void setDataSourceProperties(Properties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }
}
