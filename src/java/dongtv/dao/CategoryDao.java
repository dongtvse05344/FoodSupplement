/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.CategoryDTO;
import dongtv.util.DBUtilities;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Tran Dong
 */
public class CategoryDao extends BaseDAO<CategoryDTO, Integer> implements Serializable {
    private static CategoryDao instance;
    
    private final static Object LOCK = new Object();
    
    public static CategoryDao getInstance() {
        synchronized(LOCK) {
            if(instance ==null) {
                instance = new CategoryDao();
            }
        }
        return instance;
    }

    private CategoryDao() {
    }
    
//    private Connection conn;
//    private PreparedStatement preStm;
//    private ResultSet rs;
//    
//    public boolean addCategory(String name) throws Exception {
//        boolean res = false;
//        try {
//            conn = DBUtilities.makeConnection();
//             String sql ="INSERT INTO Categories(name) "
//                    + " VALUES(?)";
//            preStm = conn.prepareStatement(sql);
//            preStm.setString(1, name);
//            res = preStm.executeUpdate() > 0;
//        } finally {
//            closeConnect();
//        }
//
//        return res;
//    }
//    
//    private void closeConnect() throws SQLException {
//        if (rs != null) {
//            rs.close();
//        }
//        if (preStm != null) {
//            preStm.close();
//        }
//        if (conn != null) {
//            conn.close();
//        }
//    }
}
