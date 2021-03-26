package com.storm.persistence;

import com.storm.annotations.Entity;
import com.storm.annotations.Id;
import com.storm.annotations.NotNullable;
import com.storm.mapping.Column;
import com.storm.mapping.Database;
import com.storm.mapping.Schema;
import com.storm.mapping.Table;

import java.lang.reflect.Field;

public class ContextManager {

    Database context;
    Schema schema;
    Table table;

    public ContextManager(Database context){
        this.context = context;
    }

    public void addEntityToContext(Object o){
        String schemaName = o.getClass().getDeclaredAnnotation(Entity.class).schema();
        String tableName = o.getClass().getSimpleName();
        Field[] fields = o.getClass().getDeclaredFields();

        setSchema(schemaName);
        setTable(tableName);
        setColumns(fields);
    }

    public void removeEntityFromContext(Object o){
        String schemaName = o.getClass().getDeclaredAnnotation(Entity.class).schema();
        String tableName = o.getClass().getSimpleName();
        setSchema(schemaName);
        table = schema.getTables().stream().filter(t -> t.getTableName().equals(tableName))
                .findFirst().orElse(null);
        if(table != null) schema.getTables().remove(table);
    }

    public boolean contains(Object o){
        String tableName = o.getClass().getSimpleName();
        String schemaName = o.getClass().getDeclaredAnnotation(Entity.class).schema();

        schema = context.getSchemas().stream().filter(s -> s.getSchemaName().equals(schemaName))
                .findFirst().orElse(null);
        if(schema == null) return false;

        return schema.getTables().stream().anyMatch(t -> t.getTableName().equals(tableName));
    }

    private void setSchema(String schemaName){
        // check if a schema exists
        if (!context.getSchemas().isEmpty()){
            // check if the schema exists in the context, if not, make one
            schema = context.getSchemas().stream().filter(s -> s.getSchemaName().equals(schemaName))
                    .findFirst().orElse(new Schema(schemaName));
        } else schema = new Schema(schemaName);

        // add or replace the schema in the context
        context.getSchemas().add(schema);
    }

    private void setTable(String tableName){
        // check if any table exists in the schema
        if (!schema.getTables().isEmpty()){
            // check if the table exists in the schema, if not, make one
            table = schema.getTables().stream().filter(t -> t.getTableName().equals(tableName))
                    .findFirst().orElse(new Table(tableName));
        } else table = new Table(tableName);

        // add or replace the schema in the context
        schema.getTables().add(table);
    }

    private void setColumns(Field[] fields){
        for(Field f : fields){
            String name;
            Class<?> dataType;
            boolean isPrimary = false;
            boolean isNullable = true;

            name = f.getName();
            dataType = f.getType();
            if(f.getDeclaredAnnotation(Id.class) != null)
                isPrimary = f.getDeclaredAnnotation(Id.class).isPrimary();
            if(f.getDeclaredAnnotation(NotNullable.class) != null)
                isNullable = f.getDeclaredAnnotation(NotNullable.class).isNullable();

            table.getColumns().add(new Column(name,dataType,isPrimary,isNullable));
        }
    }

}
