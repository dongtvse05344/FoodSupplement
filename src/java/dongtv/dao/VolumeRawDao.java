/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.CategoryDTO;
import dongtv.dto.VolumeRawDTO;
import java.io.Serializable;

/**
 *
 * @author Tran Dong
 */
public class VolumeRawDao  extends BaseDAO<VolumeRawDTO, Integer> implements Serializable {
    private static VolumeRawDao instance;

    private final static Object LOCK = new Object();

    public static VolumeRawDao getInstance() {
        synchronized (LOCK) {
            if (instance == null) {
                instance = new VolumeRawDao();
            }
        }
        return instance;
    }

    private VolumeRawDao() {
    }
}
