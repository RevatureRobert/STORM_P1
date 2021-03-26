package com.storm.persistence;

import com.storm.config.StormConnectionProvider;
import com.storm.mapping.Database;
import com.storm.util.PropertiesLoader;

import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class EntityManager implements javax.persistence.EntityManager {

    StormConnectionProvider connectionProvider = new StormConnectionProvider();
    ExecutorService executorService = Executors.newFixedThreadPool(5);

    Database context;
    PersistenceManager persistenceManager;
    ContextManager contextManager;
    CacheManager cacheManager;


    public EntityManager() throws IOException {
        connectionProvider.configure(PropertiesLoader.readPropertiesFileAsProperties("h2.properties"));
        context = new Database("default");
        persistenceManager = new PersistenceManager();
        contextManager = new ContextManager(context);
        cacheManager = new CacheManager(context);
    }

    public EntityManager(String filename) throws IOException {
        connectionProvider.configure(PropertiesLoader.readPropertiesFileAsProperties(filename));
        context = new Database("default");
        persistenceManager = new PersistenceManager();
        contextManager = new ContextManager(context);
        cacheManager = new CacheManager(context);
    }

    public EntityManager(String filename, String databaseName) throws IOException {
        connectionProvider.configure(PropertiesLoader.readPropertiesFileAsProperties(filename));
        context = new Database(databaseName);
        persistenceManager = new PersistenceManager();
        contextManager = new ContextManager(context);
        cacheManager = new CacheManager(context);
    }


    // Make an instance managed and persistent
    @Override
    public void persist(Object o) {
        Connection activeConnection = null;
        try{
            activeConnection = connectionProvider.getConnection();
            Connection finalActiveConnection = activeConnection;
            executorService.execute(() -> {
                try {
                    persistenceManager.persist(o, finalActiveConnection);
                    contextManager.addEntityToContext(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (activeConnection!= null){
                connectionProvider.closeConnection(activeConnection);
            }
        }
    }

    // Merge the state of the given entity into the current persistence context
    @Override
    public <T> T merge(T t) {
        Connection activeConnection = null;
        try{
            activeConnection = connectionProvider.getConnection();
            Connection finalActiveConnection = activeConnection;
            Future<Object> future = executorService.submit(() -> persistenceManager.merge(t, finalActiveConnection));
            return (T) future.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (activeConnection!= null){
                connectionProvider.closeConnection(activeConnection);
            }
        }
        return null;
    }

    // Remove the entity instance
    @Override
    public void remove(Object o) {
        Connection activeConnection = null;
        try{
            activeConnection = connectionProvider.getConnection();
            Connection finalActiveConnection = activeConnection;
            executorService.execute(() -> {
                try {
                    persistenceManager.remove(o, finalActiveConnection);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (activeConnection!= null){
                connectionProvider.closeConnection(activeConnection);
            }
        }
    }

    // Find by primary key
    @Override
    public <T> T find(Class<T> aClass, Object o) {
        Connection activeConnection = null;
        try{
            activeConnection = connectionProvider.getConnection();
            Connection finalActiveConnection = activeConnection;
            Future<Object> future = executorService.submit(() -> persistenceManager.find(o, finalActiveConnection));
            return (T) future.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (activeConnection!= null){
                connectionProvider.closeConnection(activeConnection);
            }
        }
        return null;
    }

    // Returns an instance which is lazily fetched
    @Override
    public <T> T getReference(Class<T> aClass, Object o) {
        throw new UnsupportedOperationException();
    }

    // Synchronizes the persistence context with the database
    @Override
    public void flush() {
        throw new UnsupportedOperationException();
    }


    @Override
    public void setFlushMode(FlushModeType flushModeType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public FlushModeType getFlushMode() {
        throw new UnsupportedOperationException();
    }

    // Lock an entity instance that is contained in the context
    @Override
    public void lock(Object o, LockModeType lockModeType) {
        throw new UnsupportedOperationException();
    }

    // Refresh the state of the instance from the database
    @Override
    public void refresh(Object o) {
        throw new UnsupportedOperationException();
    }

    // Clear the persistence context causing all managed entities to become detached
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    // Checks if the managed entity belongs to the current persistence context
    @Override
    public boolean contains(Object o) {
        Connection activeConnection = null;
        try{
            activeConnection = connectionProvider.getConnection();
            Connection finalActiveConnection = activeConnection;
            Future<Boolean> future = executorService.submit(() -> persistenceManager.contains(o, finalActiveConnection));
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if (activeConnection!= null){
                connectionProvider.closeConnection(activeConnection);
            }
        }
        return false;
    }


    // Create an instance of Query for executing a Java Persistence query language statement
    @Override
    public Query createQuery(String s) {
        throw new UnsupportedOperationException();
    }


    // Create an instance of Query for executing a Java Persistence named query language statement
    @Override
    public Query createNamedQuery(String s) {
        throw new UnsupportedOperationException();
    }

    // Create an instance of Query for executing a native sql statement.
    @Override
    public Query createNativeQuery(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query createNativeQuery(String s, Class aClass) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query createNativeQuery(String s, String s1) {
        throw new UnsupportedOperationException();
    }


    // Indicate to the entity manager that a JTA transaction is active.
    // This method should be called on a JTA application managed entity manager that was
    // created outside the scope of the active transaction to associate it with the current JTA transaction.
    @Override
    public void joinTransaction() {
        throw new UnsupportedOperationException();
    }

    // return the provider object for the entityManager.
    @Override
    public Object getDelegate() {
        throw new UnsupportedOperationException();
    }


    // close an application-managed entityManager.
    @Override
    public void close() {
        throw new UnsupportedOperationException();
    }

    // determine if the entityManager is open.
    @Override
    public boolean isOpen() {
        throw new UnsupportedOperationException();
    }


    // Return the resource-level EntityTransaction object.
    @Override
    public EntityTransaction getTransaction() {
        throw new UnsupportedOperationException();
    }
}
