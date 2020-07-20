/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.servlet;

import dongtv.contanst.Routing;
import dongtv.dto.ProductDTO;
import dongtv.dto.ProductsDTO;
import dongtv.service.ProductService;
import dongtv.util.HTMLUtilities;
import dongtv.util.XMLUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author shuu1
 */
public class ComparisonServlet extends HttpServlet {

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
        String ids = request.getParameter("ids");

        String url = Routing.HOME_SERVLET;
        try {
            if (!ids.isEmpty()) {
                List<String> listId = HTMLUtilities.getAllMatches(ids, "[,\\[]{1}[0-9]{1,10}[,\\]]{1}");
                if (listId.size() > 1) {
                    List<ProductDTO> productDtos = new ArrayList<>();
                    ProductService productService = new ProductService();
                    for (String id : listId) {
                        id = id.substring(1, id.length() - 1);
                        ProductDTO productDto = productService.getProduct(Integer.parseInt(id));
                        productDto.setDescription("");
                        productDtos.add(productDto);
                    }

                    ProductDTO maxP = (ProductDTO) productDtos.get(0).clone();
                    for (ProductDTO productDto : productDtos) {
                        Double a = maxP.getDpg();
                        if (a == null) {
                            a = -1.0;
                        };
                        Double b = productDto.getDpg();
                        if (b == null) {
                            b = -1.0;
                        };
                        maxP.setDpg(Math.max(a, b));
                        a = maxP.getIso();
                        if (a == null) {
                            a = -1.0;
                        };
                        b = productDto.getIso();
                        if (b == null) {
                            b = -1.0;
                        };
                        maxP.setIso(Math.max(a, b));
                        a = maxP.getFps();
                        if (a == null) {
                            a = -1.0;
                        };
                        b = productDto.getFps();
                        if (b == null) {
                            b = -1.0;
                        };
                        maxP.setFps(Math.max(a, b));
                        Integer aa = maxP.getPrice();
                        if (aa == null || aa< 0) {
                            aa = 1000000000;
                        };
                        Integer bb = productDto.getPrice();
                        if (bb == null || bb< 0) {
                            bb = 1000000000;
                        };
                        maxP.setPrice(Math.min(aa, bb));

                        a = maxP.getDisplay();
                        if (a == null) {
                            a = -1.0;
                        };
                        b = productDto.getDisplay();
                        if (b == null) {
                            b = -1.0;
                        };
                        maxP.setDisplay(Math.max(a, b));
                    }
                    int maxPoint =0;
                    int maxPosition = -1;
                    for (int i =0; i< productDtos.size();i++) {
                        ProductDTO dto = productDtos.get(i);
                        int point = 0;
                        if(compare(dto.getDpg(), maxP.getDpg()) ) {
                            point++;
                        }
                        if(compare(dto.getFps(),maxP.getFps())) {
                            point++;
                        }
                        if(compare(dto.getIso(),maxP.getIso())) {
                            point++;
                        }
                        if(compare(dto.getPrice(),maxP.getPrice())) {
                            point++;
                        }
                        if(compare(dto.getDisplay(),maxP.getDisplay())) {
                            point++;
                        }
                        if(maxPoint < point) {
                            maxPosition = i;
                            maxPoint = point;
                        }
                    }
                    productDtos.add(maxP);
                    ProductsDTO productsDTO = new ProductsDTO();
                    productsDTO.setProductDTOs(productDtos);
                    productsDTO.setHightLight(maxPosition+1);
                    String products_XML = XMLUtils.marrsallMatchToString(productsDTO);
                    request.setAttribute("PRODUCTS", products_XML);
                    url = Routing.COMPARISION_VIEW;
                }
            }

        } catch (Exception ex) {
            url = Routing.INVALID_VIEW;
            Logger.getLogger(ComparisonServlet.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }
    
    private boolean compare(Double a, Double b) {
        if(a==null || b == null) return false;
        if(a.equals(b)) return true;
        return false;
    }
    private boolean compare(Integer a, Integer b) {
        if(a==null || b == null) return false;
        if(a.equals(b)) return true;
        return false;
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
