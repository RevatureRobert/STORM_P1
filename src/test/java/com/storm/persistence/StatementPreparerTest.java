package com.storm.persistence;

import com.storm.model.TestClass1;
import org.junit.Test;

public class StatementPreparerTest {

    @Test
    public void prepareCreateSQL(){
        StatementPreparer sp = new StatementPreparer();
        TestClass1 tc1 = new TestClass1(1,"Brandon");
        System.out.println(sp.prepareSql(tc1,QueryType.CREATE));
    }
}
