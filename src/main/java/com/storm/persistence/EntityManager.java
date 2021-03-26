package com.storm.persistence;

import com.storm.config.StormConnectionProvider;
import com.storm.mapping.Database;
import com.storm.util.PropertiesLoader;

import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import java.io.IOException;

public class EntityManager implements javax.persistence.EntityManager {



    StormConnectionProvider connectionProvider = new StormConnectionProvider();

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
        try {
            persistenceManager.persist(o, connectionProvider.getConnection());
            contextManager.addEntityToContext(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Merge the state of the given entity into the current persistence context
    @Override
    public <T> T merge(T t) {
        return null;
    }

    // Remove the entity instance
    @Override
    public void remove(Object o) {
        try{
            persistenceManager.remove(o, connectionProvider.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Find by primary key
    @Override
    public <T> T find(Class<T> aClass, Object o) {
        try {
            return (T) persistenceManager.find(o, connectionProvider.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Returns an instance which is lazily fetched
    @Override
    public <T> T getReference(Class<T> aClass, Object o) {
        return null;
    }

    // Synchronizes the persistence context with the database
    @Override
    public void flush() {

    }


    @Override
    public void setFlushMode(FlushModeType flushModeType) { }

    @Override
    public FlushModeType getFlushMode() {
        return null;
    }

    // Lock an entity instance that is contained in the context
    @Override
    public void lock(Object o, LockModeType lockModeType) {

    }

    // Refresh the state of the instance from the database
    @Override
    public void refresh(Object o) {

    }

    // Clear the persistence context causing all managed entities to become detached
    @Override
    public void clear() {

    }

    // Checks if the managed entity belongs to the current persistence context
    @Override
    public boolean contains(Object o) {
        // TODO: what should we check here?
        // if the entity is in the context || cache || persistence
        return contextManager.contains(o);
    }


    // Create an instance of Query for executing a Java Persistence query language statement
    @Override
    public Query createQuery(String s) {
        return null;
    }


    // Create an instance of Query for executing a Java Persistence named query language statement
    @Override
    public Query createNamedQuery(String s) {
        return null;
    }

    // Create an instance of Query for executing a native sql statement.
    @Override
    public Query createNativeQuery(String s) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s, Class aClass) {
        return null;
    }

    @Override
    public Query createNativeQuery(String s, String s1) {
        return null;
    }


    // Indicate to the entity manager that a JTA transaction is active.
    // This method should be called on a JTA application managed entity manager that was
    // created outside the scope of the active transaction to associate it with the current JTA transaction.
    @Override
    public void joinTransaction() {

    }

    // return the provider object for the entityManager.
    @Override
    public Object getDelegate() {
        return null;
    }


    // close an application-managed entityManager.
    @Override
    public void close() {

    }

    // determine if the entityManager is open.
    @Override
    public boolean isOpen() {
        return false;
    }


    // Return the resource-level EntityTransaction object.
    @Override
    public EntityTransaction getTransaction() {
        return null;
    }
}
