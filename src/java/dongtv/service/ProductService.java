/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.service;

import dongtv.dao.BrandDao;
import dongtv.dao.ProductDao;
import dongtv.dao.SubProductDao;
import dongtv.dto.BrandDTO;
import dongtv.dto.raw.CategoryDTO;
import dongtv.dto.ProductDTO;
import dongtv.dto.SubProductDTO;
import dongtv.servlet.CollectBrandServlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tran Dong
 */
public class ProductService {

    private final ProductDao productDao;
    private final SubProductDao subProductDao;
    private final BrandDao brandDao;

    public ProductService() {
        productDao = ProductDao.getInstance();
        subProductDao = SubProductDao.getInstance();
        brandDao = BrandDao.getInstance();
    }

    public List<ProductDTO> getPage(String nameSearch, int page) throws Exception {
        return productDao.getTopProduct("ProductDTO.findByName", nameSearch, page, 5);
    }

    public List<ProductDTO> getTopProduct(String namedQuery, String nameSearch, int page, int rowsOfPage) throws Exception {
        return productDao.getTopProduct(namedQuery, nameSearch, page, rowsOfPage);
    }

    public List<ProductDTO> getProductsByBrand(BrandDTO brandId, int page, int rowsOfPage) throws Exception {
        return productDao.getProductsByBrand(brandId, page, rowsOfPage);
    }

    public List<ProductDTO> getProductsByCate(CategoryDTO cateId, int page, int rowsOfPage) throws Exception {
        return productDao.getProductsByCate(cateId, page, rowsOfPage);
    }

    public Long getTotalRows(String nameSearch) {
        return productDao.getTotalRows(nameSearch);
    }

    public Long findRowByCateId(CategoryDTO cateId) {
        return productDao.findRowByCateId(cateId);
    }

    public Long findRowByBrandId(BrandDTO brandId) {
        return productDao.findRowByBrandId(brandId);
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
        try {
            double result = (double) 0;
            result = p1.getQDpg() * p2.getQDpg() + p1.getQIso() * p2.getQIso() + p1.getQFps() * p2.getQFps();
            double multiOflength = Math.sqrt(p1.getQDpg() * p1.getQDpg() + p1.getQIso() * p1.getQIso() + p1.getQFps() * p1.getQFps())
                    * Math.sqrt(p2.getQDpg() * p2.getQDpg() + p2.getQIso() * p2.getQIso() + p2.getQFps() * p2.getQFps());
            return result / multiOflength;
        } catch (Exception e) {
            return 0;
        }

    }

    public List<ProductDTO> getSimilarity(ProductDTO dto) {
        List<ProductDTO> result = new ArrayList<>();
        List<ProductDTO> products = getAll();
        for (int i = 0; i < products.size(); i++) {
            if (!products.get(i).getId().equals(dto.getId())) {
                if (this.cosinOf2Vector(products.get(i), dto) >= 0.9) {
                    result.add(products.get(i));
                }
                if (result.size() > 5) {
                    return result;
                }
            }
        }
        return result;
    }

    private List<ProductDTO> getAll() {
        return productDao.getAll("ProductDTO.findAll");
    }

    public void collectBrand() {

        // DELETE ALL BRAND
        List<ProductDTO> products = productDao.getAll("ProductDTO.findAll");
        Map<String, String> brands = new HashMap<String, String>();
        for (ProductDTO product : products) {
            brands.put(product.getName().toLowerCase()
                    .replace("máy ảnh ", "")
                    .replace("máy quay ", "")
                    .replace("ma?y quay ", "")
                    .replace("body ", "")
                    .replace("ống kính ", "").split(" ")[0], "x");
        }
        List<BrandDTO> brandDTOs = new ArrayList<>();
        try {
            System.out.println(brandDao.deleteAll());
        } catch (Exception ex) {
            Logger.getLogger(ProductService.class.getName()).log(Level.SEVERE, null, ex);

        }
        for (Map.Entry<String, String> brand : brands.entrySet()) {
            if (!checkUTF8(brand.getKey())) {
                BrandDTO brandDto = brandDao.create(new BrandDTO(brand.getKey()));
                brandDTOs.add(brandDto);
            }
        }
        for (ProductDTO product : products) {
            for (BrandDTO brand : brandDTOs) {
                if (product.getName().toLowerCase().contains(brand.getName())) {
                    product.setBrandId(brand);
                    break;
                }
            }
            productDao.update(product);
        }
    }

    public boolean checkUTF8(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if ((int) c > 126 || c < 97) {
                return true;
            }
        }
        return false;
    }
}
