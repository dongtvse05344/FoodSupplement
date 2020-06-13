package dongtv.dao;


import dongtv.dao.BaseDAO;
import dongtv.dao.ProductDao;
import dongtv.dto.VolumeDTO;
import java.io.Serializable;

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
}
