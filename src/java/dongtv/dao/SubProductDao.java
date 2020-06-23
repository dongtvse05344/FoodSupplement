/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.ProductDTO;
import dongtv.dto.SubProductDTO;
import dongtv.util.DBUtilities;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author Tran Dong
 */
public class SubProductDao extends BaseDAO<SubProductDTO, Integer> implements Serializable {
    private static SubProductDao instance;

    private final static Object LOCK = new Object();

    public static SubProductDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new SubProductDao();
            }
        }
        return instance;
    }
    
    public List<SubProductDTO> getProductDTOsByProductId(ProductDTO id) throws Exception {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<SubProductDTO> result = em.createNamedQuery("SubProductDTO.findByProductId",SubProductDTO.class)
                    .setParameter("id", id)
                    .getResultList();
            
            return result;
        } catch (Exception e) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, e);
        }finally {
            if(em != null){
                em.close();
            }
        }
        return null;
    }
}
