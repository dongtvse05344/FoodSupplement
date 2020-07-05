/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.service;

import dongtv.dao.CategoryDao;
import dongtv.dao.ProductDao;
import dongtv.dao.ProductRawDao;
import dongtv.dao.SubProductDao;
import dongtv.dto.CategoryDTO;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductRawDTO;
import dongtv.dto.ProductRawsDTO;
import dongtv.dto.ProductsDTO;
import dongtv.dto.SubProductDTO;
import dongtv.util.HTMLUtilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Tran Dong
 */
public class CrawlService {

    private ProductRawDao productRawDao;
    private ProductDao productDao;
    private CategoryDao categoryDao;
    private SubProductDao subProductDao;
    private static long totalRows = -1;

    public CrawlService() {
        productRawDao = ProductRawDao.getInstance();
        productDao = ProductDao.getInstance();
        categoryDao = CategoryDao.getInstance();
        subProductDao = SubProductDao.getInstance();
    }

    public List<ProductRawDTO> getProductPage(int pages) throws Exception {
        return productRawDao.getProductPaging("name", pages, 5);
    }

    public List<ProductRawDTO> getAllProducts() throws Exception {
        return productRawDao.getAll("ProductRawDTO.findAllWithSort");
    }

    public Long getTotalRows() {
        if (totalRows <= 0) {
            totalRows = productRawDao.getTotalRows();
        }
        return totalRows;
    }

