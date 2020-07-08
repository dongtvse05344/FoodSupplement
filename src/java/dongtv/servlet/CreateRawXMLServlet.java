/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.contanst.Routing;
import dongtv.dto.raw.ProductRawDTO;
import dongtv.dto.raw.ProductRawsDTO;
import dongtv.service.CrawlService;
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
public class CreateRawXMLServlet extends HttpServlet {

    private static final String XML_PATH = "products.xml";
    private static final String XSD_PATH = "xsd//Products.xsd";

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
                ProductRawsDTO productsDTO = new ProductRawsDTO();
                productsDTO.setProductDTOs(products);
                String realPath = getServletContext().getRealPath("/");
                XMLUtils.marshalToFile(productsDTO, realPath + XML_PATH);
            }
        } catch (Exception ex) {
            url = Routing.INVALID_VIEW;
            Logger.getLogger(CreateRawXMLServlet.class.getName()).log(Level.SEVERE, null, ex);
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
