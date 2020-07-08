/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.contanst.Routing;
import dongtv.dao.BrandDao;
import dongtv.dao.CategoryDao;
import dongtv.dao.ProductDao;
import dongtv.dto.BrandDTO;
import dongtv.dto.BrandsDTO;
import dongtv.dto.raw.CategoriesDTO;
import dongtv.dto.raw.CategoryDTO;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductsDTO;
import dongtv.service.CachingService;
import dongtv.util.XMLUtils;
import java.io.IOException;
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
public class HomeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        CachingService cachingService = new CachingService(request.getServletContext());
        String url =  Routing.HOME_VIEW;
        try {
            //get category
            CategoryDao categoryDao = CategoryDao.getInstance();
            List<CategoryDTO> categories = categoryDao.getAll("CategoryDTO.findAll");
            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setCategoryDTOs(categories);
            String categories_XML = XMLUtils.marrsallMatchToString(categoriesDTO);
            request.setAttribute("CATEGORIES", categories_XML);

            // get brand
            String brands_XML = cachingService.getBrand();
            request.setAttribute("BRANDS", brands_XML);
            //get product
            ProductDao productDao = ProductDao.getInstance();

            //load from caching
//            List<ProductDTO> products = productDao.getTopProduct("ProductDTO.findTopAll", "", 1, 9);
            String product_XML = cachingService.getTopProduct();
            String product_XML2 = cachingService.getTopProductDpg();
            String product_XML3 = cachingService.getTopProductIso();
            String product_XML4 = cachingService.getTopProductFps();

            request.setAttribute("PRODUCTS", product_XML);
            request.setAttribute("PRODUCTS2", product_XML2);
            request.setAttribute("PRODUCTS3", product_XML3);
            request.setAttribute("PRODUCTS4", product_XML4);
        } catch (Exception ex) {
            url = Routing.INVALID_VIEW;
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
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