    private ProductDTO createProductRawtoProduct(ProductRawDTO dto) {
        ProductDTO product = new ProductDTO();
        product.setCategoryId(dto.getCategoryId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setOriginalLink(dto.getOriginalLink());
        product.setImage(dto.getImage());
        product.setDpg(dto.getDpg());
        product.setFps(dto.getFps());
        product.setIso(dto.getIso());
        product.setDisplay(dto.getDisplay());
        return productDao.create(product);
    }

    private SubProductDTO createProductRawtoSubProduct(ProductRawDTO dto, ProductDTO productDto) {
        SubProductDTO product = new SubProductDTO();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setProductId(productDto);
        product.setOriginalLink(dto.getOriginalLink());
        product.setImage(dto.getImage());
        return subProductDao.create(product);
    }

    public Double takeMax(List<Double> values) {
        if (values == null || values.isEmpty()) {
            return null;
        }
        Collections.sort(values);
        int count = 1;
        int resultCount = 1;
        Double result = values.get(0);
        Double current = values.get(0);
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i).equals(current)) {
                count++;
            } else {
                if (count >= resultCount) {
                    result = current;
                    resultCount = count;

                }
                count = 1;
                current = values.get(i);
            }
        }
        if (count >= resultCount) {
            result = current;
            resultCount = count;
        }
        return result;
    }

    private void handleRaw(ProductRawDTO dto, List<ProductRawDTO> subDtos) {
        if (subDtos.isEmpty()) {
            createProductRawtoProduct(dto);
        } else {
            List<Double> values = new ArrayList<>();
            if (dto.getDpg() != null) {
                values.add(dto.getDpg());
            }
            for (ProductRawDTO myDto : subDtos) {
                if (myDto.getDpg() != null) {
                    values.add(myDto.getDpg());
                }
            }
            dto.setDpg(takeMax(values));
            values.clear();

            //
            if (dto.getIso() != null) {
                values.add(dto.getIso());
            }
            for (ProductRawDTO myDto : subDtos) {
                if (myDto.getIso() != null) {
                    values.add(myDto.getIso());
                }
            }
            dto.setIso(takeMax(values));
            values.clear();
            //
            if (dto.getFps() != null) {
                values.add(dto.getFps());
            }
            for (ProductRawDTO myDto : subDtos) {
                if (myDto.getFps() != null) {
                    values.add(myDto.getFps());
                }
            }
            dto.setFps(takeMax(values));
            values.clear();

            ProductDTO productDto = createProductRawtoProduct(dto);
            for (ProductRawDTO subDto : subDtos) {
                createProductRawtoSubProduct(subDto, productDto);
            }
        }
    }

    public void convertRawstoReal(ProductRawsDTO realProducts) throws Exception {
        List<ProductRawDTO> products = realProducts.getProductDTOs();
        if (products.isEmpty()) {
            return;
        }

        ProductRawDTO current = products.get(0);
        Integer currentPrice = current.getPrice();
        List<ProductRawDTO> collectionCurrent = new ArrayList<>();
        for (int i = 1; i < products.size(); i++) {
            Integer tempId = products.get(i).getParentId();
            if (tempId != null && tempId.equals(current.getId())) {
                collectionCurrent.add(products.get(i));
                if (products.get(i).getPrice() > -1) {
                    if (currentPrice == -1) {
                        currentPrice = products.get(i).getPrice();
                    } else {
                        currentPrice = Integer.min(currentPrice, products.get(i).getPrice());
                    }
                }
            } else {
                if (current.getPrice().equals(-1)) {
                    current.setPrice(currentPrice);
                }
                handleRaw(current, collectionCurrent);
                current = products.get(i);
                currentPrice = current.getPrice();
                collectionCurrent.clear();
            }
        }
        handleRaw(current, collectionCurrent);
//        productRawDao.deleteAll();
    }

    public Double getDpgFromDes(String des) {
        Double res = null;
        try {
            List<String> dpg = HTMLUtilities.getAllMatches(des, "[0-9]{1,3}[.,]{0,1}[0-9]{0,3}[ -–]{0,3}[Mm][PpEe]");
            if (dpg.size() > 0) {
                int p = 0;
                do {
                    String _dpg = HTMLUtilities.getAllMatches(dpg.get(0), "[0-9]{1,3}[.,]{0,1}[0-9]{0,3}")
                            .get(p++).replace(",", ".");
                    res = Double.parseDouble(_dpg);
                }
                while(res >100 || res <=1);
                if(res > 100 && res <= 1) res = null;
            }
        } catch (Exception e) {
        }
        return res;
    }

    public Double getIsoFromDes(String des) {
        Double res = null;
        try {
            List<String> iso = HTMLUtilities.getAllMatches(des, "I[Ss][Oo].{1,18}[0-9]{1,6}[,.]{0,1}[0-9]{0,3}[0]{2}");
            if (iso.size() > 0) {
                List<String> temp = HTMLUtilities.getAllMatches(iso.get(0), "[0-9]{1,6}[,.]{0,1}[0-9]{0,3}");
                List<Double> temp3 = new ArrayList<>();
                for (String childTemp : temp) {
                    try {
                        Double a = Double.parseDouble(childTemp.replace(",", "").replace(".", ""));
                        temp3.add(a);
                    } catch (Exception e) {
                    }
                }
                Collections.sort(temp3);

                res = temp3.get(temp3.size() - 1);
            }
        } catch (Exception e) {
        }
        return res;
    }

    public Double getFpsFromDes(String des) {
        Double res = null;
        try {
            List<String> fps = HTMLUtilities.getAllMatches(des, "[ -][0-9]{1,2}[.,]{0,1}[0-9]{0,1}[ ]{0,1}[fpshìnhảkhufra]{3}");
            if (fps.size() > 0) {
                String temp = fps.toString();
                List<String> temp2 = HTMLUtilities.getAllMatches(temp, "[0-9]{1,2}[.,]{0,1}[0-9]{0,1}");
                List<Double> temp3 = new ArrayList<>();
                for (String childTemp : temp2) {
                    try {
                        Double a = Double.parseDouble(childTemp);
                        temp3.add(a);
                    } catch (Exception e) {
                    }
                }
                Collections.sort(temp3);
                int pointer = 0;
                while (pointer < temp3.size()) {
                    if (temp3.get(pointer) > 30) {
                        break;
                    }
                    pointer++;
                }
                if (pointer > 0) {
                    res = temp3.get(pointer - 1);
                }
            }
        } catch (Exception e) {
        }
        return res;
    }

    public Double getDisplayFromDes(String des) {
        Double res = null;
        try {
            List<String> display = HTMLUtilities.getAllMatches(des, " [0-9][.,]{0,1}[0-9]{0,3}[ ]{0,1}[\"in”']{1,2}");
            if (display.size() > 0) {
                String temp = HTMLUtilities.getAllMatches(display.get(0), "[0-9]{1,2}[.,]{0,1}[0-9]{0,1}")
                        .get(0).replace(",", ".");
                res = Double.parseDouble(temp);
                if (res > 8 || res < 0.5) {
                    res = null;
                }
            }
        } catch (Exception e) {
        }
        return res;
    }

    public void getParamtoProduct(ProductRawDTO dto, String des) {
//        getParamFromDes(dto, des); 
        Double dpg = getDpgFromDes(des);
//        System.out.println("DPG: " + dpg);
        Double iso = getIsoFromDes(des);
//        System.out.println("ISO: " + iso);
        Double fps = getFpsFromDes(des);
//        System.out.println("FPS: " + fps);
        Double display = getDisplayFromDes(des);
        dto.setDpg(dpg);
        dto.setIso(iso);
        dto.setFps(fps);
        dto.setDisplay(display);
    }

    public void createProduct(ProductRawDTO product) {
        productRawDao.create(product);
    }

    public CategoryDTO createCategory(String category) {
        return categoryDao.create(new CategoryDTO(category));
    }

    private static String textPattern = "[a-zA-Z0-9]{1}";

    public boolean compareName(String np1, String np2) {
        np1 = np1.toLowerCase();
        np2 = np2.toLowerCase();
        int pointer = 0;
        while (pointer < np1.length() && pointer < np2.length() && np1.charAt(pointer) == np2.charAt(pointer)) {
            if (isDone(np1.charAt(pointer))) {
                return true;
            }
            pointer++;
        }
        if (pointer >= np1.length() || pointer >= np2.length()) {
            return true;
        }
        if(isDone(np1.charAt(pointer))|| isDone(np2.charAt(pointer))) {
            return true;
        }
        return false;
    }

    public boolean compareName(ProductRawDTO p1, ProductRawDTO p2) {
        String np1 = p1.getName();
        String np2 = p2.getName();
        return compareName(np1, np2);
    }

    private boolean isDone(char x) {
        if (x == '(' || x == ',' || x == '+' || x == '-') {
            return true;
        }
        return false;
    }

    public void updateProductRaw(ProductRawDTO dto) {
        productRawDao.update(dto);
    }
}
