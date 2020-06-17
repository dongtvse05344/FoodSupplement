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
    
    public Long getTotalRows(String nameSearch) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<Long> result = em.createNamedQuery("ProductDTO.getTotalRows",Long.class)
                    .setParameter("name", "%" + nameSearch +"%")
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

    public List<ProductDTO> getProductPaging(String nameSearch, String orderby, int page, int rowsOfPage) throws Exception {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<ProductDTO> result = em.createNamedQuery("ProductDTO.findAll", ProductDTO.class)
                    .setParameter("name", "%" + nameSearch +"%")
                    .setFirstResult((page - 1) * rowsOfPage)
                    .setMaxResults(rowsOfPage)
                    .getResultList();
            return result;
        } catch (Exception e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
}
