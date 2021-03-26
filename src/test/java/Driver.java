import com.storm.annotations.Entity;
import com.storm.annotations.Id;
import com.storm.config.StormConnectionProvider;
import com.storm.model.TestClass1;
import com.storm.persistence.EntityManager;
import com.storm.persistence.QueryType;
import com.storm.persistence.StatementPreparer;
import com.storm.persistence.User;
import com.storm.util.PropertiesLoader;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNull;

public class Driver {
    public static void main(String[] args) throws IOException, NoSuchFieldException, SQLException, IllegalAccessException {
        EntityManager entityManager = new EntityManager("h2.properties");
        User user = new User(1, "Brandon");
        //System.out.println(user.getClass().getDeclaredAnnotation(Entity.class));
        entityManager.persist(user);
        StormConnectionProvider cp = new StormConnectionProvider();
        cp.configure(PropertiesLoader.readPropertiesFileAsProperties("h2.properties"));
        StatementPreparer<User> sp = new StatementPreparer<>();

        PreparedStatement stmt = sp.prepareStatement(user,cp.getConnection(),sp.prepareSql(user, QueryType.SELECT), QueryType.SELECT);
        ResultSet rs = stmt.executeQuery();
        List<Object> result = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();

        while(rs.next()){
            for (int i = 1; i <= md.getColumnCount(); i++){
                result.add(rs.getObject(i));
            }
        }
        System.out.println(result);

    }
    public static void printObject(Object o){
        System.out.println(o.getClass().getSimpleName());
    }
}
