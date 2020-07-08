/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.BrandDTO;

/**
 *
 * @author shuu1
 */
public class BrandDao extends BaseDAO<BrandDTO, Integer>{
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
}
