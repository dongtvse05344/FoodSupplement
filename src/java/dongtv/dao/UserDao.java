/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.crawler.DemxanhProductCrawler;
import dongtv.dto.ProductDTO;
import dongtv.dto.UserDTO;
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
public class UserDao extends BaseDAO<UserDTO, Integer> implements Serializable {

    private static UserDao instance;

    private final static Object LOCK = new Object();

    public static UserDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new UserDao();
            }
        }
        return instance;
    }

    private UserDao() {
    }

    public UserDTO findByUsernameandPassword(String username, String password) {
        EntityManager em = DBUtilities.getEntityManager();
        try {
            List<UserDTO> result = em.createNamedQuery("UserDTO.findByUsernameAndPassword", UserDTO.class)
                    .setParameter("username", username).setParameter("password", password)
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
}
