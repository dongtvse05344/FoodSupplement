/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.CategoryDTO;
import dongtv.dto.ProductDTO;
import dongtv.util.DBUtilities;
import java.awt.print.Book;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
            List<Long> result = em.createNamedQuery("ProductDTO.getTotalRows", Long.class)
                    .setParameter("name", "%" + nameSearch + "%")
                    .getResultList();
            if (result != null && !result.isEmpty()) {
                return result.get(0);
            }
            return null;
        } catch (Exception e) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }

    

    public List<ProductDTO> getTopProduct(String namedQuery, String nameSearch, int page, int rowsOfPage) throws Exception {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<ProductDTO> result = em.createNamedQuery(namedQuery, ProductDTO.class)
                    .setParameter("name", "%" + nameSearch + "%")
                    .setFirstResult((page - 1) * rowsOfPage)
                    .setMaxResults(rowsOfPage)
                    .getResultList();
            return result;
        } catch (Exception e) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
    
    public List<ProductDTO> getProductsByCate(CategoryDTO cateId, int page, int rowsOfPage) throws Exception {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<ProductDTO> result = em.createNamedQuery("ProductDTO.findByCateId", ProductDTO.class)
                    .setParameter("cateId", cateId)
                    .setFirstResult((page - 1) * rowsOfPage)
                    .setMaxResults(rowsOfPage)
                    .getResultList();
            return result;
        } catch (Exception e) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return null;
    }
    
}
