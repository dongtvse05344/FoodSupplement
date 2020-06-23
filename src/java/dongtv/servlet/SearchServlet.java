/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.contanst.Routing;
import dongtv.dto.Paging;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductsDTO;
import dongtv.service.ProductService;
import dongtv.util.XMLUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
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
            String txtNameSearch = request.getParameter("txtNameSearch") ;
            if(txtNameSearch ==null) txtNameSearch ="";
            String type = request.getParameter("type");

            String _page = request.getParameter("page");
            int page = 1;
            try {
                page = Integer.parseInt(_page);
            } catch (Exception e) {
            }
            List<ProductDTO> result = new ArrayList<>();

            ProductService productService = new ProductService();
            if (!"isTop".equals(type)) {
                result = productService.getTopProduct("ProductDTO.findTopAll", txtNameSearch, 1, 20);
            } else if (!"isTopDpg".equals(type)) {
                result = productService.getTopProduct("ProductDTO.findTopDpg", txtNameSearch, 1, 20);

            } else if (!"isTopIso".equals(type)) {
                result = productService.getTopProduct("ProductDTO.findTopIso", txtNameSearch, 1, 20);

            } else if (!"isTopFps".equals(type)) {
                result = productService.getTopProduct("ProductDTO.findTopFps", txtNameSearch, 1, 20);

            } else {
                result = productService.getTopProduct("ProductDTO.findByName", txtNameSearch, 1, 20);
            }
            int totalRows = productService.getTotalRows(txtNameSearch).intValue();

            //Convert to XML
            ProductsDTO productsDTO = new ProductsDTO();
            productsDTO.setProductDTOs(result);
            String products_XML = XMLUtils.marrsallMatchToString(productsDTO);
            request.setAttribute("PRODUCTS", products_XML);

            Paging paging = new Paging(page, totalRows, 5, "SearchServlet", txtNameSearch, type);
            String paging_XML = XMLUtils.marrsallMatchToString(paging);
            request.setAttribute("PAGING", paging_XML);

        } catch (Exception e) {
            e.printStackTrace();
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
