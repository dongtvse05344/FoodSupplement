/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.service;

import dongtv.dao.CategoryDao;
import dongtv.dto.CategoryDTO;
import dongtv.dto.ProductDTO;
import java.util.List;

/**
 *
 * @author Tran Dong
 */
public class CategoryService {

    private CategoryDao categoryDao;
    private static long totalRows = -1;

    public CategoryService() {
        categoryDao = CategoryDao.getInstance();
    }

    public List<CategoryDTO> getPage(int page) throws Exception {
        return categoryDao.getCategoryPaging(page, 5);
    }
    
    public Long getTotalRows() {
        if (totalRows <= 0) {
            totalRows = categoryDao.getTotalRows();
        }
        return totalRows;
    }

}
