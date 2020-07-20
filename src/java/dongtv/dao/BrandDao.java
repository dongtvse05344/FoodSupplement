/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.BrandDTO;
import dongtv.util.DBUtilities;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author shuu1
 */
public class BrandDao extends BaseDAO<BrandDTO, Integer> {

    private static BrandDao instance;

    private final static Object LOCK = new Object();

    public static BrandDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new BrandDao();
            }
        }
        return instance;
    }

    public boolean deleteAll() throws Exception {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            int result = em.createNamedQuery("BrandDTO.deleteAll")
                    .executeUpdate();
            transaction.commit();

            if (result > 0) {
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
}
