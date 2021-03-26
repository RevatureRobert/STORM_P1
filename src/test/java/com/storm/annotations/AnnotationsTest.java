package com.storm.annotations;

import com.storm.model.TestClass1;
import com.storm.model.TestClass2;
import com.storm.persistence.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnnotationsTest {

    @Test
    public void testtest(){
        User tc = new User(1, "value");
        System.out.println(tc.getClass().getDeclaredAnnotation(Entity.class));
    }

    @Test
    public void entityDefaultSchemaTest(){
        TestClass1 tc = new TestClass1(1, "value");
        String actual = tc.getClass().getDeclaredAnnotation(Entity.class).schema();
        String expected = "public";
        assertEquals(expected, actual);
    }

    @Test
    public void entitySchemaTest(){
        TestClass2 tc2 = new TestClass2(1, "something");
        String actual = tc2.getClass().getDeclaredAnnotation(Entity.class).schema();
        String expected = "tc2";
        assertEquals(expected, actual);
    }

    @Test
    public void idTest() throws NoSuchFieldException {
        TestClass1 tc = new TestClass1(1, "value");
        boolean actual = tc.getClass().getDeclaredField("id").getDeclaredAnnotation(Id.class).isPrimary();
        assertTrue(actual);
    }

    @Test
    public void notNullableTest() throws NoSuchFieldException {
        TestClass1 tc = new TestClass1(1, "value");
        boolean actual = tc.getClass().getDeclaredField("notNullable").getDeclaredAnnotation(NotNullable.class).isNullable();
        assertFalse(actual);
    }
}
