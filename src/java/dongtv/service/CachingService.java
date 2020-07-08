/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.service;

import dongtv.dao.BrandDao;
import dongtv.dao.ProductDao;
import dongtv.dto.BrandDTO;
import dongtv.dto.BrandsDTO;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductsDTO;
import dongtv.util.XMLUtils;
import java.util.List;
import javax.servlet.ServletContext;

/**
 *
 * @author shuu1
 */
public class CachingService {

    private final ServletContext servletContext;
    private final ProductDao productDao;
    private final BrandDao brandDao;
    private static final String TOPPRODUCT = "TOPPRODUCT";
    private static final String TOPPRODUCTDPG = "TOPPRODUCTDPG";
    private static final String TOPPRODUCTISO = "TOPPRODUCTISO";
    private static final String TOPPRODUCTFPS = "TOPPRODUCTFPS";

    private static final String BRANDS = "BRANDS";

    public CachingService(ServletContext servletContext) {
        this.servletContext = servletContext;
        this.productDao = ProductDao.getInstance();
        this.brandDao = BrandDao.getInstance();
    }

    private String getTopProductFromDb() throws Exception {
        List<ProductDTO> products = productDao.getTopProduct("ProductDTO.findTopAll", "", 1, 9);
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setProductDTOs(products);
        String product_XML = XMLUtils.marrsallMatchToString(productsDTO);
        servletContext.setAttribute(TOPPRODUCT, product_XML);
        // store time
        return product_XML;
    }

    public String getTopProduct() throws Exception {
        String res;
        if (servletContext.getAttribute(TOPPRODUCT) != null) {
            try {
                //check time
                res = (String) servletContext.getAttribute(TOPPRODUCT);
            } catch (Exception e) {
                res = getTopProductFromDb();
            }
        } else {
            res = getTopProductFromDb();
        }
        return res;
    }

    private String getTopProductDpgFromDb() throws Exception {
        List<ProductDTO> products = productDao.getTopProduct("ProductDTO.findTopDpg", "", 1, 6);
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setProductDTOs(products);
        String product_XML = XMLUtils.marrsallMatchToString(productsDTO);
        servletContext.setAttribute(TOPPRODUCTDPG, product_XML);
        return product_XML;
        // store time
    }

    public String getTopProductDpg() throws Exception {
        String res;
        if (servletContext.getAttribute(TOPPRODUCTDPG) != null) {
            try {
                //check time
                res = (String) servletContext.getAttribute(TOPPRODUCTDPG);
            } catch (Exception e) {
                res = getTopProductDpgFromDb();
            }
        } else {
            res = getTopProductDpgFromDb();
        }
        return res;
    }

    private String getTopProductIsoFromDb() throws Exception {
        List<ProductDTO> products = productDao.getTopProduct("ProductDTO.findTopIso", "", 1, 6);
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setProductDTOs(products);
        String product_XML = XMLUtils.marrsallMatchToString(productsDTO);
        servletContext.setAttribute(TOPPRODUCTISO, product_XML);
        return product_XML;
        // store time
    }

    public String getTopProductIso() throws Exception {
        String res;
        if (servletContext.getAttribute(TOPPRODUCTISO) != null) {
            try {
                //check time
                res = (String) servletContext.getAttribute(TOPPRODUCTISO);
            } catch (Exception e) {
                res = getTopProductIsoFromDb();
            }
        } else {
            res = getTopProductIsoFromDb();
        }
        return res;
    }

    private String getTopProductFpsFromDb() throws Exception {
        List<ProductDTO> products = productDao.getTopProduct("ProductDTO.findTopFps", "", 1, 6);
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setProductDTOs(products);
        String product_XML = XMLUtils.marrsallMatchToString(productsDTO);
        servletContext.setAttribute(TOPPRODUCTFPS, product_XML);
        return product_XML;
        // store time
    }

    public String getTopProductFps() throws Exception {
        String res;
        if (servletContext.getAttribute(TOPPRODUCTFPS) != null) {
            try {
                //check time
                res = (String) servletContext.getAttribute(TOPPRODUCTFPS);
            } catch (Exception e) {
                res = getTopProductFpsFromDb();
            }
        } else {
            res = getTopProductFpsFromDb();
        }
        return res;
    }

    private String getBrandFromDb() throws Exception {
        BrandDao brandDao = BrandDao.getInstance();
        List<BrandDTO> brands = brandDao.getAll("BrandDTO.findAll");
        BrandsDTO brandsDto = new BrandsDTO();
        brandsDto.setBrandDTO(brands);
        String brands_XML = XMLUtils.marrsallMatchToString(brandsDto);
        servletContext.setAttribute(BRANDS, brands_XML);
        // store time
        return brands_XML;
    }

    public String getBrand() throws Exception {
        String res;
        if (servletContext.getAttribute(BRANDS) != null) {
            try {
                //check time
                res = (String) servletContext.getAttribute(BRANDS);
            } catch (Exception e) {
                res = getBrandFromDb();
            }
        } else {
            res = getBrandFromDb();
        }
        return res;
    }
}
