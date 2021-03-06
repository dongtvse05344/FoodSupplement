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
import dongtv.util.ServletUtils;
import dongtv.util.XMLUtils;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tran Dong
 */
public class HomeAdminServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Routing.LOGIN_VIEW;
        String pageString = request.getParameter("page");
        String nameSearch = request.getParameter("txtSearch");
        if(nameSearch == null) nameSearch = "";
        int page = 1;
        if(pageString != null) {
            try { 
                page = Integer.parseInt(pageString);
            } catch (Exception e) {
            }
        }
        try {
            HttpSession session = request.getSession();
            if (ServletUtils.isLogin(session)) {
                url = Routing.HOME_ADMIN_VIEW;
                ProductService productService = new ProductService();
                List<ProductDTO> products = productService.getPage(nameSearch, page);
                ProductsDTO productsDTO = new ProductsDTO();
                productsDTO.setProductDTOs(products);
                String products_XML = XMLUtils.marrsallMatchToString(productsDTO);
                request.setAttribute("PRODUCTS", products_XML);
                int totalRows = productService.getTotalRows(nameSearch).intValue();
                Paging paging = new Paging(page,totalRows ,5, "HomeAdminServlet",nameSearch,"","");
                String paging_XML = XMLUtils.marrsallMatchToString(paging);
                request.setAttribute("PAGING", paging_XML);

            }
        } catch (Exception ex) {
           url = Routing.INVALID_VIEW;
            Logger.getLogger(HomeAdminServlet.class.getName()).log(Level.SEVERE, null, ex);
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
