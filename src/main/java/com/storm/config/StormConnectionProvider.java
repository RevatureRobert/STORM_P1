package com.storm.config;

import com.storm.util.StormConfig;
import com.storm.util.StormDataSource;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Stoppable;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

public class StormConnectionProvider implements ConnectionProvider, Configurable, Stoppable {

    private StormConfig scfg;
    private StormDataSource sds;

    private Connection activeConnection;
    private int locationIndex = -1;

    public StormConnectionProvider() {
        this.scfg = null;
        this.sds = null;
    }

    @Override
    public Connection getConnection() throws SQLException {
        for(int i = 0; i < StormDataSource.MAX_CONNECTIONS; i++){
            Connection conn = sds.getConnectionPool()[i];
            if(conn != null){
                activeConnection = conn;
                sds.getConnectionPool()[i] = null;
                locationIndex = i;
                return activeConnection;
            }
        }
        throw new RuntimeException("No active connections available");
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {
        sds.getConnectionPool()[locationIndex] = connection;
        activeConnection = null;
        locationIndex = -1;
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }


    public void configure(Properties props){
        //TODO: implement the scfg util and place it here to load from props
        this.scfg = StormConfigurationUtil.loadConfiguration(props);
        this.sds = new StormDataSource(this.scfg);
    }

    @Override
    public void configure(Map props){
        //TODO: implement the scfg util and place it here to load from props
        this.scfg = StormConfigurationUtil.loadConfiguration(props);
        this.sds = new StormDataSource(this.scfg);
    }

    @Override
    public void stop() {
        try {
            this.sds.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return ConnectionProvider.class.equals(aClass) || StormConnectionProvider.class.isAssignableFrom(aClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T unwrap(Class<T> aClass)
    {
        if ( ConnectionProvider.class.equals( aClass ) ||
                StormConnectionProvider.class.isAssignableFrom( aClass ) ) {
            return (T) this;
        }
        else if ( DataSource.class.isAssignableFrom( aClass ) ) {
            return (T) this.sds;
        }
        else {
            throw new UnknownUnwrapTypeException( aClass );
        }
    }

    /**
     * For unit testing purpose only
     * @return
     */
    public StormConfig getScfg() {
        return scfg;
    }

    public StormDataSource getSds() {
        return sds;
    }
}
