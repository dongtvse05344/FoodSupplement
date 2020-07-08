/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.raw.ProductRawDTO;
import dongtv.util.DBUtilities;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Tran Dong
 */
public class ProductRawDao extends BaseDAO<ProductRawDTO, Integer> implements Serializable {

    private static ProductRawDao instance;

    private final static Object LOCK = new Object();

    public static ProductRawDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new ProductRawDao();
            }
        }
        return instance;
    }

    private ProductRawDao() {
    }

    public boolean deleteAll() {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            int result = em.createNamedQuery("ProductRawDTO.deleteAll")
                    .executeUpdate();
            transaction.commit();
            if (result >0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return false;
    }

    public Long getTotalRows() {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<Long> result = em.createNamedQuery("ProductRawDTO.getTotalRows", Long.class)
                    .getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
            return null;
        } catch (Exception e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    public List<ProductRawDTO> getProductPaging(String orderby, int page, int rowsOfPage) throws Exception {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<ProductRawDTO> result = em.createNamedQuery("ProductRawDTO.findAll", ProductRawDTO.class)
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
