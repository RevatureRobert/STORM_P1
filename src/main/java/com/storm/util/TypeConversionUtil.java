package com.storm.util;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.SQLException;

public class TypeConversionUtil {

    public static Class<?> convertFromSQL(Class<?> c){
        switch(c.getSimpleName()){
            case "boolean": return Boolean.class;
            case "byte": return Byte.class;
            case "short": return Short.class;
            case "int": return Integer.class;
            case "long": return Long.class;
            case "float": return Float.class;
            case "double": return Double.class;
            case "JdbcClob": return String.class;
            default: return c;
        }
    }

    public static Object wrap(Object o) throws IOException, SQLException {
        switch(o.getClass().getSimpleName()){
            case "boolean": return (Boolean) o;
            case "byte": return (Byte) o;
            case "short": return (Short) o;
            case "int": return (Integer) o;
            case "long": return (Long) o;
            case "float": return (Float) o;
            case "double": return (Double) o;
            case "JdbcClob": return clobToString((Clob) o);
            default: return o;
        }
    }

    public static Object clobToString(Clob c) throws SQLException, IOException {
        StringBuilder sb = new StringBuilder();
        Reader r = c.getCharacterStream();
        int ch;
        while((ch = r.read()) != -1){
            sb.append(""+(char)ch);
        }
        java.lang.String result = sb.toString();
        return result;

    }
}
