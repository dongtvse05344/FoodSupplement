/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.contanst.Routing;
import dongtv.dto.ProductDTO;
import dongtv.service.ProductService;
import dongtv.util.ServletUtils;
import java.io.IOException;
import java.io.PrintWriter;
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
public class UpdateProductServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Routing.LOGIN_VIEW;
        try {
            HttpSession session = request.getSession();
            if (ServletUtils.isLogin(session)) {
                url = Routing.HOME_ADMIN_SERVLET;
                Integer id = Integer.parseInt(request.getParameter("id"));
                ProductService productService = new ProductService();
                ProductDTO productDTO = productService.getProduct(id);
                String name = request.getParameter("name");
                Integer price = Integer.parseInt(request.getParameter("price"));
                Double dpg = Double.parseDouble(request.getParameter("dpg"));
                Double iso = Double.parseDouble(request.getParameter("iso"));
                Double fps = Double.parseDouble(request.getParameter("fps"));

                productDTO.setName(name);
                productDTO.setPrice(price);
                productDTO.setDpg(dpg);
                productDTO.setIso(iso);
                productDTO.setFps(fps);

                productService.updateProduct(productDTO);
            }
        } catch (Exception ex) {
            Logger.getLogger(UpdateProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            url = Routing.EDIT_PRODUCT_ADMIN;
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
