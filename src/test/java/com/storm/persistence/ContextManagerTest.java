package com.storm.persistence;

import com.storm.mapping.Column;
import com.storm.mapping.Database;
import com.storm.mapping.Schema;
import com.storm.mapping.Table;
import com.storm.model.TestClass1;
import org.junit.Test;

import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.*;

public class ContextManagerTest {

    @Test
    public void addEntityToContextTest(){
        Database context = new Database("test");
        ContextManager cm = new ContextManager(context);
        TestClass1 tc1 = new TestClass1(1, "Brandon");
        assertEquals(0,context.getSchemas().size());
        cm.addEntityToContext(tc1);
        Set<Schema> schemas = context.getSchemas();
        assertEquals(1, schemas.size());
        Set<Table> tables = Objects.requireNonNull(schemas.stream().findFirst().orElse(null)).getTables();
        assertEquals(1, tables.size());
        Set<Column> columns = Objects.requireNonNull(tables.stream().findFirst().orElse(null)).getColumns();
        //TODO: figure out why this assertion doesn't work with 2 as the expected

        System.out.println(columns);
        assertEquals(2, columns.size());
    }
    @Test
    public void contextContainsEntityTest(){
        Database context = new Database("test");
        ContextManager cm = new ContextManager(context);
        TestClass1 tc1 = new TestClass1(1, "Brandon");
        cm.addEntityToContext(tc1);
        boolean actual = cm.contains(tc1);
        assertTrue(actual);
    }
    @Test
    public void contextDoesNotContainEntityTest(){
        Database context = new Database("test");
        ContextManager cm = new ContextManager(context);
        TestClass1 tc1 = new TestClass1(1, "Brandon");
        boolean actual = cm.contains(tc1);
        assertFalse(cm.contains(tc1));
    }
    @Test
    public void removeEntityFromContextTest(){
        Database context = new Database("test");
        ContextManager cm = new ContextManager(context);
        TestClass1 tc1 = new TestClass1(1, "Brandon");
        // add to context
        cm.addEntityToContext(tc1);
        // now remove from context
        cm.removeEntityFromContext(tc1);
        Set<Schema> schemas = context.getSchemas();
        Set<Table> tables = Objects.requireNonNull(schemas.stream().findFirst().orElse(null)).getTables();
        tables = Objects.requireNonNull(schemas.stream().findFirst().orElse(null)).getTables();
        assertEquals(0,tables.size());
    }
}
