/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Tran Dong
 */
public class DBUtilities {

    private static EntityManagerFactory emf;
    private static final Object LOCK = new Object();

    public static EntityManager getEntityManager() {
        synchronized (LOCK) {
            if (emf == null) {
                try {
                    emf = Persistence.createEntityManagerFactory("FoodSupplementPU");
                } catch (Exception e) { 
                    Logger.getLogger(DBUtilities.class.getName()).log(Level.SEVERE, "exception caught", e);
                }
            }
        }
        return emf.createEntityManager();
    }
    
    private static final String connectionString = 
            "jdbc:sqlserver://localhost:1433; databaseName=FoodSupplementDB;";
    private static final String dbUsername = "sa";
    private static final String dbPassword = "";
    
    public static Connection makeConnection() throws Exception{
        Connection connection = null;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager
                    .getConnection(connectionString, dbUsername, dbPassword);
        return connection;
    }
    
}
