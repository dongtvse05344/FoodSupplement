/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.CategoryDTO;
import dongtv.dto.ProductDTO;
import dongtv.util.DBUtilities;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author Tran Dong
 */
public class CategoryDao extends BaseDAO<CategoryDTO, Integer> implements Serializable {

    private static CategoryDao instance;

    private final static Object LOCK = new Object();

    public static CategoryDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new CategoryDao();
            }
        }
        return instance;
    }

    private CategoryDao() {
    }
    
    public Long getTotalRows() {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<Long> result = em.createNamedQuery("CategoryDTO.getTotalRows",Long.class)
                    .getResultList();
            if(result !=null && !result.isEmpty()) {
                return result.get(0);
            }
            return null;
        } catch (Exception e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            if(em != null){
                em.close();
            }
        }
        return null;
    }

    public List<CategoryDTO> getCategoryPaging( int page, int rowsOfPage) throws Exception {
        List<CategoryDTO> res = new LinkedList<>();
        try {
            conn = DBUtilities.makeConnection();
            String sql = "SELECT id,name from categories Order by name OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
            preStm = conn.prepareStatement(sql);

            preStm.setInt(1, (page - 1) * rowsOfPage);
            preStm.setInt(2, rowsOfPage);
            rs = preStm.executeQuery();
            while (rs.next()) {
                res.add(new CategoryDTO(
                        rs.getInt("id"),
                        rs.getString("name")
                )
                );
            }
        } finally {
            closeConnect();
        }
        return res;
    }

    private Connection conn;
    private PreparedStatement preStm;
    private ResultSet rs;

    private void closeConnect() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (preStm != null) {
            preStm.close();
        }
        if (conn != null) {
            conn.close();
        }
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
