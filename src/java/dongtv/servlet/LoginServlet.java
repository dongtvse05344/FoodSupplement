/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.dto.UserDTO;
import dongtv.service.UserService;
import dongtv.contanst.Routing;
import dongtv.util.ServletUtils;
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
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = Routing.LOGIN_VIEW;
        try {
            HttpSession session = request.getSession();
            if (ServletUtils.isLogin(session)) {
                url = Routing.HOME_ADMIN_SERVLET;  
            } else {
                String username = request.getParameter("username");
                String password = request.getParameter("password");
                boolean isValid = true;
                if (password == null || password.length() == 0) {
                    request.setAttribute("ERROR", "Password can not be blank");
                    isValid = false;
                }
                if (username == null || username.length() == 0) {
                    request.setAttribute("ERROR", "Username can not be blank");
                    isValid = false;
                }
                if (isValid) {
                    UserService userService = new UserService();
                    UserDTO userDTO = userService.checkLogin(username, password);
                    if (userDTO != null) {
                        session.setAttribute("NAME", userDTO.getFullname());
                        session.setAttribute("ISLOGIN", true);
                        url = Routing.HOME_ADMIN_SERVLET;
                    } else {
                        request.setAttribute("ERROR", "Invalid username or password");
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
