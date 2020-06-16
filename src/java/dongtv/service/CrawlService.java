/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.service;

import dongtv.dao.ProductDao;
import dongtv.dao.ProductRawDao;
import dongtv.dao.VolumeRawDao;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductRawDTO;
import dongtv.dto.ProductsDTO;
import java.util.List;

/**
 *
 * @author Tran Dong
 */
public class CrawlService {
    private ProductRawDao productRawDao;
    private VolumeRawDao volumeRawDao;
    private ProductDao productDao;
    private static long totalRows = -1;

    public CrawlService() {
        productRawDao = ProductRawDao.getInstance();
        volumeRawDao = VolumeRawDao.getInstance();
        productDao = ProductDao.getInstance();
    }
    
    public List<ProductRawDTO> getProductPage(int pages) throws Exception {
        return productRawDao.getProductPaging("name", pages, 5);
    }
    
     public Long getTotalRows() {
        if (totalRows <= 0) {
            totalRows = productRawDao.getTotalRows();
        }
        return totalRows;
    }
     
     public List<ProductRawDTO> getProductAll() throws Exception {
         return productRawDao.getAll("ProductRawDTO.findAll");
     }
     
     public void convertRawstoReal(ProductsDTO realProducts) throws Exception {
         for (ProductDTO productDTO : realProducts.getProductDTOs()) {
             productDao.create(productDTO);
         }
         productRawDao.deleteAll();
     }
    
    
}