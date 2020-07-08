/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.dto.ProductDTO;
import dongtv.dto.ProductsDTO;
import dongtv.contanst.Routing;
import dongtv.dto.SubProductDTO;
import dongtv.dto.SubProductsDTO;
import dongtv.service.ProductService;
import dongtv.util.XMLUtils;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ProductDetailServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Routing.PRODUCT_DETAIL_VIEW;
        try {
            int id = -1;
            String idString = request.getParameter("id");
            if (idString == null) {
                url = Routing.HOME_SERVLET;
            }
            id = Integer.parseInt(idString);

            //get product
            ProductService productService = new ProductService();
            ProductDTO product = productService.getProduct(id);
            String product_XML = XMLUtils.marrsallMatchToString(product);

            // Lấy sản phẩm con
            List<SubProductDTO> subProducts = productService.getSubProduct(product);

            SubProductsDTO subProductsDTO = new SubProductsDTO();
            subProductsDTO.setProductDTOs(subProducts);
            String sub_products_XML = XMLUtils.marrsallMatchToString(subProductsDTO);

            //lấy sp tương tự
            List<ProductDTO> products4 = productService.getSimilarity(product);
            ProductsDTO productsDTO = new ProductsDTO();
            productsDTO.setProductDTOs(products4);
            String products_XML = XMLUtils.marrsallMatchToString(productsDTO);

            request.setAttribute("PRODUCTS", products_XML);
            request.setAttribute("SUBPRODUCTS", sub_products_XML);
            request.setAttribute("PRODUCT", product_XML);

        } catch (Exception ex) {
            url = Routing.INVALID_VIEW;
            Logger.getLogger(ProductDetailServlet.class.getName()).log(Level.SEVERE, null, ex);
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
