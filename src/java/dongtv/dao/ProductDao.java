/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.ProductDTO;
import dongtv.dto.UserDTO;
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
public class ProductDao extends BaseDAO<ProductDTO, Integer> implements Serializable {

    private static ProductDao instance;

    private final static Object LOCK = new Object();

    public static ProductDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ProductDao();
            }
        }
        return instance;
    }

    private ProductDao() {
    }
    
    public Long getTotalRows() {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<Long> result = em.createNamedQuery("ProductDTO.getTotalRows",Long.class)
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

    public List<ProductDTO> getProductPaging(String orderby, int page, int rowsOfPage) throws Exception {
        List<ProductDTO> res = new LinkedList<>();
        try {
            conn = DBUtilities.makeConnection();
            String sql = "SELECT id,price,name,description,image,originalLink,categoryId from products Order by name OFFSET ? ROWS FETCH NEXT ? ROWS ONLY ";
            preStm = conn.prepareStatement(sql);
            preStm.setInt(1, (page-1)*rowsOfPage);
            preStm.setInt(2, rowsOfPage);
            rs = preStm.executeQuery();
            while (rs.next()) {
                res.add(new ProductDTO(
                        rs.getInt("price"),
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),  
                        rs.getString("image"), 
                        rs.getString("originalLink"), 
                        null, 
                        null
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
}
