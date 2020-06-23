/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.service;

import dongtv.dao.ProductDao;
import dongtv.dao.SubProductDao;
import dongtv.dto.ProductDTO;
import dongtv.dto.SubProductDTO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tran Dong
 */
public class ProductService {

    private ProductDao productDao;
    private SubProductDao subProductDao;
    private static long totalRows = -1;

    public ProductService() {
        productDao = ProductDao.getInstance();
        subProductDao = SubProductDao.getInstance();
    }

    public List<ProductDTO> getPage(String nameSearch, int page) throws Exception {
        return productDao.getProductPaging(nameSearch, "name", page, 5);
    }
    
    public List<ProductDTO> getTopProduct(String namedQuery, String nameSearch, int page, int rowsOfPage) throws Exception {
        return productDao.getTopProduct(namedQuery, nameSearch, page, rowsOfPage);
    }

    public Long getTotalRows(String nameSearch) {
        return totalRows = productDao.getTotalRows(nameSearch);
    }

    public ProductDTO getProduct(Integer id) {
        return productDao.findById(id);
    }
    
    public ProductDTO updateProduct(ProductDTO dto) {
        return productDao.update(dto);
    }

    public List<SubProductDTO> getSubProduct(ProductDTO id) throws Exception {
        return subProductDao.getProductDTOsByProductId(id);
    }

    public void quanlifyData() {
        List<ProductDTO> products = productDao.getAll("ProductDTO.findAll");
        Double meanDpg = productDao.getData("ProductDTO.getMeanDpg");
        Double exxDpg = productDao.getData("ProductDTO.getExxDpg");
        Double varianceDpg = exxDpg - meanDpg * meanDpg;
        Double standardDpg = Math.sqrt(varianceDpg);

        Double meanIso = productDao.getData("ProductDTO.getMeanIso");
        Double exxIso = productDao.getData("ProductDTO.getExxIso");
        Double varianceIso = exxIso - meanIso * meanIso;
        Double standardIso = Math.sqrt(varianceIso);

        Double meanFps = productDao.getData("ProductDTO.getMeanFps");
        Double exxFps = productDao.getData("ProductDTO.getExxFps");
        Double varianceFps = exxFps - meanFps * meanFps;
        Double standardFps = Math.sqrt(varianceFps);

        System.out.println(meanDpg + " | " + standardDpg);
        System.out.println(meanIso + " | " + standardIso);
        System.out.println(meanFps + " | " + standardFps);

        for (ProductDTO product : products) {
            Double dpg = product.getDpg();
            if (dpg == null) {
                dpg = -0.5;
            } else {
                dpg = (dpg - meanDpg) / standardDpg;
            }
            product.setQDpg(dpg);

            Double iso = product.getIso();
            if (iso == null) {
                iso = -0.5;
            } else {
                iso = (iso - meanIso) / standardIso;
            }
            product.setQIso(iso);

            Double fps = product.getFps();
            if (fps == null) {
                fps = -0.5;
            } else {
                fps = (fps - meanFps) / standardFps;
            }
            product.setQFps(fps);
            productDao.update(product);
        }
    }

    public double cosinOf2Vector(ProductDTO p1, ProductDTO p2) {
        double result = (double) 0;
        result = p1.getQDpg() * p2.getQDpg() + p1.getQIso() * p2.getQIso() + p1.getQFps() * p2.getQFps();
        double multiOflength = Math.sqrt(p1.getQDpg() * p1.getQDpg() + p1.getQIso() * p1.getQIso() + p1.getQFps() * p1.getQFps())
                * Math.sqrt(p2.getQDpg() * p2.getQDpg() + p2.getQIso() * p2.getQIso() + p2.getQFps() * p2.getQFps());
        return result / multiOflength;
    }

    public List<ProductDTO> getSimilarity(ProductDTO dto) {
        List<ProductDTO> result = new ArrayList<>();
        List<ProductDTO> products = getAll();
        for (int i = 0; i < products.size(); i++) {
            if(!products.get(i).getId().equals(dto.getId())) {
                if(this.cosinOf2Vector(products.get(i), dto)>= 0.9) {
                    result.add(products.get(i));
                }
                if(result.size() > 4) return result;
            }
        }
        return result;
    }

    private List<ProductDTO> getAll() {
        return productDao.getAll("ProductDTO.findAll");
    }
}
