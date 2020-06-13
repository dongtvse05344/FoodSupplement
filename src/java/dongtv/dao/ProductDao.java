/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.ProductDTO;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Tran Dong
 */
public class ProductDao extends BaseDAO<ProductDTO, Integer> implements Serializable {
    private static ProductDao instance;
    
    private final static Object LOCK = new Object();
    
    public static ProductDao getInstance() {
        synchronized(LOCK) {
            if(instance ==null) {
                instance = new ProductDao();
            }
        }
        return instance;
    }

    private ProductDao() {
    }
//    EntityManagerFactory emf = Persistence.createEntityManagerFactory("FoodSupplementPU");
//  
//    public void persist(Object object) {
//        EntityManager em = emf.createEntityManager();
//        try {
//            em.getTransaction().begin();
//            em.persist(object);
//            em.getTransaction().commit();
//        } catch (Exception e) {
//            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
//            em.getTransaction().rollback();
//        } finally {
//            em.close();
//        }
//    }
}
