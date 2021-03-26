package com.storm.persistence;

import com.storm.annotations.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Iterator;

public class StatementPreparer<T> {

    @SuppressWarnings("rawtypes")
    public PreparedStatement prepareStatement(Object t, Connection connection, String sql, QueryType queryType) throws SQLException, IllegalAccessException, NoSuchFieldException {
        PreparedStatement statement = connection.prepareStatement(sql);

        Field[] fields = t.getClass().getDeclaredFields();
        Iterator<Field> iterator = Arrays.stream(fields).iterator();
        int location = 1;

        switch(queryType){
            case INSERT: while(iterator.hasNext()) {
                    setStatementParameter(t, location, iterator.next(), statement);
                    location++; }
                break;
            case SELECT:
                for(Field f : fields){
                    if(f.getName().toLowerCase().equals("id")){
                        f.setAccessible(true);
                        statement.setInt(location, f.getInt(t));
                    }
                }
                break;
            case UPDATE: while(iterator.hasNext()) {
                setStatementParameter(t, location, iterator.next(), statement);
                location++; }
                for(Field f : fields){
                    if(f.getName().toLowerCase().equals("id")){
                        f.setAccessible(true);
                        statement.setInt(location, f.getInt(t));
                    }
                }
            case DELETE:
                for(Field f : fields){
                    if(f.getName().toLowerCase().equals("id")){
                        f.setAccessible(true);
                        statement.setInt(location, f.getInt(t));
                    }
                }
                break;
        }
        return statement;
    }

    public void setStatementParameter(Object t, int location, Field field, PreparedStatement stmt) throws IllegalAccessException, SQLException {
        field.setAccessible(true);
        switch(field.getType().getSimpleName()){
            case "byte":
            case "Byte":
                stmt.setByte(location, field.getByte(t));
                break;
            case "short":
            case "Short":
                stmt.setShort(location, field.getShort(t));
                break;
            case "int":
            case "Integer":
                stmt.setInt(location, field.getInt(t));
                break;
            case "long":
            case "Long":
                stmt.setLong(location, field.getLong(t));
                break;
            case "float":
            case "Float":
                stmt.setFloat(location, field.getFloat(t));
                break;
            case "double":
            case "Double":
                stmt.setDouble(location, field.getDouble(t));
                break;
            case "char":
            case "Char":
                stmt.setString(location, String.valueOf(field.getChar(t)));
                break;
            case "boolean":
            case "Boolean":
                stmt.setBoolean(location, field.getBoolean(t));
                break;
            case "String":
                stmt.setString(location, (String) field.get(t));
                break;
            default:
                break;
        }
    }

    public String prepareSql(Object t, QueryType queryType){
        StringBuilder query = new StringBuilder();
        String whereId = " where \"id\" = ?";
        query.append(queryType);
        switch (queryType){
            case INSERT:
                query.append(" into ");
                query.append(t.getClass().getSimpleName()).append("s");
                query.append(" values ");
                query.append(generateInsertParameters(t));
                break;
            case SELECT:
                query.append(" * from ");
                query.append(t.getClass().getSimpleName()).append(("s"));
                query.append(whereId);
                break;
            case UPDATE:
                query.append(" ").append(t.getClass().getSimpleName()).append("s");
                query.append(" SET ").append(generateUpdateParameters(t));
                query.append(whereId);
                break;
            case DELETE:
                query.append(" from ");
                query.append(t.getClass().getSimpleName()).append(("s"));
                query.append(whereId);
                break;
            case CREATE:
                query.append(" table if not exists ")
                        .append(t.getClass().getSimpleName()).append("s").append(" (")
                        .append(generateCreateParameters(t)).append(generateConstraints(t))
                        .append(");");
                break;
            case DROP:
            case ALTER:
                break;
        }
        return query.toString();
    }

    private String generateConstraints(Object t){
        StringBuilder constraints = new StringBuilder(", primary key(\"");
        Field[] fields = t.getClass().getDeclaredFields();

        for(int i = 0; i< fields.length; i++){
            if(fields[i].getDeclaredAnnotation(Id.class) != null){
                return constraints.append(fields[i].getName()).append("\")").toString();
            }
        }
        return "";
    }

    private String generateInsertParameters(Object t){
        StringBuilder parameters = new StringBuilder("(");
        Field[] fields = t.getClass().getDeclaredFields();
        Iterator<Field> iterator = Arrays.stream(fields).iterator();
        while(iterator.hasNext()){
            iterator.next();
            parameters.append("?");
            if(iterator.hasNext()) parameters.append(",");
        }
        parameters.append(")");
        return parameters.toString();
    }

    private String generateUpdateParameters(Object t){
        StringBuilder parameters = new StringBuilder();
        Field[] fields = t.getClass().getDeclaredFields();
        Iterator<Field> iterator = Arrays.stream(fields).iterator();
        while(iterator.hasNext()){
            Field field = iterator.next();
            parameters.append("\"").append(field.getName()).append("\"").append("=").append("?");
            if(iterator.hasNext()) parameters.append(",");
        }
        return parameters.toString();
    }

    private String generateCreateParameters(Object t){
        StringBuilder query = new StringBuilder();
        Field[] fields = t.getClass().getDeclaredFields();
        Iterator<Field> iterator = Arrays.stream(fields).iterator();
        while(iterator.hasNext()){
            Field field = iterator.next();
            switch(field.getType().getSimpleName()){
                case "byte":
                case "Byte":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" bit");
                    break;
                case "short":
                case "Short":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" smallint");
                    break;
                case "int":
                case "Integer":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" integer");
                    break;
                case "long":
                case "Long":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" bigint");
                    break;
                case "float":
                case "Float":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" decimal");
                    break;
                case "double":
                case "Double":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" double precision");
                    break;
                case "char":
                case "Char":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" char");
                    break;
                case "boolean":
                case "Boolean":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" boolean");
                    break;
                case "string":
                case "String":
                    query.append("\"").append(field.getName()).append("\"");
                    query.append(" text");
                    break;
                default:
                    query.append("\"").append(field.getName()).append("-id").append("\"");
                    query.append(" integer");
                    break;
            }
            if (iterator.hasNext()) query.append(",");
        }
        return query.toString();
    }
}
