package com.storm.persistence;


import com.storm.config.StormConnectionProvider;
import com.storm.mapping.Database;
import com.storm.model.TestClass1;
import com.storm.model.TestClass2;
import com.storm.util.PropertiesLoader;
import org.junit.Test;

public class PersistenceManagerTest {



    @Test
    public void persistEntityTest() throws Exception {
        StormConnectionProvider cp = new StormConnectionProvider();
        cp.configure(PropertiesLoader.readPropertiesFileAsProperties("h2.properties"));
        Database context = new Database("test");
        PersistenceManager pm = new PersistenceManager();
        TestClass1 tc1 = new TestClass1(1, "Brandon");
        pm.persist(tc1,cp.getConnection());
    }

    @Test
    public void findEntityTest() throws Exception {
        StormConnectionProvider cp = new StormConnectionProvider();
        cp.configure(PropertiesLoader.readPropertiesFileAsProperties("h2.properties"));
        Database context = new Database("test");
        PersistenceManager pm = new PersistenceManager();
        ContextManager cm = new ContextManager(context);
        TestClass1 tc1 = new TestClass1(1, "Brandon");
        pm.find(tc1, cp.getConnection());
    }

    @Test
    public void removeEntityTest() throws Exception {
        StormConnectionProvider cp = new StormConnectionProvider();
        cp.configure(PropertiesLoader.readPropertiesFileAsProperties("h2.properties"));
        Database context = new Database("test");
        ContextManager cm = new ContextManager(context);
        PersistenceManager pm = new PersistenceManager();
        TestClass1 tc1 = new TestClass1(1, "Brandon");
        pm.remove(tc1,cp.getConnection());
    }


}
