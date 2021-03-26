package com.storm.util;

import com.storm.util.StormConfig;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class StormDataSource extends StormConfig implements Closeable {
    public static final int MAX_CONNECTIONS = 4;
    private final Connection[] connectionPool = new Connection[MAX_CONNECTIONS];

    public StormDataSource() {
        generateConnectionPool();
    }

    public StormDataSource(StormConfig configuration){
        // StormDataSource extends StormConfig, we can copy the state over for each field.
        configuration.copyStateTo(this);
        generateConnectionPool();
    }

    private void generateConnectionPool(){
        for(int i = 0; i < MAX_CONNECTIONS; i++){
            connectionPool[i] = createConnection();
        } try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Connection createConnection() {
        Properties props = getDataSourceProperties();
        try {
            return DriverManager.getConnection(
                    props.getProperty("jdbcUrl"),
                    props.getProperty("username"),
                    props.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Connection[] getConnectionPool(){
        return connectionPool;
    }

    @Override
    public void close() throws IOException {
        for(Connection con: connectionPool){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
