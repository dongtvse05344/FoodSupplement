/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.contanst.Routing;
import dongtv.dao.BrandDao;
import dongtv.dao.CategoryDao;
import dongtv.dto.BrandDTO;
import dongtv.dto.BrandsDTO;
import dongtv.dto.raw.CategoriesDTO;
import dongtv.dto.raw.CategoryDTO;
import dongtv.dto.Paging;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductsDTO;
import dongtv.service.ProductService;
import dongtv.util.XMLUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tran Dong
 */
public class SearchServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Routing.SEARCH_VIEW;

        try {
            CategoryDao categoryDao = CategoryDao.getInstance();
            BrandDao brandDao = BrandDao.getInstance();
            String txtNameSearch = request.getParameter("txtSearch");
            if (txtNameSearch == null) {
                txtNameSearch = "";
            }
            String type = request.getParameter("type");
            String cateId = request.getParameter("cateId");
            System.out.println(cateId);
            String _page = request.getParameter("page");
            int page = 1;
            try {
                page = Integer.parseInt(_page);
            } catch (Exception e) {
            }
            // get Cate
            List<CategoryDTO> categories = categoryDao.getAll("CategoryDTO.findAll");
            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setCategoryDTOs(categories);
            String categories_XML = XMLUtils.marrsallMatchToString(categoriesDTO);
            request.setAttribute("CATEGORIES", categories_XML);

            // get brand
            List<BrandDTO> brands = brandDao.getAll("BrandDTO.findAll");
            BrandsDTO brandsDto = new BrandsDTO();
            brandsDto.setBrandDTO(brands);

            // get product
            List<ProductDTO> result;
            ProductService productService = new ProductService();

            Integer totalRows = null;
            if (cateId != null && !cateId.isEmpty()) {
                CategoryDTO categoryDTO = categoryDao.findById(Integer.parseInt(cateId));
                result = productService.getProductsByCate(categoryDTO, page, 20);
                totalRows = productService.findRowByCateId(categoryDTO).intValue();

            } else if ("isTop".equals(type)) {
                result = productService.getTopProduct("ProductDTO.findTopAll", txtNameSearch, page, 20);
            } else if ("isTopDpg".equals(type)) {
                result = productService.getTopProduct("ProductDTO.findTopDpg", txtNameSearch, page, 20);

            } else if ("isTopIso".equals(type)) {
                result = productService.getTopProduct("ProductDTO.findTopIso", txtNameSearch, page, 20);

            } else if ("isTopFps".equals(type)) {
                result = productService.getTopProduct("ProductDTO.findTopFps", txtNameSearch, page, 20);

            } else if ("brand".equals(type)) {
                String brandid = request.getParameter("value");
                BrandDTO brandDTO = brandDao.findById(Integer.parseInt(brandid));
                brandsDto.setSelected(brandid);

                result = productService.getProductsByBrand(brandDTO, page, 20);
                totalRows = productService.findRowByBrandId(brandDTO).intValue();

            } else {
                result = productService.getTopProduct("ProductDTO.findByName", txtNameSearch, page, 20);
            }
            if (totalRows == null) {
                totalRows = productService.getTotalRows(txtNameSearch).intValue();
            }
            //Convert to XML
            ProductsDTO productsDTO = new ProductsDTO();
            productsDTO.setProductDTOs(result);
            String products_XML = XMLUtils.marrsallMatchToString(productsDTO);
            request.setAttribute("PRODUCTS", products_XML);

            Paging paging = new Paging(page, totalRows, 20, "SearchServlet", txtNameSearch, type, cateId);
            String paging_XML = XMLUtils.marrsallMatchToString(paging);
            request.setAttribute("PAGING", paging_XML);

            // brand
            String brands_XML = XMLUtils.marrsallMatchToString(brandsDto);
            System.out.println(brands_XML);
            request.setAttribute("BRANDS", brands_XML);

        } catch (Exception ex) {
            url = Routing.INVALID_VIEW;
            Logger.getLogger(SearchServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
