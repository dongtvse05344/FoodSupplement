/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.contanst.Routing;
import dongtv.dto.ProductRawDTO;
import dongtv.service.CrawlService;
import dongtv.util.ServletUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tran Dong
 */
public class GroupByNameServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Routing.LOGIN_VIEW;

        try {
            HttpSession session = request.getSession();
            if (ServletUtils.isLogin(session)) {
                url = Routing.CRAWL_ADMIN_SERVLET;
                CrawlService crawlService = new CrawlService();
                List<ProductRawDTO> products = crawlService.getAllProducts();
                ProductRawDTO current = products.get(0);
                for (int i = 1; i < products.size(); i++) {
                    ProductRawDTO dto = products.get(i);
                    if(crawlService.compareName(current, dto)) {
                        dto.setParentId(current.getId());
                        crawlService.updateProductRaw(dto);
                    } else {
                        current = dto;
                        dto.setParentId(null);
                        crawlService.updateProductRaw(dto);
                    }
                }

            }
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