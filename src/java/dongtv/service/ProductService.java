/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.service;

import dongtv.dao.ProductDao;
import dongtv.dao.VolumeDao;
import dongtv.dto.ProductDTO;
import dongtv.dto.VolumeDTO;
import java.util.List;

/**
 *
 * @author Tran Dong
 */
public class ProductService {

    private ProductDao productDao;
    private VolumeDao volumeDao;
    private static long totalRows = -1;

    public ProductService() {
        productDao = ProductDao.getInstance();
        volumeDao = VolumeDao.getInstance();
    }

    public List<ProductDTO> getPage(int page) throws Exception {
        return productDao.getProductPaging("name", page, 5);
    }

    public Long getTotalRows() {
        if (totalRows <= 0) {
            totalRows = productDao.getTotalRows();
        }
        return totalRows;
    }
    public ProductDTO getProduct(Integer id) {
        return productDao.findById(id);
    }
    public List<VolumeDTO> getVolumes(ProductDTO id) {
        return volumeDao.getVolumesbyProductId(id);
    }
}
