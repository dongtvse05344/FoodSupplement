package dongtv.dao;


import dongtv.dao.BaseDAO;
import dongtv.dao.ProductDao;
import dongtv.dto.ProductDTO;
import dongtv.dto.UserDTO;
import dongtv.dto.VolumeDTO;
import dongtv.util.DBUtilities;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tran Dong
 */
public class VolumeDao extends BaseDAO<VolumeDTO, Integer> implements Serializable {
    private static VolumeDao instance;
    
    private final static Object LOCK = new Object();
    
    public static VolumeDao getInstance() {
        synchronized(LOCK) {
            if(instance ==null) {
                instance = new VolumeDao();
            }
        }
        return instance;
    }
    private VolumeDao() {
    }
    
    public List<VolumeDTO> getVolumesbyProductId(ProductDTO productId) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<VolumeDTO> result = em.createNamedQuery("VolumeDTO.findByProductId", VolumeDTO.class)
                    .setParameter("productId", productId)
                    .getResultList();
            if(result !=null && !result.isEmpty()) {
                return result;
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
}
