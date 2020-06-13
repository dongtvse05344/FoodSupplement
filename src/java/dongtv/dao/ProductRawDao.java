/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dao;

import dongtv.dto.ProductRawDTO;
import dongtv.dto.VolumeRawDTO;
import java.io.Serializable;

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
}
