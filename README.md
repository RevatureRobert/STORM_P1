# Project 1 - Custom Object Relational Mapping Framework

## Description

Your first project will be to create a custom object relational mapping (ORM) framework.
This framework will allow for a simplified and SQL-free interaction with the relational data source.
The requirements of the project are purposefully vague, the intention is to allow for you to be creative
in your implementation of this framework. There are many ways that this task can be approached,
and you are encouraged to explore existing Java ORM implementations in order to get some inspiration.
Some suggested features that your ORM can provide are:

1. provide developers the option of file-based and programmatic configuration of entities

2. Programmatic persistence of entities (basic CRUD support)

3. Basic transaction management (begin, commit, savepoint, rollback)

4. Connection pooling

5. Lightweight session creation

6. Session-based caching to minimize calls to the database

7. Multithreading support for executing queries

## Tech Stack
- [ ] Java 8
- [ ] JUnit
- [ ] Apache Maven
- [ ] PostGreSQL deployed on AWS RDS
- [ ] Git SCM (on GitHub)



# STORM
### Take Your Next Project By STORM

> * Systemic
> * Transactional
> * Object
> * Relational
> * Mapping



## Features
- Automatic connection/thread pooling built into the entity manager
- Simple and quick five-step configuration to begin persisting entities
- Transaction based sql queries abstracted away from the user
- Build on top of Hibernate and HikariCP interfaces for ease of portability

## Instructions
1. Package/install the jar and add the following maven dependencies
   ```
    <dependencies>
       <dependency>
           <groupId>org.storm</groupId>
           <artifactId>STORM</artifactId>
           <version>0.1</version>
       </dependency>
       <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>6.0.0.Alpha6</version>
        </dependency>
     </dependencies>
2. Create a .properties file with the following basic options to configure your connection provider
   - **jdbcUrl**=*url*
   - **username**=*username*
   - **password**=*password*
    ```
   #Example h2.properties file
   jdbcUrl=jdbc:h2:tcp://localhost/~/test
   username=sa
   password=
3. Flag entities you want to manage with the **@Entity** annotation.
   - Managed Entities must be POJO objects
   - Entity Constructors must use Object/Wrapper types
   - You can set the desired schema in **@Entity(schema = "schemaName")**
   - You can flag a primary integer id with the **@Id** annotation
4. Create a new EntityManager object and pass the constructor your file path
   - the EntityManager will act as your custom profile for that properties file
   - it contains all of the connection/thread pooling
   - it will handle all of the mapping/caching for you
5. Begin persisting objects
   - calling **entityManager.persist(Object)** will begin managing the object for you
   - it will create the table if it does not already exist and add the object to the persistence context
   - for full documentation and guidelines, check the javaDoc


## Init Instructions
- Create a new repository within this organization (naming convention: `orm_name_p1`; with `orm_name` being replaced by the name of your custom library)

## Presentation
- [ ] finalized version of library must be pushed to personal repository within this organization by the presentation date (March 26th, 2021)
- [ ] 10-15 minute live demonstration of the implemented features using a demo application to showcase the ORM's functionality
