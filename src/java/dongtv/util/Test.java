/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.util;

import dongtv.dao.CategoryDao;
import dongtv.dto.raw.CategoriesDTO;
import dongtv.dto.raw.CategoryDTO;
import java.util.List;

/**
 *
 * @author Tran Dong
 */
public class Test {
    public static void main(String[] args) throws Exception {
        CategoryDao categoryDao =CategoryDao.getInstance();
        List<CategoryDTO> categories = categoryDao.getAll("CategoryDTO.findAll");
        CategoriesDTO categoriesDTO  =  new CategoriesDTO();
        categoriesDTO.setCategoryDTOs(categories);
        String result = XMLUtils.marrsallMatchToString(categoriesDTO);
        System.out.println(result);
    }
}
