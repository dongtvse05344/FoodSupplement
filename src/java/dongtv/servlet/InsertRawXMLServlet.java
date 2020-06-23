/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.contanst.Routing;
import dongtv.dto.ProductRawDTO;
import dongtv.dto.ProductRawsDTO;
import dongtv.dto.ProductsDTO;
import dongtv.service.CrawlService;
import dongtv.util.ServletUtils;
import dongtv.validation.XSDValidation;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tran Dong
 */
public class InsertRawXMLServlet extends HttpServlet {

    private static final String XML_PATH = "products.xml";
    private static final String XSD_PATH = "xsd//CameraProduct.xsd";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Routing.LOGIN_VIEW;
        try {
            HttpSession session = request.getSession();
            if (ServletUtils.isLogin(session)) {
                url = Routing.CRAWL_ADMIN_SERVLET;
                String realPath = getServletContext().getRealPath("/");
                StringBuilder erString = new StringBuilder();
                ProductRawsDTO productsDTO = XSDValidation.validation(realPath + XML_PATH, realPath + XSD_PATH, erString);
//                System.out.println(productsDTO.getProductDTOs().get(0).getVolumeDTOCollection().size());
                request.setAttribute("ERROR_VALIDATION", erString.toString());
                CrawlService crawlService = new CrawlService();
                crawlService.convertRawstoReal(productsDTO);
                File file = new File(realPath + XML_PATH);
//                System.out.println(file.delete());
                request.setAttribute("MESSAGE", "Convert to Products Done !!!");
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
