/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.dao.CategoryDao;
import dongtv.dao.ProductDao;
import dongtv.dto.CategoriesDTO;
import dongtv.dto.CategoryDTO;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductsDTO;
import dongtv.util.XMLUtils;
import java.io.IOException;
import java.util.List;
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
        try {
            //get category
            CategoryDao categoryDao = CategoryDao.getInstance();
            List<CategoryDTO> categories = categoryDao.getAll("CategoryDTO.findAll");
            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setCategoryDTOs(categories);
            String categories_XML = XMLUtils.marrsallMatchToString(categoriesDTO);
            request.setAttribute("CATEGORIES", categories_XML);

            //get product
            ProductDao productDao = ProductDao.getInstance();
            List<ProductDTO> products = productDao.getTopProduct("ProductDTO.findTopAll","", 1, 9);
            
            
            List<ProductDTO> products2 = productDao.getTopProduct("ProductDTO.findTopDpg","", 1, 6);
            List<ProductDTO> products3 = productDao.getTopProduct("ProductDTO.findTopIso","", 1, 6);
            List<ProductDTO> products4 = productDao.getTopProduct("ProductDTO.findTopFps","", 1, 6);

            ProductsDTO productsDTO = new ProductsDTO();
            productsDTO.setProductDTOs(products);
            String product_XML = XMLUtils.marrsallMatchToString(productsDTO);
            request.setAttribute("PRODUCTS", product_XML);
            
            productsDTO.setProductDTOs(products2);
            String product_XML2 = XMLUtils.marrsallMatchToString(productsDTO);
            request.setAttribute("PRODUCTS2", product_XML2);
             
            productsDTO.setProductDTOs(products3);
            String product_XML3 = XMLUtils.marrsallMatchToString(productsDTO);
            request.setAttribute("PRODUCTS3", product_XML3);
            
            productsDTO.setProductDTOs(products4);
            String product_XML4 = XMLUtils.marrsallMatchToString(productsDTO);
            request.setAttribute("PRODUCTS4", product_XML4);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher("view/home.jsp").forward(request, response);
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
