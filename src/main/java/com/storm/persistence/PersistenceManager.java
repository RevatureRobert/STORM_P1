package com.storm.persistence;



import com.storm.util.TypeConversionUtil;

import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the database persistence for a given context
 *
 */
public class PersistenceManager {

    StatementPreparer<?> statementPreparer = new StatementPreparer<>();

    public void persist(Object o, Connection conn) throws Exception {
        String sql;
        PreparedStatement stmt;
        int success = 0;

        // if a table does not already exist for the entity
        createTable(o, conn);

        conn.setAutoCommit(false);
        Savepoint savepoint = conn.setSavepoint();

        sql = statementPreparer.prepareSql(o, QueryType.INSERT);
        stmt = statementPreparer.prepareStatement(o, conn, sql, QueryType.INSERT);
        success = stmt.executeUpdate();

        if(success == 0){
            conn.rollback(savepoint);
            throw new Exception("Could not add the desired entity: " + o);
        }

        conn.commit();
    }

    // TODO: show this off... its awesome
    public Object find(Object o, Connection conn) throws Exception{
        String sql;
        PreparedStatement stmt;

        sql = statementPreparer.prepareSql(o, QueryType.SELECT);
        stmt = statementPreparer.prepareStatement(o, conn, sql, QueryType.SELECT);

        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData rsmd = rs.getMetaData();

        Class[] parameters = new Class[rsmd.getColumnCount()];
        List<Object> values = new ArrayList<>();


        //TODO: create mapping for the parameters to their java object types
        while(rs.next()){
            for(int i = 1; i <= rsmd.getColumnCount(); i++){
                parameters[i-1] = TypeConversionUtil.convertFromSQL(rs.getObject(i).getClass());
                values.add(TypeConversionUtil.wrap(rs.getObject(i)));
            }
            Constructor<?> constructor = o.getClass().getConstructor(parameters);
            return constructor.newInstance(values.toArray());
        }
        return null;
    }

    public void remove(Object o, Connection conn) throws Exception {
        String sql;
        PreparedStatement stmt;
        int success = 0;

        // if a table does not already exist for the entity
        //if(!contains(o)) throw new Exception("Table does not exist for this entity");

        conn.setAutoCommit(false);
        Savepoint savepoint = conn.setSavepoint();

        sql = statementPreparer.prepareSql(o, QueryType.DELETE);
        stmt = statementPreparer.prepareStatement(o, conn, sql, QueryType.DELETE);
        success = stmt.executeUpdate();

        if(success == 0){
            conn.rollback(savepoint);
            throw new Exception("Could not removed the desired entity: " + o);
        }

        conn.commit();
    }

    private void createTable(Object o, Connection conn) throws IllegalAccessException, SQLException, NoSuchFieldException {
        statementPreparer.prepareStatement(o, conn,
                statementPreparer.prepareSql(o, QueryType.CREATE), QueryType.CREATE
        ).executeUpdate();
    }
}
